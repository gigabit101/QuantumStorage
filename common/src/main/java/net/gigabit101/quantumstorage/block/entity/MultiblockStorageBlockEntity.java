package net.gigabit101.quantumstorage.block.entity;

import net.creeperhost.polylib.blocks.InteractableBlock;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.creeperhost.polylib.mulitblock.MultiblockControllerBase;
import net.creeperhost.polylib.mulitblock.MultiblockValidationException;
import net.creeperhost.polylib.mulitblock.rectangular.RectangularMultiblockTileEntityBase;
import net.creeperhost.polylib.platform.Services;
import net.gigabit101.quantumstorage.menu.MultiblockStorageMenu;
import net.gigabit101.quantumstorage.multiblock.MultiblockStorageController;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultiblockStorageBlockEntity extends RectangularMultiblockTileEntityBase implements InteractableBlock, WorldlyContainer, PolyInventoryBlock, MenuProvider {
    public static final int COLUMNS = 13;
    public static final int ROWS = 6;
    public static final int SLOTS_PER_STORAGE = COLUMNS * ROWS;

    private static final int[] STORAGE_SLOTS = createSlots(SLOTS_PER_STORAGE);
    private static final int[] NO_SLOTS = new int[0];

    private final BlockInventory inventory = new BlockInventory(this, SLOTS_PER_STORAGE);

    public MultiblockStorageBlockEntity(BlockPos pos, BlockState state) {
        super(QuantumStorageContent.MULTIBLOCK_STORAGE_BLOCK_ENTITY.get(), pos, state);
    }

    public BlockInventory inventory() {
        return inventory;
    }

    public boolean isStorageBlock() {
        return getBlockState().is(QuantumStorageContent.MULTISTORAGE.get());
    }

    public boolean isIoBlock() {
        return getBlockState().is(QuantumStorageContent.MULTISTORAGE_IO.get());
    }

    public MultiblockStorageController getMultiBlock() {
        return getMultiblockController() instanceof MultiblockStorageController controller ? controller : null;
    }

    public boolean isAssembled() {
        MultiblockStorageController controller = getMultiBlock();
        return controller != null && controller.isAssembled();
    }

    @Override
    public @Nullable Container getContainer(@Nullable Direction side) {
        if (isIoBlock() && isAssembled()) {
            return this;
        }
        return null;
    }

    @Override
    public InteractionResult onBlockUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        MultiblockStorageController controller = getMultiBlock();
        if (controller == null || !controller.isAssembled() || controller.storageBlocks().isEmpty()) {
            return InteractionResult.SUCCESS;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            MultiblockStorageBlockEntity menuStorage = isStorageBlock() ? this : controller.storageBlocks().getFirst();
            Services.REGISTER_HELPER.openMenu(
                    serverPlayer,
                    new SimpleMenuProvider((syncId, inventory, menuPlayer) -> new MultiblockStorageMenu(syncId, inventory, menuStorage), menuStorage.getDisplayName()),
                    buffer -> buffer.writeBlockPos(menuStorage.getBlockPos())
            );
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (isStorageBlock()) {
            return STORAGE_SLOTS;
        }
        if (isIoBlock()) {
            int slotCount = getContainerSize();
            return slotCount == 0 ? NO_SLOTS : createSlots(slotCount);
        }
        return NO_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        return canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return isStorageBlock() || (isIoBlock() && isAssembled());
    }

    @Override
    public int getContainerSize() {
        if (isStorageBlock()) {
            return inventory.getContainerSize();
        }

        MultiblockStorageController controller = getMultiBlock();
        return controller != null && controller.isAssembled() ? controller.storageSlots() : 0;
    }

    @Override
    public boolean isEmpty() {
        if (isStorageBlock()) {
            return inventory.isEmpty();
        }

        MultiblockStorageController controller = getMultiBlock();
        return controller == null || !controller.isAssembled()
                || controller.storageBlocks().stream().allMatch(blockEntity -> blockEntity.inventory().isEmpty());
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        SlotLookup lookup = lookup(slot);
        return lookup == null ? ItemStack.EMPTY : lookup.inventory().getItem(lookup.slot());
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        SlotLookup lookup = lookup(slot);
        return lookup == null ? ItemStack.EMPTY : lookup.inventory().removeItem(lookup.slot(), amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        SlotLookup lookup = lookup(slot);
        return lookup == null ? ItemStack.EMPTY : lookup.inventory().removeItemNoUpdate(lookup.slot());
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        SlotLookup lookup = lookup(slot);
        if (lookup != null) {
            lookup.inventory().setItem(lookup.slot(), stack);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return level != null
                && level.getBlockEntity(worldPosition) == this
                && player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return lookup(slot) != null;
    }

    @Override
    public void clearContent() {
        if (isStorageBlock()) {
            inventory.clearContent();
            return;
        }

        MultiblockStorageController controller = getMultiBlock();
        if (controller != null && controller.isAssembled()) {
            controller.storageBlocks().forEach(blockEntity -> blockEntity.inventory().clearContent());
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (isStorageBlock()) {
            inventory.serialize(output.child("inventory"));
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        if (isStorageBlock()) {
            inventory.deserialize(input.childOrEmpty("inventory"));
        }
    }

    public void dropContents() {
        if (level == null || level.isClientSide() || !isStorageBlock()) {
            return;
        }

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (!stack.isEmpty()) {
                Block.popResource(level, worldPosition, stack.copy());
                inventory.setItem(slot, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void isGoodForFrame() throws MultiblockValidationException {
        if (!getBlockState().is(QuantumStorageContent.MULTISTORAGE_FRAME.get())) {
            throw new MultiblockValidationException("Only Storage Frame blocks can be used for the multiblock frame.");
        }
    }

    @Override
    public void isGoodForSides() throws MultiblockValidationException {
        if (!getBlockState().is(QuantumStorageContent.MULTISTORAGE_HEAT.get())
                && !getBlockState().is(QuantumStorageContent.MULTISTORAGE_IO.get())) {
            throw new MultiblockValidationException("Only Heat Conductors or Storage IO blocks can be used on multiblock sides.");
        }
    }

    @Override
    public void isGoodForTop() throws MultiblockValidationException {
        isGoodForSides();
    }

    @Override
    public void isGoodForBottom() throws MultiblockValidationException {
        if (!getBlockState().is(QuantumStorageContent.MULTISTORAGE_HEAT.get())) {
            throw new MultiblockValidationException("Only Heat Conductors can be used on the multiblock bottom.");
        }
    }

    @Override
    public void isGoodForInterior() throws MultiblockValidationException {
        if (!isStorageBlock()) {
            throw new MultiblockValidationException("Only Storage Units can be used inside the multiblock.");
        }
    }

    @Override
    public void onMachineAssembled(MultiblockControllerBase multiblockControllerBase) {
    }

    @Override
    public void onMachineBroken() {
    }

    @Override
    public void onMachineActivated() {
    }

    @Override
    public void onMachineDeactivated() {
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new MultiblockStorageController(getLevel());
    }

    @Override
    public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
        return MultiblockStorageController.class;
    }

    @Override
    public boolean hasMultiblockSaveData() {
        return false;
    }

    @Override
    public CompoundTag getMultiblockSaveData() {
        return new CompoundTag();
    }

    @Override
    public void onMultiblockDataAssimilated() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.quantumstorage.multistorage");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        MultiblockStorageController controller = getMultiBlock();
        if (isStorageBlock()) {
            return new MultiblockStorageMenu(containerId, playerInventory, this);
        }
        if (controller != null && controller.isAssembled() && !controller.storageBlocks().isEmpty()) {
            return new MultiblockStorageMenu(containerId, playerInventory, controller.storageBlocks().getFirst());
        }
        return null;
    }

    private SlotLookup lookup(int slot) {
        if (slot < 0) {
            return null;
        }

        if (isStorageBlock()) {
            return slot < inventory.getContainerSize() ? new SlotLookup(this, slot) : null;
        }

        if (!isIoBlock()) {
            return null;
        }

        MultiblockStorageController controller = getMultiBlock();
        if (controller == null || !controller.isAssembled() || slot >= controller.storageSlots()) {
            return null;
        }

        int blockIndex = slot / SLOTS_PER_STORAGE;
        int storageSlot = slot % SLOTS_PER_STORAGE;
        return new SlotLookup(controller.storageBlocks().get(blockIndex), storageSlot);
    }

    private static int[] createSlots(int size) {
        int[] slots = new int[size];
        for (int slot = 0; slot < size; slot++) {
            slots[slot] = slot;
        }
        return slots;
    }

    private record SlotLookup(MultiblockStorageBlockEntity blockEntity, int slot) {
        private BlockInventory inventory() {
            return blockEntity.inventory();
        }
    }
}

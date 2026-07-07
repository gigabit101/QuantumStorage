package net.gigabit101.quantumstorage.block.entity;

import net.creeperhost.polylib.blocks.InteractableBlock;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.platform.Services;
import net.gigabit101.quantumstorage.menu.QuantumStorageUnitMenu;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class QuantumStorageUnitBlockEntity extends PolyBlockEntity implements InteractableBlock, WorldlyContainer {
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final long MAX_STORED = Integer.MAX_VALUE;
    private static final int[] AUTOMATION_SLOTS = new int[]{INPUT_SLOT, OUTPUT_SLOT};

    private final BlockInventory inventory = new BlockInventory(this, 2)
            .setSlotValidator(INPUT_SLOT, this::canAcceptItem)
            .setSlotValidator(OUTPUT_SLOT, stack -> false);
    private ItemStack storedStack = ItemStack.EMPTY;
    private long storedCount = 0L;
    private boolean renderUpgrade;
    private boolean voidUpgrade;

    public QuantumStorageUnitBlockEntity(BlockPos pos, BlockState state) {
        super(QuantumStorageContent.QUANTUM_STORAGE_UNIT_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) {
            return;
        }

        boolean changed = absorbInput();
        changed |= refillOutput();
        if (changed) {
            setChanged();
        }
    }

    public BlockInventory inventory() {
        return inventory;
    }

    @Override
    public CompoundTag writeToItemStack(HolderLookup.Provider provider, boolean willHarvest) {
        if (willHarvest) {
            prepareForHarvest();
        }
        return super.writeToItemStack(provider, willHarvest);
    }

    public void prepareForHarvest() {
        storeSlotContents(INPUT_SLOT);
        storeSlotContents(OUTPUT_SLOT);
    }

    public ItemStack getStoredStack() {
        return storedStack.copy();
    }

    public long getStoredCount() {
        return storedCount;
    }

    public long getDisplayStoredCount() {
        return storedCount + inventory.getItem(OUTPUT_SLOT).getCount();
    }

    public boolean hasRenderUpgrade() {
        return renderUpgrade;
    }

    public boolean hasVoidUpgrade() {
        return voidUpgrade;
    }

    public boolean applyRenderUpgrade() {
        if (renderUpgrade) {
            return false;
        }
        renderUpgrade = true;
        setChanged();
        return true;
    }

    public boolean applyVoidUpgrade() {
        if (voidUpgrade) {
            return false;
        }
        voidUpgrade = true;
        setChanged();
        return true;
    }

    public boolean applyCreativeUpgrade() {
        ItemStack displayStack = storedStack.isEmpty() ? inventory.getItem(OUTPUT_SLOT) : storedStack;
        if (displayStack.isEmpty()) {
            return false;
        }

        storedStack = displayStack.copyWithCount(1);
        storedCount = Math.max(0L, MAX_STORED - inventory.getItem(OUTPUT_SLOT).getCount());
        setChanged();
        return true;
    }

    public boolean canAcceptItem(ItemStack stack) {
        boolean sameItem = storedStack.isEmpty() || ItemStack.isSameItemSameComponents(storedStack, stack);
        return !stack.isEmpty()
                && sameItem
                && (storedCount < MAX_STORED || voidUpgrade);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return AUTOMATION_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        return slot == INPUT_SLOT && canAcceptItem(stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return slot == OUTPUT_SLOT;
    }

    @Override
    public int getContainerSize() {
        return inventory.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return inventory.getItem(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return inventory.removeItem(slot, amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return inventory.removeItemNoUpdate(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.setItem(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return inventory.stillValid(player);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot == INPUT_SLOT && canAcceptItem(stack);
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
    }

    private boolean absorbInput() {
        ItemStack input = inventory.getItem(INPUT_SLOT);
        if (input.isEmpty() || !canAcceptItem(input)) {
            return false;
        }

        if (storedStack.isEmpty()) {
            storedStack = input.copyWithCount(1);
        }

        int toStore = (int) Math.min(input.getCount(), MAX_STORED - storedCount);
        if (toStore <= 0) {
            if (!voidUpgrade || storedStack.isEmpty() || !ItemStack.isSameItemSameComponents(storedStack, input)) {
                return false;
            }

            inventory.setItem(INPUT_SLOT, ItemStack.EMPTY);
            return true;
        }

        input.shrink(toStore);
        storedCount += toStore;
        if (input.isEmpty()) {
            inventory.setItem(INPUT_SLOT, ItemStack.EMPTY);
        }
        return true;
    }

    private boolean refillOutput() {
        ItemStack output = inventory.getItem(OUTPUT_SLOT);
        if (storedStack.isEmpty()) {
            return false;
        }

        if (output.isEmpty()) {
            if (storedCount <= 0) {
                storedStack = ItemStack.EMPTY;
                return true;
            }

            int toExtract = (int) Math.min(storedStack.getMaxStackSize(), storedCount);
            inventory.setItem(OUTPUT_SLOT, storedStack.copyWithCount(toExtract));
            storedCount -= toExtract;
            if (storedCount <= 0) {
                storedCount = 0;
            }
            return true;
        }

        if (!ItemStack.isSameItemSameComponents(storedStack, output) || output.getCount() >= output.getMaxStackSize() || storedCount <= 0) {
            return false;
        }

        int room = output.getMaxStackSize() - output.getCount();
        int toExtract = (int) Math.min(room, storedCount);
        output.grow(toExtract);
        storedCount -= toExtract;
        if (storedCount <= 0) {
            storedCount = 0;
        }
        return toExtract > 0;
    }

    private void storeSlotContents(int slot) {
        ItemStack stack = inventory.getItem(slot);
        if (stack.isEmpty()) {
            return;
        }

        if (storedStack.isEmpty()) {
            storedStack = stack.copyWithCount(1);
        }

        if (!ItemStack.isSameItemSameComponents(storedStack, stack)) {
            return;
        }

        long room = MAX_STORED - storedCount;
        if (room <= 0) {
            return;
        }

        int toStore = (int) Math.min(stack.getCount(), room);
        storedCount += toStore;
        stack.shrink(toStore);
        if (stack.isEmpty()) {
            inventory.setItem(slot, ItemStack.EMPTY);
        }
    }

    @Override
    public InteractionResult onBlockUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            Services.REGISTER_HELPER.openMenu(
                    serverPlayer,
                    new SimpleMenuProvider((syncId, inventory, menuPlayer) -> new QuantumStorageUnitMenu(syncId, inventory, this), getDisplayName()),
                    buffer -> buffer.writeBlockPos(pos)
            );
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void writeExtraData(ValueOutput output) {
        inventory.serialize(output.child("inventory"));
        output.store("stored_stack", ItemStack.OPTIONAL_CODEC, storedStack);
        output.putLong("stored_count", storedCount);
        output.putBoolean("render_upgrade", renderUpgrade);
        output.putBoolean("void_upgrade", voidUpgrade);
    }

    @Override
    public void readExtraData(ValueInput input) {
        inventory.deserialize(input.childOrEmpty("inventory"));
        storedStack = input.read("stored_stack", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
        if (!storedStack.isEmpty()) {
            storedStack = storedStack.copyWithCount(1);
        }
        storedCount = Math.max(0L, Math.min(MAX_STORED, input.getLongOr("stored_count", 0L)));
        renderUpgrade = input.getBooleanOr("render_upgrade", false);
        voidUpgrade = input.getBooleanOr("void_upgrade", false);
        if (storedCount == 0L && inventory.getItem(OUTPUT_SLOT).isEmpty()) {
            storedStack = ItemStack.EMPTY;
        }
    }
}

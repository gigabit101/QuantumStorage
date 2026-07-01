package net.gigabit101.quantumstorage.block.entity;

import net.creeperhost.polylib.blocks.InteractableBlock;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.data.serializable.IntData;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.inventory.items.ContainerAccessControl;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.creeperhost.polylib.platform.Services;
import net.gigabit101.quantumstorage.item.CrateItem;
import net.gigabit101.quantumstorage.menu.CratingMachineMenu;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
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
import org.jetbrains.annotations.Nullable;

public class CratingMachineBlockEntity extends PolyBlockEntity implements InteractableBlock, WorldlyContainer, PolyInventoryBlock {
    public static final int CRATE_SLOT = 0;
    public static final int ITEM_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    public static final int WORK_TIME = 100;

    private static final int[] SLOTS = new int[]{CRATE_SLOT, ITEM_SLOT, OUTPUT_SLOT};

    private final BlockInventory inventory;
    private final ContainerAccessControl accessControl;
    private final IntData progress = register("progress", new IntData(), SAVE, SYNC);

    public CratingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(QuantumStorageContent.CRATING_MACHINE_BLOCK_ENTITY.get(), pos, state);
        inventory = new BlockInventory(this, 3)
                .setSlotValidator(CRATE_SLOT, CrateItem::isEmptyCrate)
                .setSlotValidator(ITEM_SLOT, this::canCrateItem)
                .setSlotValidator(OUTPUT_SLOT, stack -> false);
        accessControl = new ContainerAccessControl(inventory, 0, 3);
    }

    public BlockInventory inventory() {
        return inventory;
    }

    public int getProgress() {
        return progress.get();
    }

    public boolean canCrateItem(ItemStack stack) {
        return !stack.isEmpty()
                && !CrateItem.isEmptyCrate(stack)
                && !CrateItem.isFilledCrate(stack)
                && stack.getCount() == stack.getMaxStackSize();
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) {
            return;
        }

        if (!canWork()) {
            if (progress.get() != 0) {
                progress.zero();
                setChanged();
            }
            return;
        }

        progress.inc();
        if (progress.get() >= WORK_TIME) {
            processCrate();
            progress.zero();
        }
        setChanged();
    }

    private boolean canWork() {
        return CrateItem.isEmptyCrate(inventory.getItem(CRATE_SLOT))
                && canCrateItem(inventory.getItem(ITEM_SLOT))
                && inventory.getItem(OUTPUT_SLOT).isEmpty();
    }

    private void processCrate() {
        if (level == null) {
            return;
        }

        ItemStack packedStack = inventory.getItem(ITEM_SLOT).copy();
        ItemStack filledCrate = CrateItem.createFilledCrate(packedStack, level.registryAccess());
        if (filledCrate.isEmpty()) {
            return;
        }

        inventory.removeItem(CRATE_SLOT, 1);
        inventory.setItem(ITEM_SLOT, ItemStack.EMPTY);
        inventory.setItem(OUTPUT_SLOT, filledCrate);
    }

    @Override
    public @Nullable Container getContainer(@Nullable Direction side) {
        return accessControl;
    }

    @Override
    public InteractionResult onBlockUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            Services.REGISTER_HELPER.openMenu(
                    serverPlayer,
                    new SimpleMenuProvider((syncId, inventory, menuPlayer) -> new CratingMachineMenu(syncId, inventory, this), getDisplayName()),
                    buffer -> buffer.writeBlockPos(pos)
            );
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        return slot != OUTPUT_SLOT && inventory.canPlaceItem(slot, stack);
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
        return inventory.canPlaceItem(slot, stack);
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
    }

    @Override
    public void writeExtraData(ValueOutput output) {
        inventory.serialize(output.child("inventory"));
    }

    @Override
    public void readExtraData(ValueInput input) {
        inventory.deserialize(input.childOrEmpty("inventory"));
    }
}

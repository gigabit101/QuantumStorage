package net.gigabit101.quantumstorage.block.entity;

import net.creeperhost.polylib.blocks.InteractableBlock;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.platform.Services;
import net.gigabit101.quantumstorage.menu.TrashCanMenu;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class TrashCanBlockEntity extends PolyBlockEntity implements InteractableBlock, WorldlyContainer {
    public static final int TRASH_SLOT = 0;
    private static final int[] AUTOMATION_SLOTS = new int[]{TRASH_SLOT};

    public TrashCanBlockEntity(BlockPos pos, BlockState state) {
        super(QuantumStorageContent.TRASH_CAN_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public InteractionResult onBlockUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            Services.REGISTER_HELPER.openMenu(
                    serverPlayer,
                    new SimpleMenuProvider((syncId, inventory, menuPlayer) -> new TrashCanMenu(syncId, inventory, this), getDisplayName()),
                    buffer -> buffer.writeBlockPos(pos)
            );
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return AUTOMATION_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        return slot == TRASH_SLOT && !stack.isEmpty();
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return false;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot == TRASH_SLOT && !stack.isEmpty()) {
            stack.setCount(0);
            setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return getLevel() != null && getLevel().getBlockEntity(getBlockPos()) == this && player.distanceToSqr(
                (double) getBlockPos().getX() + 0.5D,
                (double) getBlockPos().getY() + 0.5D,
                (double) getBlockPos().getZ() + 0.5D
        ) <= getAccessDistanceSq();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot == TRASH_SLOT && !stack.isEmpty();
    }

    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) {
        return false;
    }

    @Override
    public void clearContent() {
    }
}

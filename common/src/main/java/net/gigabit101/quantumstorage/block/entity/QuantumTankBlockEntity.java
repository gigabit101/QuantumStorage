package net.gigabit101.quantumstorage.block.entity;

import net.creeperhost.polylib.blocks.InteractableBlock;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.inventory.fluid.FluidManager;
import net.creeperhost.polylib.inventory.fluid.IPolyFluidStorage;
import net.creeperhost.polylib.inventory.fluid.IPolyFluidStorageItem;
import net.creeperhost.polylib.inventory.fluid.PolyBlockFluidStorage;
import net.creeperhost.polylib.inventory.fluid.PolyFluidBlock;
import net.creeperhost.polylib.inventory.fluid.PolyFluidStack;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.platform.Services;
import net.gigabit101.quantumstorage.menu.QuantumTankMenu;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
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

public class QuantumTankBlockEntity extends PolyBlockEntity implements InteractableBlock, WorldlyContainer, PolyFluidBlock {
    public static final int CONTAINER_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final long CAPACITY = Integer.MAX_VALUE * FluidManager.MILLIBUCKET;
    private static final int[] AUTOMATION_SLOTS = new int[]{CONTAINER_SLOT, OUTPUT_SLOT};

    private final BlockInventory inventory = new BlockInventory(this, 2)
            .setSlotValidator(CONTAINER_SLOT, FluidManager::isFluidItem)
            .setSlotValidator(OUTPUT_SLOT, stack -> false);
    private final PolyBlockFluidStorage tank = new PolyBlockFluidStorage(this, CAPACITY);

    public QuantumTankBlockEntity(BlockPos pos, BlockState state) {
        super(QuantumStorageContent.QUANTUM_TANK_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) {
            return;
        }

        boolean changed = processContainerSlot();
        FluidManager.distributeFluidNearby(this);
        if (changed) {
            setChanged();
        }
    }

    public BlockInventory inventory() {
        return inventory;
    }

    public PolyFluidStack getStoredFluid() {
        return tank.getFluid().copy();
    }

    public long getCapacity() {
        return tank.getCapacity();
    }

    @Override
    public @Nullable IPolyFluidStorage getFluidStorage(@Nullable Direction side) {
        return tank;
    }

    @Override
    public InteractionResult onBlockUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ItemStack held = player.getMainHandItem();
            if (!held.isEmpty() && FluidManager.isFluidItem(held) && tryUseHeldFluidContainer(player, held)) {
                return InteractionResult.SUCCESS;
            }

            Services.REGISTER_HELPER.openMenu(
                    serverPlayer,
                    new SimpleMenuProvider((syncId, inventory, menuPlayer) -> new QuantumTankMenu(syncId, inventory, this), getDisplayName()),
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
        return slot == CONTAINER_SLOT && inventory.canPlaceItem(slot, stack);
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
        return slot == CONTAINER_SLOT && inventory.canPlaceItem(slot, stack);
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
    }

    @Override
    public void writeExtraData(ValueOutput output) {
        inventory.serialize(output.child("inventory"));
        tank.serialize(output.child("tank"));
    }

    @Override
    public void readExtraData(ValueInput input) {
        inventory.deserialize(input.childOrEmpty("inventory"));
        tank.deserialize(input.childOrEmpty("tank"));
    }

    private boolean processContainerSlot() {
        ItemStack input = inventory.getItem(CONTAINER_SLOT);
        if (input.isEmpty() || !inventory.getItem(OUTPUT_SLOT).isEmpty()) {
            return false;
        }

        ItemStack working = input.copyWithCount(1);
        IPolyFluidStorageItem container = FluidManager.getHandler(working);
        if (container == null) {
            return false;
        }

        if (!transferContainerToTank(container) && !transferTankToContainer(container)) {
            return false;
        }

        input.shrink(1);
        if (input.isEmpty()) {
            inventory.setItem(CONTAINER_SLOT, ItemStack.EMPTY);
        }
        inventory.setItem(OUTPUT_SLOT, container.getContainer());
        return true;
    }

    private boolean tryUseHeldFluidContainer(Player player, ItemStack held) {
        ItemStack working = held.copyWithCount(1);
        IPolyFluidStorageItem container = FluidManager.getHandler(working);
        if (container == null || (!transferContainerToTank(container) && !transferTankToContainer(container))) {
            return false;
        }

        held.shrink(1);
        ItemStack result = container.getContainer();
        if (held.isEmpty()) {
            player.setItemInHand(InteractionHand.MAIN_HAND, result);
        } else if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }
        return true;
    }

    private boolean transferContainerToTank(IPolyFluidStorageItem container) {
        PolyFluidStack available = container.drain(FluidManager.BUCKET, true);
        if (available.isEmpty()) {
            return false;
        }

        long accepted = tank.fill(available, true);
        if (accepted <= 0) {
            return false;
        }

        PolyFluidStack drained = container.drain(available.copyWithAmount(accepted), false);
        if (drained.isEmpty()) {
            return false;
        }
        tank.fill(drained, false);
        return true;
    }

    private boolean transferTankToContainer(IPolyFluidStorageItem container) {
        PolyFluidStack stored = tank.getFluid();
        if (stored.isEmpty()) {
            return false;
        }

        long accepted = container.fill(stored.copyWithAmount(FluidManager.BUCKET), true);
        if (accepted <= 0) {
            return false;
        }

        PolyFluidStack drained = tank.drain(stored.copyWithAmount(accepted), false);
        if (drained.isEmpty()) {
            return false;
        }
        container.fill(drained, false);
        return true;
    }
}

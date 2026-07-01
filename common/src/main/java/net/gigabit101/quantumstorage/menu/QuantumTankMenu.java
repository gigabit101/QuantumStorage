package net.gigabit101.quantumstorage.menu;

import net.creeperhost.polylib.client.modulargui.lib.container.DataSync;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.serializable.FluidData;
import net.creeperhost.polylib.inventory.fluid.PolyFluidStack;
import net.gigabit101.quantumstorage.block.entity.QuantumTankBlockEntity;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class QuantumTankMenu extends PolyBlockContainerMenu<QuantumTankBlockEntity> {
    public final SlotGroup tankSlots;
    public final SlotGroup playerSlots;
    public final SlotGroup hotbarSlots;
    public final DataSync<PolyFluidStack> storedFluid;

    public QuantumTankMenu(int containerId, Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        this(containerId, inventory, getClientBlockEntity(inventory, data));
    }

    public QuantumTankMenu(int containerId, Inventory inventory, QuantumTankBlockEntity blockEntity) {
        super(QuantumStorageContent.QUANTUM_TANK_MENU.get(), containerId, inventory, blockEntity);

        tankSlots = remoteSlotGroup();
        tankSlots.addSlot(new PolySlot(blockEntity.inventory(), QuantumTankBlockEntity.CONTAINER_SLOT)
                .setValidator(stack -> blockEntity.inventory().canPlaceItem(QuantumTankBlockEntity.CONTAINER_SLOT, stack)));
        tankSlots.addSlot(new PolySlot(blockEntity.inventory(), QuantumTankBlockEntity.OUTPUT_SLOT).output());

        playerSlots = playerSlotGroup();
        playerSlots.addPlayerMain(inventory);

        hotbarSlots = playerSlotGroup();
        hotbarSlots.addPlayerBar(inventory);

        boolean forceInitialSync = !inventory.player.level().isClientSide();
        storedFluid = new DataSync<>(this, new InitialFluidData(blockEntity.getStoredFluid(), forceInitialSync), blockEntity::getStoredFluid);
    }

    private static QuantumTankBlockEntity getClientBlockEntity(Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        Objects.requireNonNull(data, "Quantum Tank menu requires a block position");
        if (inventory.player.level().getBlockEntity(data.readBlockPos()) instanceof QuantumTankBlockEntity blockEntity) {
            return blockEntity;
        }
        throw new IllegalStateException("Quantum Tank menu opened for a missing block entity");
    }

    private static final class InitialFluidData extends FluidData {
        private boolean forceInitialSync;

        private InitialFluidData(PolyFluidStack value, boolean forceInitialSync) {
            super(value);
            this.forceInitialSync = forceInitialSync;
        }

        @Override
        public boolean isSameValue(PolyFluidStack newValue) {
            if (forceInitialSync) {
                forceInitialSync = false;
                return false;
            }
            return super.isSameValue(newValue);
        }
    }
}

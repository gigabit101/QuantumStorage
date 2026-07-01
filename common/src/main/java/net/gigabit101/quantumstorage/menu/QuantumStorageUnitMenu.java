package net.gigabit101.quantumstorage.menu;

import net.creeperhost.polylib.client.modulargui.lib.container.DataSync;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.serializable.LongData;
import net.creeperhost.polylib.data.serializable.StackData;
import net.gigabit101.quantumstorage.block.entity.QuantumStorageUnitBlockEntity;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class QuantumStorageUnitMenu extends PolyBlockContainerMenu<QuantumStorageUnitBlockEntity> {
    public final SlotGroup storageSlots;
    public final SlotGroup playerSlots;
    public final SlotGroup hotbarSlots;
    public final DataSync<Long> storedCount;
    public final DataSync<ItemStack> storedStack;

    public QuantumStorageUnitMenu(int containerId, Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        this(containerId, inventory, getClientBlockEntity(inventory, data));
    }

    public QuantumStorageUnitMenu(int containerId, Inventory inventory, QuantumStorageUnitBlockEntity blockEntity) {
        super(QuantumStorageContent.QUANTUM_STORAGE_UNIT_MENU.get(), containerId, inventory, blockEntity);

        storageSlots = remoteSlotGroup();
        storageSlots.addSlot(new PolySlot(blockEntity.inventory(), QuantumStorageUnitBlockEntity.INPUT_SLOT)
                .setValidator(blockEntity::canAcceptItem));
        storageSlots.addSlot(new PolySlot(blockEntity.inventory(), QuantumStorageUnitBlockEntity.OUTPUT_SLOT).output());

        playerSlots = playerSlotGroup();
        playerSlots.addPlayerMain(inventory);

        hotbarSlots = playerSlotGroup();
        hotbarSlots.addPlayerBar(inventory);

        boolean forceInitialSync = !inventory.player.level().isClientSide();
        storedCount = new DataSync<>(this, new InitialLongData(blockEntity.getDisplayStoredCount(), forceInitialSync), blockEntity::getDisplayStoredCount);
        storedStack = new DataSync<>(this, new InitialStackData(blockEntity.getStoredStack(), forceInitialSync), blockEntity::getStoredStack);
    }

    private static QuantumStorageUnitBlockEntity getClientBlockEntity(Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        Objects.requireNonNull(data, "Quantum Storage Unit menu requires a block position");
        if (inventory.player.level().getBlockEntity(data.readBlockPos()) instanceof QuantumStorageUnitBlockEntity blockEntity) {
            return blockEntity;
        }
        throw new IllegalStateException("Quantum Storage Unit menu opened for a missing block entity");
    }

    private static final class InitialLongData extends LongData {
        private boolean forceInitialSync;

        private InitialLongData(long value, boolean forceInitialSync) {
            super(value);
            this.forceInitialSync = forceInitialSync;
        }

        @Override
        public boolean isSameValue(Long newValue) {
            if (forceInitialSync) {
                forceInitialSync = false;
                return false;
            }
            return super.isSameValue(newValue);
        }
    }

    private static final class InitialStackData extends StackData {
        private boolean forceInitialSync;

        private InitialStackData(ItemStack value, boolean forceInitialSync) {
            super(value);
            this.forceInitialSync = forceInitialSync;
        }

        @Override
        public boolean isSameValue(ItemStack newValue) {
            if (forceInitialSync) {
                forceInitialSync = false;
                return false;
            }
            return super.isSameValue(newValue);
        }
    }
}

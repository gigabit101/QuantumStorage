package net.gigabit101.quantumstorage.menu;

import net.creeperhost.polylib.client.modulargui.lib.container.DataSync;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.serializable.IntData;
import net.gigabit101.quantumstorage.block.entity.CratingMachineBlockEntity;
import net.gigabit101.quantumstorage.item.CrateItem;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CratingMachineMenu extends PolyBlockContainerMenu<CratingMachineBlockEntity> {
    public final SlotGroup machineSlots;
    public final SlotGroup playerSlots;
    public final SlotGroup hotbarSlots;
    public final DataSync<Integer> progress;

    public CratingMachineMenu(int containerId, Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        this(containerId, inventory, getClientBlockEntity(inventory, data));
    }

    public CratingMachineMenu(int containerId, Inventory inventory, CratingMachineBlockEntity blockEntity) {
        super(QuantumStorageContent.CRATING_MACHINE_MENU.get(), containerId, inventory, blockEntity);

        machineSlots = remoteSlotGroup();
        machineSlots.addSlot(new PolySlot(blockEntity.inventory(), CratingMachineBlockEntity.CRATE_SLOT)
                .setValidator(CrateItem::isEmptyCrate));
        machineSlots.addSlot(new PolySlot(blockEntity.inventory(), CratingMachineBlockEntity.ITEM_SLOT)
                .setValidator(blockEntity::canCrateItem));
        machineSlots.addSlot(new PolySlot(blockEntity.inventory(), CratingMachineBlockEntity.OUTPUT_SLOT).output());

        playerSlots = playerSlotGroup();
        playerSlots.addPlayerMain(inventory);

        hotbarSlots = playerSlotGroup();
        hotbarSlots.addPlayerBar(inventory);

        boolean forceInitialSync = !inventory.player.level().isClientSide();
        progress = new DataSync<>(this, new InitialIntData(blockEntity.getProgress(), forceInitialSync), blockEntity::getProgress);
    }

    private static CratingMachineBlockEntity getClientBlockEntity(Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        Objects.requireNonNull(data, "Crating Machine menu requires a block position");
        if (inventory.player.level().getBlockEntity(data.readBlockPos()) instanceof CratingMachineBlockEntity blockEntity) {
            return blockEntity;
        }
        throw new IllegalStateException("Crating Machine menu opened for a missing block entity");
    }

    private static final class InitialIntData extends IntData {
        private boolean forceInitialSync;

        private InitialIntData(int value, boolean forceInitialSync) {
            super(value);
            this.forceInitialSync = forceInitialSync;
        }

        @Override
        public boolean isSameValue(Integer newValue) {
            if (forceInitialSync) {
                forceInitialSync = false;
                return false;
            }
            return super.isSameValue(newValue);
        }
    }
}

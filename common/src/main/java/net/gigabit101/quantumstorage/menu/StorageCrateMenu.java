package net.gigabit101.quantumstorage.menu;

import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.gigabit101.quantumstorage.block.entity.StorageCrateBlockEntity;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StorageCrateMenu extends PolyBlockContainerMenu<StorageCrateBlockEntity> {
    public final SlotGroup crateSlots;
    public final SlotGroup playerSlots;
    public final SlotGroup hotbarSlots;
    public final int columns;
    public final int rows;

    public StorageCrateMenu(int containerId, Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        this(containerId, inventory, getClientBlockEntity(inventory, data));
    }

    public StorageCrateMenu(int containerId, Inventory inventory, StorageCrateBlockEntity blockEntity) {
        super(QuantumStorageContent.STORAGE_CRATE_MENU.get(), containerId, inventory, blockEntity);

        this.columns = blockEntity.tier().columns();
        this.rows = blockEntity.tier().rows();

        crateSlots = remoteSlotGroup();
        for (int slot = 0; slot < blockEntity.inventory().getContainerSize(); slot++) {
            crateSlots.addSlot(new PolySlot(blockEntity.inventory(), slot));
        }

        playerSlots = playerSlotGroup();
        playerSlots.addPlayerMain(inventory);

        hotbarSlots = playerSlotGroup();
        hotbarSlots.addPlayerBar(inventory);
    }

    private static StorageCrateBlockEntity getClientBlockEntity(Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        Objects.requireNonNull(data, "Storage Crate menu requires a block position");
        if (inventory.player.level().getBlockEntity(data.readBlockPos()) instanceof StorageCrateBlockEntity blockEntity) {
            return blockEntity;
        }
        throw new IllegalStateException("Storage Crate menu opened for a missing block entity");
    }
}

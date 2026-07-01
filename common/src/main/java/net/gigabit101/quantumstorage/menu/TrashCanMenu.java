package net.gigabit101.quantumstorage.menu;

import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.gigabit101.quantumstorage.block.entity.TrashCanBlockEntity;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TrashCanMenu extends PolyBlockContainerMenu<TrashCanBlockEntity> {
    public final SlotGroup trashSlot;
    public final SlotGroup playerSlots;
    public final SlotGroup hotbarSlots;

    public TrashCanMenu(int containerId, Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        this(containerId, inventory, getClientBlockEntity(inventory, data));
    }

    public TrashCanMenu(int containerId, Inventory inventory, TrashCanBlockEntity blockEntity) {
        super(QuantumStorageContent.TRASH_CAN_MENU.get(), containerId, inventory, blockEntity);

        trashSlot = remoteSlotGroup();
        trashSlot.addSlot(new PolySlot(blockEntity, TrashCanBlockEntity.TRASH_SLOT));

        playerSlots = playerSlotGroup();
        playerSlots.addPlayerMain(inventory);

        hotbarSlots = playerSlotGroup();
        hotbarSlots.addPlayerBar(inventory);
    }

    private static TrashCanBlockEntity getClientBlockEntity(Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        Objects.requireNonNull(data, "Trash Can menu requires a block position");
        if (inventory.player.level().getBlockEntity(data.readBlockPos()) instanceof TrashCanBlockEntity blockEntity) {
            return blockEntity;
        }
        throw new IllegalStateException("Trash Can menu opened for a missing block entity");
    }
}

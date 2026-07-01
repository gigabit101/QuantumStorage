package net.gigabit101.quantumstorage.menu;

import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.gigabit101.quantumstorage.block.entity.FluidTrashCanBlockEntity;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FluidTrashCanMenu extends PolyBlockContainerMenu<FluidTrashCanBlockEntity> {
    public final SlotGroup trashSlots;
    public final SlotGroup playerSlots;
    public final SlotGroup hotbarSlots;

    public FluidTrashCanMenu(int containerId, Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        this(containerId, inventory, getClientBlockEntity(inventory, data));
    }

    public FluidTrashCanMenu(int containerId, Inventory inventory, FluidTrashCanBlockEntity blockEntity) {
        super(QuantumStorageContent.FLUID_TRASH_CAN_MENU.get(), containerId, inventory, blockEntity);

        trashSlots = remoteSlotGroup();
        trashSlots.addSlot(new PolySlot(blockEntity.inventory(), FluidTrashCanBlockEntity.CONTAINER_SLOT)
                .setValidator(stack -> blockEntity.inventory().canPlaceItem(FluidTrashCanBlockEntity.CONTAINER_SLOT, stack)));
        trashSlots.addSlot(new PolySlot(blockEntity.inventory(), FluidTrashCanBlockEntity.OUTPUT_SLOT).output());

        playerSlots = playerSlotGroup();
        playerSlots.addPlayerMain(inventory);

        hotbarSlots = playerSlotGroup();
        hotbarSlots.addPlayerBar(inventory);
    }

    private static FluidTrashCanBlockEntity getClientBlockEntity(Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        Objects.requireNonNull(data, "Fluid Trash Can menu requires a block position");
        if (inventory.player.level().getBlockEntity(data.readBlockPos()) instanceof FluidTrashCanBlockEntity blockEntity) {
            return blockEntity;
        }
        throw new IllegalStateException("Fluid Trash Can menu opened for a missing block entity");
    }
}

package net.gigabit101.quantumstorage.menu;

import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.ModularGuiContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.gigabit101.quantumstorage.block.entity.MultiblockStorageBlockEntity;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MultiblockStorageMenu extends ModularGuiContainerMenu {
    public final MultiblockStorageBlockEntity blockEntity;
    public final SlotGroup storageSlots;
    public final SlotGroup playerSlots;
    public final SlotGroup hotbarSlots;

    public MultiblockStorageMenu(int containerId, Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        this(containerId, inventory, getClientBlockEntity(inventory, data));
    }

    public MultiblockStorageMenu(int containerId, Inventory inventory, MultiblockStorageBlockEntity blockEntity) {
        super(QuantumStorageContent.MULTIBLOCK_STORAGE_MENU.get(), containerId, inventory);
        this.blockEntity = blockEntity;

        storageSlots = remoteSlotGroup();
        for (int slot = 0; slot < blockEntity.inventory().getContainerSize(); slot++) {
            storageSlots.addSlot(new PolySlot(blockEntity.inventory(), slot));
        }

        playerSlots = playerSlotGroup();
        playerSlots.addPlayerMain(inventory);

        hotbarSlots = playerSlotGroup();
        hotbarSlots.addPlayerBar(inventory);
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }

    private static MultiblockStorageBlockEntity getClientBlockEntity(Inventory inventory, @Nullable RegistryFriendlyByteBuf data) {
        Objects.requireNonNull(data, "Multiblock Storage menu requires a block position");
        if (inventory.player.level().getBlockEntity(data.readBlockPos()) instanceof MultiblockStorageBlockEntity blockEntity) {
            return blockEntity;
        }
        throw new IllegalStateException("Multiblock Storage menu opened for a missing block entity");
    }
}

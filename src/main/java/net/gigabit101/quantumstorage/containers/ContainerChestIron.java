package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.init.ModContainers;
import net.gigabit101.quantumstorage.tiles.chests.TileChestBase;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import java.util.Objects;

public class ContainerChestIron extends ContainerChestBase {

    public ContainerChestIron(int id, Inventory playerInv, FriendlyByteBuf extraData) {
        this(id, playerInv, (TileChestBase) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos())));
    }

    public ContainerChestIron(int id, Inventory playerInv, TileChestBase te) {
        super(ModContainers.CHEST_IRON_CONTAINER.get(), id);

        addSlots(te, 9, playerInv);
    }
}

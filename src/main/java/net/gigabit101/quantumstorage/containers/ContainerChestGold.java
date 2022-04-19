package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.init.ModContainers;
import net.gigabit101.quantumstorage.tiles.chests.TileChestGold;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import java.util.Objects;

public class ContainerChestGold extends ContainerChestBase {

    public ContainerChestGold(int id, Inventory playerInv, FriendlyByteBuf extraData) {
        this(id, playerInv, (TileChestGold) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos())));
    }

    public ContainerChestGold(int id, Inventory playerInv, TileChestGold te) {
        super(ModContainers.CHEST_GOLD_CONTAINER.get(), id);

        addSlots(te, 9, playerInv);
    }


}

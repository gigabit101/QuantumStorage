package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.init.ModContainers;
import net.gigabit101.quantumstorage.tiles.chests.TileChestDiamond;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import java.util.Objects;

public class ContainerChestDiamond extends ContainerChestBase {

    public ContainerChestDiamond(int id, Inventory playerInv, FriendlyByteBuf extraData) {
        this(id, playerInv, (TileChestDiamond) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos())));
    }

    public ContainerChestDiamond(int id, Inventory playerInv, TileChestDiamond te) {
        super(ModContainers.CHEST_DIAMOND_CONTAINER.get(), id);

        addSlots(te, 12, playerInv);
    }


}

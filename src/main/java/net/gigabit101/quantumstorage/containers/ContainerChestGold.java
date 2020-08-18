package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.tiles.chests.TileChestGold;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

import java.util.Objects;

public class ContainerChestGold extends ContainerChestBase {

    public ContainerChestGold(int id, PlayerInventory playerInv, PacketBuffer extraData) {
        this(id, playerInv, (TileChestGold) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerChestGold(int id, PlayerInventory playerInv, TileChestGold te) {
        super(QuantumStorage.containerChestGoldContainerType, id);

        addSlots(te, 9, playerInv);
    }


}

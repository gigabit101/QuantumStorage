package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.tiles.chests.TileChestDiamond;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

import java.util.Objects;

public class ContainerChestDiamond extends ContainerChestBase {

    public ContainerChestDiamond(int id, PlayerInventory playerInv, PacketBuffer extraData) {
        this(id, playerInv, (TileChestDiamond) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerChestDiamond(int id, PlayerInventory playerInv, TileChestDiamond te) {
        super(QuantumStorage.containerChestDiamondContainerType, id);

        addSlots(te, 12, playerInv);
    }


}

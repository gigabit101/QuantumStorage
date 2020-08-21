package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.tiles.chests.TileChestBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

import java.util.Objects;

public class ContainerChestIron extends ContainerChestBase {

    public ContainerChestIron(int id, PlayerInventory playerInv, PacketBuffer extraData) {
        this(id, playerInv, (TileChestBase) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerChestIron(int id, PlayerInventory playerInv, TileChestBase te) {
        super(QuantumStorage.containerChestIronContainerType, id);

        addSlots(te, 9, playerInv);
    }


}

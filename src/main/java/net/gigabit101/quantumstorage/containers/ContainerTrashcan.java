package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.tiles.TileTrashcan;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerTrashcan extends ContainerQS
{
    public ContainerTrashcan(int id, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(id, playerInv, (TileTrashcan) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerTrashcan(int id, PlayerInventory playerInv, TileTrashcan te)
    {
        super(QuantumStorage.containerTrashcanContainerType, id);

        addSlot(new SlotItemHandler(te.inventory,0, 87, 61));

        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }
}

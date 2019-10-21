package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.inventory.slot.SlotOutputItemHandler;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerQSU extends ContainerQS
{
    public IItemHandler inv;

    public ContainerQSU(int id, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(id, playerInv, (TileQsu) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerQSU(int id, PlayerInventory playerInv, TileQsu te)
    {
        super(QuantumStorage.containerQsuContainerType, id);
        this.inv = te.inventory;

        addSlot(new SlotItemHandler(te.inventory,0, 87, 31));
        addSlot(new SlotOutputItemHandler(te.inventory,2, 87, 81));
    
        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }
    
    public IItemHandler getInv()
    {
        return this.inv;
    }
}

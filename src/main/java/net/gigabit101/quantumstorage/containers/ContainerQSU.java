package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.containers.slots.SlotFiltered;
import net.gigabit101.quantumstorage.init.ModContainers;
import net.gigabit101.quantumstorage.inventory.slot.SlotOutputItemHandler;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.IItemHandler;

import java.util.Objects;

public class ContainerQSU extends ContainerQS
{
    public IItemHandler inv;
    public TileQsu tileQsu;

    public ContainerQSU(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (TileQsu) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos())));
    }

    public ContainerQSU(int id, Inventory playerInv, TileQsu te)
    {
        super(ModContainers.QSU_CONTAINER.get(), id);
        tileQsu = te;
        this.inv = te.inventory;

        addSlot(new SlotFiltered(te.inventory,0, 87, 31, te.inventory.getStackInSlot(0)));
        addSlot(new SlotOutputItemHandler(te.inventory,2, 87, 81));
    
        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
    }
    
    public IItemHandler getInv()
    {
        return this.inv;
    }
}

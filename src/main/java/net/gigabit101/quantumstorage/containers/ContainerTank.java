package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.init.ModContainers;
import net.gigabit101.quantumstorage.inventory.slot.SlotOutputItemHandler;
import net.gigabit101.quantumstorage.tiles.TileTank;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerTank extends ContainerQS
{
    public IFluidTank tank;
    
    public ContainerTank(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (TileTank) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos())));
    }

    public ContainerTank(int id, Inventory playerInv, TileTank te)
    {
        super(ModContainers.TANK_CONTAINER.get(), id);
        this.tank = te.tank;

        addSlot(new SlotItemHandler(te.inventory,0, 87, 31));
        addSlot(new SlotOutputItemHandler(te.inventory,1, 87, 81));
    
        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
    }
    
    public IFluidTank getTank()
    {
        return tank;
    }
}

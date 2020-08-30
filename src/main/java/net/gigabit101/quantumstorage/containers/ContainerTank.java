package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.inventory.slot.SlotOutputItemHandler;
import net.gigabit101.quantumstorage.tiles.TileTank;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerTank extends ContainerQS
{
    public IFluidTank tank;
    
    public ContainerTank(int id, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(id, playerInv, (TileTank) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerTank(int id, PlayerInventory playerInv, TileTank te)
    {
        super(QuantumStorage.containerTankContainerType, id);
        this.tank = te.tank;

        addSlot(new SlotItemHandler(te.inventory,0, 87, 31));
        addSlot(new SlotOutputItemHandler(te.inventory,1, 87, 81));
    
        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }
    
    public IFluidTank getTank()
    {
        return tank;
    }
}

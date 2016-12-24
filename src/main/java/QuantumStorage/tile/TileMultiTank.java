package QuantumStorage.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import reborncore.common.network.packet.CustomDescriptionPacket;
import reborncore.common.util.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 16/11/2016.
 */
public class TileMultiTank extends TileEntity
{
    public  Tank tank1 = new Tank("tank1", 16000, this);
    public  Tank tank2 = new Tank("tank2", 16000, this);
    public  Tank tank3 = new Tank("tank3", 16000, this);
    public  Tank tank4 = new Tank("tank4", 16000, this);
    public  Tank tank5 = new Tank("tank4", 16000, this);
    public  Tank tank6 = new Tank("tank4", 16000, this);


    public TileMultiTank() {}

    public List<Tank> getTanks()
    {
        List<Tank> tanks = new ArrayList<Tank>();
        tanks.add(tank1);
        tanks.add(tank2);
        tanks.add(tank3);
        tanks.add(tank4);
        tanks.add(tank5);
        tanks.add(tank6);
        return tanks;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        readFromNBTWithoutCoords(tagCompound);
        super.readFromNBT(tagCompound);
    }

    public void readFromNBTWithoutCoords(NBTTagCompound tagCompound)
    {
        for(Tank tank : this.getTanks())
        {
            tank.readFromNBT(tagCompound);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        writeToNBTWithoutCoords(compound);
        return super.writeToNBT(compound);
    }

    public void writeToNBTWithoutCoords(NBTTagCompound tagCompound)
    {
        for(Tank tank : this.getTanks())
        {
            tank.writeToNBT(tagCompound);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            if(facing == EnumFacing.UP) return (T) tank1;
            if(facing == EnumFacing.DOWN) return (T) tank2;
            if(facing == EnumFacing.EAST) return (T) tank3;
            if(facing == EnumFacing.SOUTH) return (T) tank4;
            if(facing == EnumFacing.WEST) return (T) tank5;
            if(facing == EnumFacing.NORTH) return (T) tank6;
        }
        return super.getCapability(capability, facing);
    }

    public void syncWithAll()
    {
        if (!worldObj.isRemote)
        {
            reborncore.common.network.NetworkManager.sendToAllAround(new CustomDescriptionPacket(this), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), getPos().getX(), getPos().getY(), getPos().getZ(), 20));;
        }
    }
}

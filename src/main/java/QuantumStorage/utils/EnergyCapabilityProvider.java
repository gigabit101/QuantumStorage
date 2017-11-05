package QuantumStorage.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 07/07/2017.
 */
public class EnergyCapabilityProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound>
{
    final EnergyNbt storage;

    public EnergyCapabilityProvider(int cap)
    {
        this.storage = new EnergyNbt(cap, 1000);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityEnergy.ENERGY)
        {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityEnergy.ENERGY)
        {
            return CapabilityEnergy.ENERGY.cast(storage);
        }
        return null;
    }


    @Override
    public NBTTagCompound serializeNBT()
    {
        return this.storage.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        this.storage.deserializeNBT(nbt);
    }
}

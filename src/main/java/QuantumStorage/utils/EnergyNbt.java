package QuantumStorage.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

/**
 * Created by Gigabit101
 */
public class EnergyNbt extends EnergyStorage implements INBTSerializable<NBTTagCompound>
{
    public EnergyNbt(int capacity)
    {
        super(capacity);
    }

    public EnergyNbt(int capacity, int maxTransfer)
    {
        super(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyNbt(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract, 0);
    }

    public EnergyNbt(int capacity, int maxReceive, int maxExtract, int energy)
    {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setAmount(int amount)
    {
        this.energy = amount;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        final NBTTagCompound dataTag = new NBTTagCompound();
        dataTag.setLong("ForgePower", this.energy);
        dataTag.setLong("ForgeCapacity", this.capacity);
        dataTag.setLong("ForgeInput", this.maxReceive);
        dataTag.setLong("ForgeOutput", this.maxExtract);

        return dataTag;
    }

    @Override
    public void deserializeNBT (NBTTagCompound nbt)
    {
        this.energy = nbt.getInteger("ForgePower");

        if (nbt.hasKey("ForgeCapacity"))
            this.capacity = nbt.getInteger("ForgeCapacity");

        if (nbt.hasKey("ForgeInput"))
            this.maxReceive = nbt.getInteger("ForgeInput");

        if (nbt.hasKey("ForgeOutput"))
            this.maxExtract = nbt.getInteger("ForgeOutput");

        if (this.energy > this.getMaxEnergyStored())
            this.energy = this.getMaxEnergyStored();
    }
}

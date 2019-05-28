package QuantumStorage.api;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public interface IQuantumBagProvider extends INBTSerializable<NBTTagCompound>
{
    @Nonnull
    IItemHandler getBag(@Nonnull EnumDyeColor color);
    
    void sync(EnumDyeColor color, @Nonnull EntityPlayerMP player);
}

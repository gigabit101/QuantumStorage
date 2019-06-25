package QuantumStorage.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public interface IQuantumBagProvider extends INBTSerializable<CompoundNBT>
{
    @Nonnull
    IItemHandler getBag(@Nonnull DyeColor color);
    
    void sync(DyeColor color, @Nonnull PlayerEntity player);
}

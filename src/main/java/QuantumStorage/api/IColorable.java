package QuantumStorage.api;

import net.minecraft.item.ItemStack;

public interface IColorable
{
    int getColor(ItemStack stack, int tint);
}

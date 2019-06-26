package QuantumStorage.api;

import net.minecraft.item.ItemStack;
@Deprecated
public interface IColorable
{
    int getColorFromItemStack(ItemStack stack, int tintIndex);
}

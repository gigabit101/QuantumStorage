package QuantumStorage.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class InventoryTrashcan extends ItemStackHandler
{
    public InventoryTrashcan()
    {
        super(1);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        return ItemStack.EMPTY;
    }
}

package net.gigabit101.quantumstorage.inventory;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class InventoryTrashcan extends ItemStackHandler
{
    public InventoryTrashcan()
    {
        super(1);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
    {
        return ItemStack.EMPTY;
    }
}

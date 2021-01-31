package net.gigabit101.quantumstorage.inventory.slot;

import net.gigabit101.quantumstorage.items.backpack.ItemQuantumBag;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotAntiBag extends SlotItemHandler
{
    public SlotAntiBag(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        if(stack.getItem() instanceof ItemQuantumBag) return false;
        return true;
    }
}

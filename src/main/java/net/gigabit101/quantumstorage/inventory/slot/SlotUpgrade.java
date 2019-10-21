package net.gigabit101.quantumstorage.inventory.slot;

import net.gigabit101.quantumstorage.items.ItemUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Created by Gigabit101 on 30/07/2017.
 */
public class SlotUpgrade extends SlotItemHandler
{
    public SlotUpgrade(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        if (stack.getItem() instanceof ItemUpgrade)
        {
            return true;
        }
        return false;
    }
}

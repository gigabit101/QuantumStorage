package QuantumStorage.inventory.slot;

import QuantumStorage.items.ItemCrate;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Created by Gigabit101 on 30/07/2017.
 */
public class SlotCrate extends SlotItemHandler
{
    public SlotCrate(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        if (stack.getItem() instanceof ItemCrate)
        {
            return true;
        }
        return false;
    }
}

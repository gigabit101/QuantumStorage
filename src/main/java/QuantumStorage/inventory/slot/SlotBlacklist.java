package QuantumStorage.inventory.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.util.ItemUtils;

import javax.annotation.Nonnull;

/**
 * Created by Gigabit101 on 30/07/2017.
 */
public class SlotBlacklist extends SlotItemHandler
{
    ItemStack blacklist;
    
    public SlotBlacklist(IItemHandler itemHandler, int index, int xPosition, int yPosition, ItemStack blacklist)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.blacklist = blacklist;
    }
    
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        if (ItemUtils.isItemEqual(stack, blacklist, false, false))
        {
            return false;
        }
        return true;
    }
}

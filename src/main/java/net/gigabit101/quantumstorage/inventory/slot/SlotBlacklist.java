package net.gigabit101.quantumstorage.inventory.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;


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
    public boolean mayPlace(@NotNull ItemStack stack)
    {
        if (stack.sameItem(blacklist))
        {
            return false;
        }
        return true;
    }
}

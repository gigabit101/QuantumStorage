package net.gigabit101.quantumstorage.inventory.slot;

import net.gigabit101.quantumstorage.init.QSItems;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotAntiCrate extends SlotItemHandler
{
    public SlotAntiCrate(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        if (ItemUtils.isItemEqual(stack, new ItemStack(QSItems.CHEST_IRON_ITEM.get()), false)) return false;
        if (ItemUtils.isItemEqual(stack, new ItemStack(QSItems.CHEST_GOLD_ITEM.get()), false)) return false;
        if (ItemUtils.isItemEqual(stack, new ItemStack(QSItems.CHEST_DIAMOND_ITEM.get()), false)) return false;

        return true;
    }
}

package net.gigabit101.quantumstorage.containers.slots;

import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFiltered extends SlotItemHandler
{
    ItemStack filteredStack;

    public SlotFiltered(IItemHandler inventoryIn, int index, int xPosition, int yPosition, ItemStack filteredStack)
    {
        super(inventoryIn, index, xPosition, yPosition);
        this.filteredStack = filteredStack;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if(filteredStack.isEmpty() || ItemUtils.isItemEqual(stack, filteredStack, true))
            return super.isItemValid(stack);

        return false;
    }
}

package net.gigabit101.quantumstorage;

import net.minecraft.item.ItemStack;

public class ItemUtils
{
    public static boolean isItemEqual(final ItemStack a, final ItemStack b, final boolean matchNBT) {
        if (a.isEmpty() || b.isEmpty())
            return false;
        if (a.getItem() != b.getItem())
            return false;
        if (matchNBT && !ItemStack.areItemStackTagsEqual(a, b))
            return false;
        return true;
    }
    
    public static boolean isEmpty(ItemStack stack)
    {
        return stack == ItemStack.EMPTY || stack == null || stack.getCount() <= 0;
    }
    
    public static int getSize(ItemStack stack)
    {
        return isEmpty(stack) ? 0 : stack.getCount();
    }
    
    public static ItemStack setSize(ItemStack stack, int size)
    {
        if (size <= 0) return ItemStack.EMPTY;
        stack.setCount(size);
        return stack;
    }
    
    public static ItemStack increaseSize(ItemStack stack, int amount)
    {
        return setSize(stack, getSize(stack) + amount);
    }
    
    public static ItemStack increaseSize(ItemStack stack)
    {
        return increaseSize(stack, 1);
    }
    
    public static ItemStack decreaseSize(ItemStack stack, int amount)
    {
        return setSize(stack, getSize(stack) - amount);
    }
    
    public static ItemStack decreaseSize(ItemStack stack)
    {
        return decreaseSize(stack, 1);
    }
    
    public static ItemStack copyWithSize(ItemStack stack, int size)
    {
        if (isEmpty(stack)) return ItemStack.EMPTY;
        return setSize(stack.copy(), size);
    }
}

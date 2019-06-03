package QuantumStorage.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class SortingHandler
{
    public static void sortInventory(IItemHandler handler) {
        sortInventory(handler, 0);
    }
    
    public static void sortInventory(IItemHandler handler, int iStart) {
        sortInventory(handler, iStart, handler.getSlots());
    }
    
    public static void sortInventory(IItemHandler handler, int iStart, int iEnd) {
        List<ItemStack> stacks = new ArrayList<>();
        List<ItemStack> restore = new ArrayList<>();
        
        for (int i = iStart; i < iEnd; i++) {
            ItemStack stackAt = handler.getStackInSlot(i);
            restore.add(stackAt.copy());
            if (!stackAt.isEmpty())
                stacks.add(stackAt.copy());
        }
        
        mergeStacks(stacks);
        
        if (setInventory(handler, stacks, iStart, iEnd) == EnumActionResult.FAIL)
            setInventory(handler, restore, iStart, iEnd);
    }
    
    private static EnumActionResult setInventory(IItemHandler inventory, List<ItemStack> stacks, int iStart, int iEnd)
    {
        for (int i = iStart; i < iEnd; i++) {
            int j = i - iStart;
            ItemStack stack = j >= stacks.size() ? ItemStack.EMPTY : stacks.get(j);
            
            inventory.extractItem(i, 64, false);
            if (!stack.isEmpty())
                if (!inventory.insertItem(i, stack, false).isEmpty())
                    return EnumActionResult.FAIL;
        }
        return EnumActionResult.SUCCESS;
    }
    
    private static void mergeStacks(List<ItemStack> list) {
        for (int i = 0; i < list.size(); i++) {
            ItemStack set = mergeStackWithOthers(list, i);
            list.set(i, set);
        }
        list.removeIf((ItemStack stack) -> stack.isEmpty() || stack.getCount() == 0);
    }
    
    private static ItemStack mergeStackWithOthers(List<ItemStack> list, int index) {
        ItemStack stack = list.get(index);
        if (stack.isEmpty())
            return stack;
        
        for (int i = 0; i < list.size(); i++) {
            if (i == index)
                continue;
            
            ItemStack stackAt = list.get(i);
            if (stackAt.isEmpty())
                continue;
            
            if (stackAt.getCount() < stackAt.getMaxStackSize() && ItemStack.areItemsEqual(stack, stackAt) && ItemStack.areItemStackTagsEqual(stack, stackAt)) {
                int setSize = stackAt.getCount() + stack.getCount();
                int carryover = Math.max(0, setSize - stackAt.getMaxStackSize());
                stackAt.setCount(carryover);
                stack.setCount(setSize - carryover);
                
                if (stack.getCount() == stack.getMaxStackSize())
                    return stack;
            }
        }
        return stack;
    }
}

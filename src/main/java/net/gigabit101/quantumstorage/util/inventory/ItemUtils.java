package net.gigabit101.quantumstorage.util.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;

public class ItemUtils
{
    public static boolean isItemEqual(final ItemStack a, final ItemStack b, final boolean matchNBT) {
        if (a.isEmpty() || b.isEmpty())
            return false;
        if (a.getItem() != b.getItem())
            return false;
        if (matchNBT && !ItemStack.matches(a, b))
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

    public static void dropInventory(Level world, ItemStackHandler inventory, BlockPos pos, boolean motion, int pickupDelay) {
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            dropItem(inventory.getStackInSlot(slot), world, pos, motion, pickupDelay);
        }
    }

    public static void dropItem(ItemStack stack, Level world, BlockPos pos, boolean motion, int pickupDelay) {
        if (world.isClientSide) return;

        if (stack.getMaxStackSize() <= 0 || stack.isEmpty())
            return;

        ItemEntity entityitem = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        entityitem.setPickUpDelay(pickupDelay);
        if (!motion) {
            entityitem.setDeltaMovement(0, 0, 0);
        }

        world.addFreshEntity(entityitem);
    }
}

package net.gigabit101.quantumstorage.util.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IterableInventory implements Iterable<ItemStack>
{
    private final IInventory inventory;

    private IterableInventory(IInventory inventory)
    {
        this.inventory = inventory;
    }

    @Override
    public Iterator<ItemStack> iterator()
    {
        return new InventoryIterator(inventory);
    }

    public static IterableInventory from(IInventory inventoryIn)
    {
        return new IterableInventory(inventoryIn);
    }

    private static class InventoryIterator implements Iterator<ItemStack>
    {

        private final IInventory inventory;
        private int position;

        public InventoryIterator(IInventory inventory)
        {
            this.inventory = inventory;
            this.position = 0;
        }

        @Override
        public boolean hasNext()
        {
            return position < inventory.getSizeInventory();
        }

        @Override
        public ItemStack next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException("Inventory out of bounds");
            }
            position++;
            return inventory.getStackInSlot(position);
        }
    }
}

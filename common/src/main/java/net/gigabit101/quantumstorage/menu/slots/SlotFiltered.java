package net.gigabit101.quantumstorage.menu.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class SlotFiltered extends Slot
{
    public ItemStack itemStack;

    public SlotFiltered(Container container, int i, int j, int k, Supplier<ItemStack> itemStackSupplier)
    {
        super(container, i, j, k);
        this.itemStack = itemStackSupplier.get();
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        if(this.itemStack.isEmpty()) return true;

        return ItemStack.isSame(this.itemStack, itemStack);
    }
}

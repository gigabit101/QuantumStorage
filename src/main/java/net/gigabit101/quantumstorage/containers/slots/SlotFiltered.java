package net.gigabit101.quantumstorage.containers.slots;

import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SlotFiltered extends SlotItemHandler
{
    ItemStack filteredStack;

    public SlotFiltered(IItemHandler inventoryIn, int index, int xPosition, int yPosition, ItemStack filteredStack)
    {
        super(inventoryIn, index, xPosition, yPosition);
        this.filteredStack = filteredStack;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack)
    {
        if(filteredStack.isEmpty() || ItemUtils.isItemEqual(stack, filteredStack, true))
            return super.mayPlace(stack);

        return false;
    }
}

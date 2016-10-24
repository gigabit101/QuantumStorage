package QuantumStorage.client.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import reborncore.common.util.ItemUtils;

import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 24/10/2016.
 */
public class SlotFiltered extends Slot
{
    ItemStack filter;

    public SlotFiltered(IInventory inventoryIn, int index, int xPosition, int yPosition, ItemStack stack)
    {
        super(inventoryIn, index, xPosition, yPosition);
        if(stack != null)
            this.filter = stack;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack)
    {
        if(filter != null)
        {
            if(ItemUtils.isItemEqual(stack, filter, true, true))
            {
                return super.isItemValid(stack);
            }
            return false;
        }
        return super.isItemValid(stack);
    }
}

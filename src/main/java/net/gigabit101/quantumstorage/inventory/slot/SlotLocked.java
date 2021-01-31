package net.gigabit101.quantumstorage.inventory.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotLocked extends Slot
{
    public SlotLocked(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }

    @Override
    public boolean canTakeStack(PlayerEntity player)
    {
        return false;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }
}

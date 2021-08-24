package net.gigabit101.quantumstorage.menu.slots;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotNoTake extends Slot
{
    public SlotNoTake(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPickup(Player player)
    {
        return false;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return false;
    }
}

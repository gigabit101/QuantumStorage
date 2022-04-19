package net.gigabit101.quantumstorage.inventory.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Created by Gigabit101 on 18/05/2017.
 */
public class SlotOutputItemHandler extends SlotItemHandler
{
    public SlotOutputItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Override
    public boolean mayPlace(@NotNull ItemStack stack)
    {
        return false;
    }
}

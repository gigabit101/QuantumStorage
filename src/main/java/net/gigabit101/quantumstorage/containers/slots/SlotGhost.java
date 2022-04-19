package net.gigabit101.quantumstorage.containers.slots;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class SlotGhost extends SlotItemHandler
{
    public SlotGhost(IItemHandler inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    @Override
    public boolean mayPickup(Player playerIn)
    {
        return false;
    }
}

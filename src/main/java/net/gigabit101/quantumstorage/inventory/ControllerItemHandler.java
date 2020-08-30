package net.gigabit101.quantumstorage.inventory;

import net.gigabit101.quantumstorage.tiles.TileController;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ControllerItemHandler extends ItemStackHandler
{
    TileController controller;

    public ControllerItemHandler(TileController controller)
    {
        super(controller.getConnectedTiles().size());
        this.controller = controller;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        return controller.insertItem(stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return controller.extractItem(slot, amount, simulate);
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return controller.getStackInSlot(slot);
    }
}

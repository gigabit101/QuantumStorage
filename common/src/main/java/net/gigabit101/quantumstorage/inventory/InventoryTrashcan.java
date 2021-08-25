package net.gigabit101.quantumstorage.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InventoryTrashcan implements Container
{
    private final int size;
    private final NonNullList<ItemStack> items;
    private List<ContainerListener> listeners;

    public InventoryTrashcan(int i)
    {
        this.size = i;
        this.items = NonNullList.withSize(i, ItemStack.EMPTY);
    }

    public InventoryTrashcan(ItemStack... itemStacks)
    {
        this.size = itemStacks.length;
        this.items = NonNullList.of(ItemStack.EMPTY, itemStacks);
    }

    @Override
    public int getContainerSize()
    {
        return 1;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public ItemStack getItem(int i)
    {
        return i >= 0 && i < this.items.size() ? (ItemStack)this.items.get(i) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {

    }
}

package net.gigabit101.quantumstorage.menu;

import com.google.common.collect.Lists;
import com.mojang.math.Constants;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryQuantumStorageUnit implements Container, StackedContentsCompatible
{
    public int INPUT_SLOT = 1;
    public int STORAGE_SLOT = 2;
    public int OUTPUT_SLOT = 3;
    private final int size;
    private final NonNullList<ItemStack> items;
    private List<ContainerListener> listeners;
    private int STORAGE_COUNT;

    public InventoryQuantumStorageUnit(int i)
    {
        this.size = i;
        this.items = NonNullList.withSize(i, ItemStack.EMPTY);
    }

    public InventoryQuantumStorageUnit(ItemStack... itemStacks)
    {
        this.size = itemStacks.length;
        this.items = NonNullList.of(ItemStack.EMPTY, itemStacks);
    }

    public void addListener(ContainerListener containerListener)
    {
        if (this.listeners == null) this.listeners = Lists.newArrayList();
        this.listeners.add(containerListener);
    }

    public void removeListener(ContainerListener containerListener)
    {
        this.listeners.remove(containerListener);
    }

    @Override
    public ItemStack getItem(int i)
    {
        return i >= 0 && i < this.items.size() ? (ItemStack)this.items.get(i) : ItemStack.EMPTY;
    }

    public List<ItemStack> removeAllItems() {
        List<ItemStack> list = (List)this.items.stream().filter((itemStack) -> {
            return !itemStack.isEmpty();
        }).collect(Collectors.toList());
        this.clearContent();
        return list;
    }

    @Override
    public ItemStack removeItem(int i, int j)
    {
        ItemStack itemStack = ContainerHelper.removeItem(this.items, i, j);
        if (!itemStack.isEmpty()) this.setChanged();
        return itemStack;
    }

    public ItemStack removeItemType(Item item, int i)
    {
        ItemStack itemStack = new ItemStack(item, 0);

        for(int j = this.size - 1; j >= 0; --j)
        {
            ItemStack itemStack2 = this.getItem(j);
            if (itemStack2.getItem().equals(item))
            {
                int k = i - itemStack.getCount();
                ItemStack itemStack3 = itemStack2.split(k);
                itemStack.grow(itemStack3.getCount());
                if (itemStack.getCount() == i)
                {
                    break;
                }
            }
        }

        if (!itemStack.isEmpty()) this.setChanged();
        return itemStack;
    }

    public ItemStack addItem(ItemStack itemStack)
    {
        ItemStack itemStack2 = itemStack.copy();
        this.moveItemToOccupiedSlotsWithSameType(itemStack2);
        if (itemStack2.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        else
        {
            this.moveItemToEmptySlots(itemStack2);
            return itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2;
        }
    }

    public boolean canAddItem(ItemStack itemStack)
    {
        boolean bl = false;
        Iterator var3 = this.items.iterator();

        while(var3.hasNext())
        {
            ItemStack itemStack2 = (ItemStack)var3.next();
            if (itemStack2.isEmpty() || ItemStack.isSameItemSameTags(itemStack2, itemStack) && itemStack2.getCount() < itemStack2.getMaxStackSize())
            {
                bl = true;
                break;
            }
        }
        return bl;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i)
    {
        ItemStack itemStack = (ItemStack)this.items.get(i);
        if (itemStack.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        else
        {
            this.items.set(i, ItemStack.EMPTY);
            return itemStack;
        }
    }

    @Override
    public void setItem(int i, ItemStack itemStack)
    {
        this.items.set(i, itemStack);
        this.setChanged();
    }

    @Override
    public int getContainerSize()
    {
        return this.size;
    }

    @Override
    public boolean isEmpty()
    {
        Iterator var1 = this.items.iterator();

        ItemStack itemStack;
        do
        {
            if (!var1.hasNext())
            {
                return true;
            }
            itemStack = (ItemStack)var1.next();
        } while(itemStack.isEmpty());

        return false;
    }

    @Override
    public void setChanged()
    {
        if (this.listeners != null)
        {
            Iterator var1 = this.listeners.iterator();

            while(var1.hasNext())
            {
                ContainerListener containerListener = (ContainerListener)var1.next();
                containerListener.containerChanged(this);
            }
        }
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        this.items.clear();
        this.setChanged();
    }

    @Override
    public void fillStackedContents(StackedContents stackedContents)
    {
        Iterator var2 = this.items.iterator();
        while(var2.hasNext())
        {
            ItemStack itemStack = (ItemStack)var2.next();
            stackedContents.accountStack(itemStack);
        }
    }

    @Override
    public String toString()
    {
        return ((List)this.items.stream().filter((itemStack) ->
        {
            return !itemStack.isEmpty();
        }).collect(Collectors.toList())).toString();
    }

    private void moveItemToEmptySlots(ItemStack itemStack)
    {
        for(int i = 0; i < this.size; ++i)
        {
            ItemStack itemStack2 = this.getItem(i);
            if (itemStack2.isEmpty())
            {
                this.setItem(i, itemStack.copy());
                itemStack.setCount(0);
                return;
            }
        }

    }

    private void moveItemToOccupiedSlotsWithSameType(ItemStack itemStack)
    {
        for(int i = 0; i < this.size; ++i)
        {
            ItemStack itemStack2 = this.getItem(i);
            if (ItemStack.isSameItemSameTags(itemStack2, itemStack))
            {
                this.moveItemsBetweenStacks(itemStack, itemStack2);
                if (itemStack.isEmpty())
                {
                    return;
                }
            }
        }
    }

    private void moveItemsBetweenStacks(ItemStack itemStack, ItemStack itemStack2)
    {
        int i = Math.min(this.getMaxStackSize(), itemStack2.getMaxStackSize());
        int j = Math.min(itemStack.getCount(), i - itemStack2.getCount());
        if (j > 0)
        {
            itemStack2.grow(j);
            itemStack.shrink(j);
            this.setChanged();
        }
    }

    public CompoundTag serializeNBT()
    {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i) != null)
            {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                itemTag.putInt("SizeSpecial", items.get(i).getCount());
                items.get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", items.size());
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt)
    {
        ListTag tagList = nbt.getList("Items", 10);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < items.size())
            {
                items.set(slot, ItemStack.of(itemTags));
                items.get(slot).setCount(itemTags.getInt("SizeSpecial"));
            }
        }
    }

    public void tick()
    {
        increaseStorageStack();

    }

    public void increaseStorageStack()
    {
        if(getItem(INPUT_SLOT).isEmpty()) return;

        if(getItem(STORAGE_SLOT).isEmpty())
        {
            setItem(STORAGE_SLOT, getItem(INPUT_SLOT));
            setItem(STORAGE_SLOT, ItemStack.EMPTY);
            return;
        }

        if(getItem(STORAGE_SLOT).sameItem(getItem(INPUT_SLOT)))
        {
            getItem(STORAGE_SLOT).grow(getItem(INPUT_SLOT).getCount());
            setItem(STORAGE_SLOT, ItemStack.EMPTY);
            return;
        }
    }

    public void moveStackToSlot(int from, int too)
    {
        if(getItem(from).isEmpty()) return;
        if(getItem(too).isEmpty() && !getItem(from).isEmpty())
        {
            if(getItem(from).getCount() > getItem(from).getMaxStackSize())
            {
                ItemStack newStack = getItem(from);
                int size = getItem(from).getItem().getMaxStackSize();
                newStack.setCount(size);
//                getItem(from).shrink(size);
                setItem(too, newStack);
            }
        }
    }
}

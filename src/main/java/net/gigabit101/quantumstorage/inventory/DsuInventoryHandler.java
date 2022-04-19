package net.gigabit101.quantumstorage.inventory;

import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

/**
 * Created by Gigabit101 on 06/03/2017.
 */
public class DsuInventoryHandler extends ItemStackHandler
{
    int STORAGE = 0;
    ItemStack lockedStack;

    public DsuInventoryHandler(ItemStack lockedStack)
    {
        super(3);
        this.lockedStack = lockedStack;
    }

    @Override
    protected int getStackLimit(int slot, ItemStack stack)
    {
        if (slot == STORAGE)
        {
            return Integer.MAX_VALUE;
        }
        return 64;
    }

    @Override
    public CompoundTag serializeNBT()
    {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++)
        {
            if (stacks.get(i) != null)
            {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                itemTag.putInt("SizeSpecial", stacks.get(i).getCount());
                stacks.get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", stacks.size());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        setSize(nbt.contains("Size", Tag.TAG_INT) ? nbt.getInt("Size") : stacks.size());
        ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, ItemStack.of(itemTags));
                stacks.get(slot).setCount(itemTags.getInt("SizeSpecial"));
            }
        }
        onLoad();
    }

    public void updateLockedStack(ItemStack itemStack)
    {
        this.lockedStack = itemStack;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if(!lockedStack.isEmpty() && ItemUtils.isItemEqual(lockedStack, stack, false))
        {
            return super.insertItem(slot, stack, simulate);
        }
        if(lockedStack.isEmpty() && getStackInSlot(2).isEmpty() || lockedStack.isEmpty() &&!getStackInSlot(2).isEmpty() && ItemUtils.isItemEqual(getStackInSlot(2), stack, false))
        {
            return super.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return super.extractItem(slot, amount, simulate);
    }

    public boolean requestUpdate = false;

    @Override
    public void onContentsChanged(int slot)
    {
        super.onContentsChanged(slot);
        requestUpdate = true;
    }
}

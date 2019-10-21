package net.gigabit101.quantumstorage.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

/**
 * Created by Gigabit101 on 06/03/2017.
 */
public class DsuInventoryHandler extends ItemStackHandler
{
    int STORAGE = 0;

    public DsuInventoryHandler()
    {
        super(3);
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
    public CompoundNBT serializeNBT()
    {
        ListNBT nbtTagList = new ListNBT();
        for (int i = 0; i < stacks.size(); i++)
        {
            if (stacks.get(i) != null)
            {
                CompoundNBT itemTag = new CompoundNBT();
                itemTag.putInt("Slot", i);
                itemTag.putInt("SizeSpecial", stacks.get(i).getCount());
                stacks.get(i).write(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", stacks.size());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        setSize(nbt.contains("Size", Constants.NBT.TAG_INT) ? nbt.getInt("Size") : stacks.size());
        ListNBT tagList = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundNBT itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, ItemStack.read(itemTags));
                stacks.get(slot).setCount(itemTags.getInt("SizeSpecial"));
            }
        }
        onLoad();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if(getStackInSlot(2).isEmpty() || !getStackInSlot(2).isEmpty() && ItemStack.areItemStackTagsEqual(getStackInSlot(2), stack))
        {
            return super.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    public boolean requestUpdate = false;

    @Override
    public void onContentsChanged(int slot)
    {
        super.onContentsChanged(slot);
        requestUpdate = true;
    }
}

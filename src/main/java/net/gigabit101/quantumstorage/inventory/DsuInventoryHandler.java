package net.gigabit101.quantumstorage.inventory;

import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
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

package QuantumStorage.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

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
    public NBTTagCompound serializeNBT()
    {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < stacks.size(); i++)
        {
            if (stacks.get(i) != null)
            {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("Slot", i);
                itemTag.setInteger("SizeSpecial", stacks.get(i).getCount());
                stacks.get(i).writeToNBT(itemTag);
                nbtTagList.appendTag(itemTag);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", nbtTagList);
        nbt.setInteger("Size", stacks.size());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        setSize(nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : stacks.size());
        NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
            int slot = itemTags.getInteger("Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, new ItemStack(itemTags));
                stacks.get(slot).setCount(itemTags.getInteger("SizeSpecial"));
            }
        }
        onLoad();
    }
}

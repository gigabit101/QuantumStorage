package QuantumStorage.items;

import QuantumStorage.lib.ModInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 11/01/2017.
 */
public class ItemBag extends ItemQuantumStorage
{
    public ItemStackHandler inv = new StackHandler(5);

    public ItemBag()
    {
        setUnlocalizedName(ModInfo.MOD_ID + ".bag");
        setMaxStackSize(1);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new ICapabilityProvider()
        {
            @Override
            public boolean hasCapability(Capability<?> cap, @Nullable EnumFacing side)
            {
                if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    return true;
                return false;
            }

            @Override
            public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
            {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
            }
        };
    }

    class StackHandler extends ItemStackHandler
    {
        StackHandler(int size)
        {
            super(size);
        }
    }
}

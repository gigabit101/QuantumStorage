package QuantumStorage.multiblock;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class TileIoPort extends TileMultiStorage implements IItemHandler
{
    public static int MAX = 78;
    
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return getVarient().equals("io");
        }
        return super.hasCapability(capability, facing);
    }
    
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }
    
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        int page = slot / MAX + 1;
        slot %= MAX;
        IItemHandler handler = new InvWrapper(getMultiBlock().getInvForPage(page));
        return handler.extractItem(slot, amount, simulate);
    }
    
    @Override
    public int getSlotLimit(int slot)
    {
        return 64;
    }
    
    @Override
    public int getSlots()
    {
        return MAX * getMultiBlock().pages;
    }
    
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        int page = slot / MAX + 1;
        slot %= MAX;
        return getMultiBlock().getInvForPage(page).getStackInSlot(slot);
    }
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        int page = slot / MAX + 1;
        slot %= MAX;
        IItemHandler handler = new InvWrapper(getMultiBlock().getInvForPage(page));
        return handler.insertItem(slot, stack, simulate);
    }
}

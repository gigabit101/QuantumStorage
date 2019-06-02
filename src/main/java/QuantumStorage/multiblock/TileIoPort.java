package QuantumStorage.multiblock;

import QuantumStorage.inventory.CachingItemHandler;
import QuantumStorage.utils.SortingHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import reborncore.common.util.ItemUtils;

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
    public int getSlotLimit(int slot)
    {
        return 64;
    }
    
    private class Slot
    {
        private IItemHandler inv;
        private int slot;
        
        Slot(IItemHandler inv, int slot)
        {
            this.inv = inv;
            this.slot = slot;
        }
        
        public ItemStack extractItem(int amount, boolean simulate)
        {
            return inv.extractItem(slot, amount, simulate);
        }
        
        public ItemStack getStack()
        {
            return inv.getStackInSlot(slot);
        }
        
        public ItemStack insertItem(ItemStack stack, boolean simulate)
        {
            return inv.insertItem(slot, stack, simulate);
        }
    }
    
    public int page = -1;
    
    private Slot getFirstAvailable()
    {
        MultiBlockStorage multiBlock = getMultiBlock();
        if (multiBlock == null || !multiBlock.isAssembled())
        {
            return null;
        }
        for (int i = 1; i <= multiBlock.pages; ++i)
        {
            CachingItemHandler inv = multiBlock.getInvForPage(i);
            if (!inv.isFull())
            {
                page = i;
                return new Slot(inv, inv.getFirstAvailable());
            }
        }
        return null;
    }
    
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        int page = slot / MAX + 1;
        slot %= MAX;
        CachingItemHandler handler = getMultiBlock().getInvForPage(page);
        return handler.extractItem(slot, amount, simulate);
    }
    
    @Override
    public int getSlots()
    {
        try
        {
            return MAX * getMultiBlock().pages;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            world.removeTileEntity(pos);
        }
        return 0;
    }
    
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        int page = slot / MAX + 1;
        slot %= MAX;
        CachingItemHandler handler = getMultiBlock().getInvForPage(page);
        return handler.getStackInSlot(slot);
    }
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        if (slot != 0)
        {
            return stack;
        }
        else
        {
            Slot firstAvailable = getFirstAvailable();
            if(page != -1)
            {
                if(firstAvailable != null)
                {
                    SortingHandler.sortInventory(getMultiBlock().getInvForPage(page), 0, firstAvailable.slot);
                }
            }
            return firstAvailable == null ? stack : firstAvailable.insertItem(stack, simulate);
        }
    }
}

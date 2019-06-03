package QuantumStorage.multiblock;

import QuantumStorage.inventory.CachingItemHandler;
import QuantumStorage.utils.SortingHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import reborncore.common.util.ItemUtils;

import javax.annotation.Nullable;

public class TileIoPort extends TileMultiStorage implements IItemHandler
{
    public static int slotsPerPage = 78;
    
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
    
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        int page = slot / slotsPerPage + 1;
        slot %= slotsPerPage;
        CachingItemHandler handler = getMultiBlock().getInvForPage(page);
        return handler.extractItem(slot, amount, simulate);
    }
    
    @Override
    public int getSlots()
    {
        if(this.getMultiBlock().getLie() == 0)
            getMultiBlock().lie();
        
        try
        {
            return (slotsPerPage * getMultiBlock().getLie());
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
        int page = slot / slotsPerPage + 1;
        slot %= slotsPerPage;
        CachingItemHandler handler = getMultiBlock().getInvForPage(page);
        return handler.getStackInSlot(slot);
    }
    
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        int page = slot / slotsPerPage + 1;
        slot %= slotsPerPage;
        if(!getMultiBlock().getInvForPage(getMultiBlock().getLie()).getStackInSlot(slotsPerPage -1).isEmpty())
        {
            getMultiBlock().lie();
        }
        return getMultiBlock().getInvForPage(page).insertItem(slot, stack, simulate);
    }
}

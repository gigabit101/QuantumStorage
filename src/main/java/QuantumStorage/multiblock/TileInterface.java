package QuantumStorage.multiblock;

import QuantumStorage.inventory.CachingItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TileInterface extends TileMultiStorage implements IItemHandler
{
    public static int MAX = 78;
    
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return getVarient().equals("interface");
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
            world.setTileEntity(pos, this);
            getSlots();
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
//        int page = slot / MAX + 1;
//        slot %= MAX;
//        CachingItemHandler handler = getMultiBlock().getInvForPage(page);
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        int page = slot / MAX + 1;
        slot %= MAX;
        CachingItemHandler handler = getMultiBlock().getInvForPage(page);
        return handler.extractItem(slot, amount, simulate);
    }
}

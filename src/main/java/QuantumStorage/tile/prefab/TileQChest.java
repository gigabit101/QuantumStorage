package QuantumStorage.tile.prefab;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import reborncore.common.util.Inventory;

import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 01/11/2016.
 */
public abstract class TileQChest extends TileEntity implements IInventory
{
    int invSize;

    Inventory inv;// = new Inventory(invSize, "", 64, this);

    public Inventory getInv()
    {
        return this.inv;
    }

    public void setInv(Inventory inv)
    {
        this.inv = inv;
    }

    @Override
    public int getSizeInventory()
    {
        return inv.getSizeInventory();
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return inv.getStackInSlot(index);
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return inv.decrStackSize(index, count);
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return inv.removeStackFromSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        inv.setInventorySlotContents(index, stack);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return inv.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return inv.isItemValidForSlot(index, stack);
    }

    @Override
    public int getField(int id)
    {
        return inv.getField(id);
    }

    @Override
    public void setField(int id, int value)
    {
        inv.setField(id, value);
    }

    @Override
    public int getFieldCount()
    {
        return inv.getFieldCount();
    }

    @Override
    public void clear()
    {
        inv.clear();
    }

    @Override
    public String getName()
    {
        return inv.getName();
    }

    @Override
    public boolean hasCustomName()
    {
        return inv.hasCustomName();
    }

    public int getInvSize()
    {
        return invSize;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(getPos(), getBlockMetadata(), writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
    {
        readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        inv.writeToNBT(compound);
        System.out.print("---------------------SAVED NBT-------------------------------");
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        inv.readFromNBT(compound);
        System.out.print("---------------------LOADED NBT------------------------------");
    }

//    @Override
//    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
//    {
//        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//        {
//            return true;
//        }
//        return super.hasCapability(capability, facing);
//    }
//
//    @Override
//    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
//    {
//        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//        {
//            return (T) new InventoryWrapper(this);
//        }
//        return super.getCapability(capability, facing);
//    }
}

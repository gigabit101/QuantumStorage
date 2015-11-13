package QuantumStorage.block.tile;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import QuantumStorage.QuantumStorage;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.packet.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.api.impl.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;
import reborncore.common.util.Inventory;
import reborncore.common.util.ItemUtils;

public class TileQuantumChest extends TileEntity implements IInventory 
{
	public Inventory inventory = new Inventory(54, "TileQuantumChest", Integer.MAX_VALUE);
	
	@Override
	public int getSizeInventory() 
	{
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slotId) 
	{
		return inventory.getStackInSlot(slotId);
	}

	@Override
	public ItemStack decrStackSize(int slotId, int count) 
	{
		return inventory.decrStackSize(slotId, count);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotId) 
	{
		return inventory.getStackInSlotOnClosing(slotId);
	}

	@Override
	public void setInventorySlotContents(int slotId, ItemStack itemstack) 
	{
		inventory.setInventorySlotContents(slotId, itemstack);
	}

	@Override
	public String getInventoryName() 
	{
		return inventory.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return inventory.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void closeInventory() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) 
	{
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) 
	{
		super.readFromNBT(tagCompound);
		readFromNBTWithoutCoords(tagCompound);
	}

	public void readFromNBTWithoutCoords(NBTTagCompound tagCompound) 
	{
		inventory.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		writeToNBTWithoutCoords(tagCompound);
	}

	public void writeToNBTWithoutCoords(NBTTagCompound tagCompound) 
	{
		inventory.writeToNBT(tagCompound);
	}
	
	public ItemStack getDropWithNBT() 
	{
		NBTTagCompound tileEntity = new NBTTagCompound();
		ItemStack dropStack = new ItemStack(ModBlocks.QuantumChest, 1);
		writeToNBTWithoutCoords(tileEntity);
		dropStack.setTagCompound(new NBTTagCompound());
		dropStack.stackTagCompound.setTag("tileEntity", tileEntity);
		return dropStack;
	}
}

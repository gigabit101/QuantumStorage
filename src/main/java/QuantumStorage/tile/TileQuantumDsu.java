package QuantumStorage.tile;

import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.tile.prefab.TileQuantumStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;
import reborncore.common.network.packet.CustomDescriptionPacket;
import reborncore.common.util.Inventory;
import reborncore.common.util.ItemUtils;

import java.util.List;

public class TileQuantumDsu extends TileQuantumStorage implements IInventory, IDeepStorageUnit, ISidedInventory
{
	int storage = ConfigQuantumStorage.dsuMaxStorage;

	public Inventory inventory = new Inventory(3, "TileQuantumDsu", storage, this);

	public ItemStack storedItem;
	public String storedItemAsString;
	public boolean isLocked;
	public ItemStack lockedStack;
	
	@Override
	public void update() 
	{
		if (!worldObj.isRemote) 
		{
			if (storedItem != null) 
			{
				ItemStack fakeStack = storedItem.copy();
				fakeStack.stackSize = 1;
				setInventorySlotContents(2, fakeStack);
			} 
			else if (storedItem == null && getStackInSlot(1) != null)
			{
				ItemStack fakeStack = getStackInSlot(1).copy();
				fakeStack.stackSize = 1;
				setInventorySlotContents(2, fakeStack);
			}
			else
			{
				setInventorySlotContents(2, null);
			}

			if (getStackInSlot(0) != null) 
			{
				if (storedItem == null) 
				{
					storedItem = getStackInSlot(0);
					setInventorySlotContents(0, null);
				} 
				else if (ItemUtils.isItemEqual(storedItem, getStackInSlot(0), true, true)) 
				{
					if (storedItem.stackSize <= storage - getStackInSlot(0).stackSize) 
					{
						storedItem.stackSize += getStackInSlot(0).stackSize;
						decrStackSize(0, getStackInSlot(0).stackSize);
					}
				}
			}

            //TODO move this change to 1.11
			if (storedItem != null && getStackInSlot(1) == null) 
			{
				ItemStack itemStack = storedItem.copy();
                if(storedItem.stackSize >= itemStack.getMaxStackSize())
                {
                    itemStack.stackSize = itemStack.getMaxStackSize();
                    storedItem.stackSize -= itemStack.getMaxStackSize();
                    setInventorySlotContents(1, itemStack);
                }
                else
                {
                    storedItem = null;
                    setInventorySlotContents(1, itemStack);
                }
			}
			else if (ItemUtils.isItemEqual(getStackInSlot(1), storedItem, true, true)) 
			{
				int wanted = getStackInSlot(1).getMaxStackSize() - getStackInSlot(1).stackSize;
				if (storedItem.stackSize >= wanted) 
				{
                    storedItem.stackSize -= wanted;
                    decrStackSize(1, -wanted);
				}
				else 
				{
					decrStackSize(1, -storedItem.stackSize);
					storedItem = null;
				}
			}
		}
		if(inventory.hasChanged)
		{
			syncWithAll();
			inventory.hasChanged = false;
		}
	}
	
	public void lock()
	{
		if(getStackInSlot(1) != null)
		{
			lockedStack = getStackInSlot(2);
			isLocked = true;
			System.out.println("LOCKED");
		}
	}
	
	public void unlock()
	{
		lockedStack = null;
		isLocked = false;
		System.out.println("UNLOCKED");
	}

	public Packet getDescriptionPacket() 
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(this.pos, 0, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) 
	{
		readFromNBT(packet.getNbtCompound());
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

		storedItem = null;
		storedItemAsString = null;

		if (tagCompound.hasKey("storedStack")) 
		{
			storedItem = ItemStack.loadItemStackFromNBT((NBTTagCompound) tagCompound.getTag("storedStack"));
		}

		if (storedItem != null) 
		{
			storedItem.stackSize = tagCompound.getInteger("storedQuantity");
			storedItemAsString = tagCompound.getString("storedItemAsString");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		writeToNBTWithoutCoords(tagCompound);
		return super.writeToNBT(tagCompound);
	}

	public void writeToNBTWithoutCoords(NBTTagCompound tagCompound) 
	{
		inventory.writeToNBT(tagCompound);
		if (storedItem != null) 
		{
			tagCompound.setTag("storedStack", storedItem.writeToNBT(new NBTTagCompound()));
			tagCompound.setInteger("storedQuantity", storedItem.stackSize);
			tagCompound.setString("storedItemAsString", storedItem.getDisplayName());
		} else
			tagCompound.setInteger("storedQuantity", 0);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T) new SidedInvWrapper(this, facing);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public int getSizeInventory() 
	{
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slotId, int count) 
	{
		return inventory.decrStackSize(slotId, count);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) 
	{
		inventory.setInventorySlotContents(slot, stack);
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		if(slot == 2)
		{
			return false;
		}
		if(ItemUtils.isItemEqual(stack, getStackInSlot(1), true, true))
		{
			return true;
		}
		else if(getStackInSlot(1) == null)
		{
				return true;
		}
		else 
		{
			return false;
		}
	}

	public ItemStack getDropWithNBT() 
	{
		NBTTagCompound tileEntity = new NBTTagCompound();
		ItemStack dropStack = new ItemStack(ModBlocks.QuantumDsu, 1);
		writeToNBTWithoutCoords(tileEntity);
		dropStack.setTagCompound(new NBTTagCompound());
		dropStack.getTagCompound().setTag("tileEntity", tileEntity);
		return dropStack;
	}

	public void syncWithAll() 
	{
		if (!worldObj.isRemote) 
		{
			reborncore.common.network.NetworkManager.sendToAllAround(new CustomDescriptionPacket(this), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), getPos().getX(), getPos().getY(), getPos().getZ(), 20));
		}
	}
	
    @SideOnly(Side.CLIENT)
	 public void addWailaInfo(List<String> info) 
    {
		int size = 0;
		String name = "";
		if (getStackInSlot(1) != null && storedItem == null) 
		{
			name = getStackInSlot(1).getDisplayName();
			size += getStackInSlot(1).stackSize;
		}
		if(getStackInSlot(1) != null && storedItem != null)
		{
			name = getStackInSlot(1).getDisplayName();
			size += getStackInSlot(1).stackSize + storedItem.stackSize;
		}
		if (storedItem != null)
		info.add(size + " " + name);	
	 }

	@Override
	public ItemStack getStoredItemType() 
	{
		return this.storedItem;
	}

	@Override
	public void setStoredItemCount(int amount) 
	{
		this.storedItem.stackSize = 0;
		this.storedItem.stackSize += (amount);
		this.markDirty();
	}

	@Override
	public void setStoredItemType(ItemStack type, int amount) 
	{
		this.storedItem = type;
		this.storedItem.stackSize = amount;
		this.markDirty();
	}

	@Override
	public int getMaxStoredCount() 
	{
		return this.storage;
	}

	@Override
	public String getName() 
	{
		return inventory.getName();
	}

	@Override
	public boolean hasCustomName()
	{
		return inventory.hasCustomName();
	}

	@Override
	public ITextComponent getDisplayName() 
	{
		return inventory.getDisplayName();
	}

	@Override
	public ItemStack removeStackFromSlot(int index) 
	{
		return inventory.removeStackFromSlot(index);
	}

	@Override
	public void openInventory(EntityPlayer player) 
	{
		inventory.openInventory(player);
	}

	@Override
	public void closeInventory(EntityPlayer player) 
	{
		inventory.closeInventory(player);
	}

	@Override
	public int getField(int id) 
	{
		return inventory.getField(id);
	}

	@Override
	public void setField(int id, int value) 
	{
		inventory.setField(id, value);
	}

	@Override
	public int getFieldCount() 
	{
		return inventory.getFieldCount();
	}

	@Override
	public void clear() 
	{
		inventory.clear();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return new int[]{0, 1};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		if(index == 0)
			return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if(index == 1 && stack != null)
			return true;
		return false;
	}
}

package QuantumStorage.block.tile;

import java.util.List;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.packet.PacketHandler;
import QuantumStorage.util.Inventory;
import QuantumStorage.util.ItemUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;

public class TileQuantumDsu extends TileEntity implements IInventory, IDeepStorageUnit {
	int storage = (int) Double.MAX_VALUE;

	public Inventory inventory = new Inventory(3, "TileSimpleDsu", storage);

	public ItemStack storedItem;
	public String storedItemAsString;

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			if (storedItem != null) {
				ItemStack fakeStack = storedItem.copy();
				fakeStack.stackSize = 1;
				setInventorySlotContents(2, fakeStack);
			} else {
				setInventorySlotContents(2, null);
			}

			if (getStackInSlot(0) != null) {
				if (storedItem == null) {
					storedItem = getStackInSlot(0);
					setInventorySlotContents(0, null);
				} else if (ItemUtils.isItemEqual(storedItem, getStackInSlot(0), true, true)) {
					if (storedItem.stackSize <= Integer.MAX_VALUE - getStackInSlot(0).stackSize) {
						storedItem.stackSize += getStackInSlot(0).stackSize;
						decrStackSize(0, getStackInSlot(0).stackSize);
					}
				}
			}

			if (storedItem != null && getStackInSlot(1) == null) {
				ItemStack itemStack = storedItem.copy();
				itemStack.stackSize = itemStack.getMaxStackSize();
				setInventorySlotContents(1, itemStack);
				storedItem.stackSize -= itemStack.getMaxStackSize();
			} else if (ItemUtils.isItemEqual(getStackInSlot(1), storedItem, true, true)) {
				int wanted = getStackInSlot(1).getMaxStackSize() - getStackInSlot(1).stackSize;
				if (storedItem.stackSize >= wanted) {
					decrStackSize(1, -wanted);
					storedItem.stackSize -= wanted;
				} else {
					decrStackSize(1, -storedItem.stackSize);
					storedItem = null;
				}
			}
		}
		syncWithAll();
	}

	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		readFromNBTWithoutCoords(tagCompound);
	}

	public void readFromNBTWithoutCoords(NBTTagCompound tagCompound) {
		inventory.readFromNBT(tagCompound);

		storedItem = null;
		storedItemAsString = null;

		if (tagCompound.hasKey("storedStack")) {
			storedItem = ItemStack.loadItemStackFromNBT((NBTTagCompound) tagCompound.getTag("storedStack"));
		}

		if (storedItem != null) {
			storedItem.stackSize = tagCompound.getInteger("storedQuantity");
			storedItemAsString = tagCompound.getString("storedItemAsString");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		writeToNBTWithoutCoords(tagCompound);
	}

	public void writeToNBTWithoutCoords(NBTTagCompound tagCompound) {
		inventory.writeToNBT(tagCompound);
		if (storedItem != null) {
			tagCompound.setTag("storedStack", storedItem.writeToNBT(new NBTTagCompound()));
			tagCompound.setInteger("storedQuantity", storedItem.stackSize);
			tagCompound.setString("storedItemAsString", storedItem.getDisplayName());
		} else
			tagCompound.setInteger("storedQuantity", 0);
	}

	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slotId, int count) {
		return inventory.decrStackSize(slotId, count);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return inventory.getStackInSlotOnClosing(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory.setInventorySlotContents(slot, stack);
	}

	@Override
	public String getInventoryName() {
		return inventory.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return inventory.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public void openInventory() {
		inventory.openInventory();
	}

	@Override
	public void closeInventory() {
		inventory.closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return inventory.isItemValidForSlot(slot, stack);
	}

	public ItemStack getDropWithNBT() {
		NBTTagCompound tileEntity = new NBTTagCompound();
		ItemStack dropStack = new ItemStack(ModBlocks.QuantumDsu, 1);
		writeToNBTWithoutCoords(tileEntity);
		dropStack.setTagCompound(new NBTTagCompound());
		dropStack.stackTagCompound.setTag("tileEntity", tileEntity);
		return dropStack;
	}

	public void syncWithAll() {
		if (!worldObj.isRemote) {
			PacketHandler.sendPacketToAllPlayers(getDescriptionPacket(), worldObj);
		}
	}
	
    @SideOnly(Side.CLIENT)
	 public void addWailaInfo(List<String> info) {
		int size = 0;
		String name = "";
		if (storedItem != null) {
			name = storedItem.getDisplayName();
			size += storedItem.stackSize;
		}
		if (getStackInSlot(1) != null) {
			name = getStackInSlot(1).getDisplayName();
			size += getStackInSlot(1).stackSize;
		}
		if (storedItem != null)
		info.add(size + " " + name);
	
	 }

	@Override
	public ItemStack getStoredItemType() {
		return this.storedItem;
	}

	@Override
	public void setStoredItemCount(int amount) {
		this.storedItem.stackSize = 0;
		this.storedItem.stackSize += (amount);
		this.markDirty();
	}

	@Override
	public void setStoredItemType(ItemStack type, int amount) {
		this.storedItem = type;
		this.storedItem.stackSize = amount;
		this.markDirty();
	}

	@Override
	public int getMaxStoredCount() {
		return this.storage;
	}
}

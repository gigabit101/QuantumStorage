package QuantumStorage.client.inventory;

import QuantumStorage.init.ModItems;
import QuantumStorage.items.upgrades.ItemPickupUpgrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryPickupCard implements IInventory
{
	private static final ItemStack[] FALLBACK_INVENTORY = new ItemStack[1];

	EntityPlayer player;
	int slot;
	ItemStack[] stacks = null;
	
	boolean invPushed = false;
	ItemStack storedInv = null;
	
	public InventoryPickupCard(EntityPlayer player, int slot) 
	{
		this.player = player;
		this.slot = slot;	
	}
	
	public static boolean isBag(ItemStack stack) 
	{
		return stack != null && stack.getItem() == ModItems.pickupCard;
	}
	
	ItemStack getStack()
	{
		ItemStack stack = player.inventory.getStackInSlot(slot);
		if(stack != null)
			storedInv = stack;
		return stack;
	}
	
	ItemStack[] getInventory() 
	{
		if(stacks != null)
			return stacks;

		ItemStack stack = getStack();
		if(isBag(getStack())) {
			stacks = ItemPickupUpgrade.loadStacks(stack);
			return stacks;
		}

		return FALLBACK_INVENTORY;
	}

	public void pushInventory() 
	{
		if(invPushed)
			return;

		ItemStack stack = getStack();
		if(stack == null)
			stack = storedInv;

		if(stack != null) {
			ItemStack[] inv = getInventory();
			ItemPickupUpgrade.setStacks(stack, inv);
		}

		invPushed = true;
	}

	@Override
	public int getSizeInventory() 
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return getInventory()[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) 
	{
		ItemStack[] inventorySlots = getInventory();
		if (inventorySlots[i] != null) {
			ItemStack stackAt;

			if (inventorySlots[i].stackSize <= j) 
			{
				stackAt = inventorySlots[i];
				inventorySlots[i] = null;
				return stackAt;
			} else {
				stackAt = inventorySlots[i].splitStack(j);

				if (inventorySlots[i].stackSize == 0)
					inventorySlots[i] = null;

				return stackAt;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) 
	{
		return getStackInSlot(i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		ItemStack[] inventorySlots = getInventory();
		inventorySlots[i] = itemstack;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) 
	{
		return isBag(getStack());
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return true;
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	@Override
	public void openInventory()
	{
		// NO-OP
	}

	@Override
	public void closeInventory()
	{
		// NO-OP
	}

	@Override
	public String getInventoryName()
	{
		return "quantumbag";
	}

	@Override
	public void markDirty() 
	{
		// NO-OP
	}
}

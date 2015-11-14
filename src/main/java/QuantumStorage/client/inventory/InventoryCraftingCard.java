package QuantumStorage.client.inventory;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class InventoryCraftingCard extends InventoryCrafting 
{	
	public InventoryCraftingCard(Container cont, int p1, int p2) 
	{	
		super(cont, p1, p2);
	}
	
//	@Override
//	public ItemStack getStackInSlotOnClosing(int slot)
//	{
//		return getStackInSlot(slot);
//	}
//	
//	@Override
//	public void closeInventory() 
//	{
//		super.closeInventory();
//	}
}

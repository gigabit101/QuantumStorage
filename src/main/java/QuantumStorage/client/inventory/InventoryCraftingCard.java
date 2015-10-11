package QuantumStorage.client.inventory;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingCard extends InventoryCrafting 
{
	public InventoryCraftingCard(Container p_i1807_1_, int p_i1807_2_, int p_i1807_3_) 
	{
		super(p_i1807_1_, p_i1807_2_, p_i1807_3_);
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return getStackInSlot(slot);
	}
}

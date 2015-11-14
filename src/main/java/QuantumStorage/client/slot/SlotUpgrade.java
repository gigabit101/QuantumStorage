package QuantumStorage.client.slot;

import QuantumStorage.items.upgrades.ItemUpgrade;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUpgrade extends Slot
{
	public SlotUpgrade(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) 
	{
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		if(stack.getItem() instanceof ItemUpgrade)
			return true;
		return false;
	}
}

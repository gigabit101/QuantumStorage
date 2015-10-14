package QuantumStorage.client.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLocked extends Slot 
{
	public SlotLocked(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) 
	{
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}

	@Override
	public boolean canTakeStack(EntityPlayer p_82869_1_) 
	{
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack p_75214_1_) 
	{
		return false;
	}
}

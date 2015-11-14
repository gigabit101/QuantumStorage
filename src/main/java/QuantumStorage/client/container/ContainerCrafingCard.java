package QuantumStorage.client.container;

import QuantumStorage.client.inventory.InventoryCraftingCard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;

public class ContainerCrafingCard extends ContainerWorkbench 
{
	public ContainerCrafingCard(EntityPlayer player) 
	{
		super(player.inventory, player.worldObj, 0, 0, 0);
		int slot = player.inventory.currentItem;
		IInventory playerInv = player.inventory;		
		craftMatrix = new InventoryCraftingCard(this, 3, 3);

		inventorySlots.clear();
		inventoryItemStacks.clear();

		// Le copypasta
		addSlotToContainer(new SlotCrafting(player.inventory.player, craftMatrix, craftResult, 0, 124, 35));
		int l;
		int i1;

		for (l = 0; l < 3; ++l)
		{
			for (i1 = 0; i1 < 3; ++i1)
			{
				addSlotToContainer(new Slot(craftMatrix, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
			}
		}

		for (l = 0; l < 3; ++l)
		{
			for (i1 = 0; i1 < 9; ++i1)
			{
				addSlotToContainer(new Slot(playerInv, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for (l = 0; l < 9; ++l)
		{
			addSlotToContainer(new Slot(playerInv, l, 8 + l * 18, 142));
		}

		onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return true;
	}
}

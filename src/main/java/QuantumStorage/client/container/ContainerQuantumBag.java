package QuantumStorage.client.container;

import QuantumStorage.client.QuantumStorageContainer;
import QuantumStorage.client.inventory.InventoryQuantumBag;
import QuantumStorage.client.slot.SlotLocked;
import QuantumStorage.client.slot.SlotUpgrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerQuantumBag extends QuantumStorageContainer
{	
	InventoryQuantumBag bagInv;
	public ContainerQuantumBag(EntityPlayer player) 
	{
		int slot = player.inventory.currentItem;
		IInventory playerInv = player.inventory;
		bagInv = new InventoryQuantumBag(player, slot);
		
        int i;
        int j;
        //Bag inv
		for(i = 0; i < 6; ++i)
		{
			for(j = 0; j < 9; ++j) 
			{
				addSlotToContainer(new Slot(bagInv, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}
		//upgrade slots
		for(i = 0; i < 6; ++i) 
		{
			addSlotToContainer(new SlotUpgrade(bagInv, i + 54, 173, 18 + i * 18));
		}
		
		//player inv
		for (i = 0; i < 3; ++i)
		{
			for (j = 0; j < 9; ++j)
			{
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}
		//player hotbar
		for(i = 0; i < 9; ++i) 
		{
			if(player.inventory.currentItem == i)
				addSlotToContainer(new SlotLocked(playerInv, i, 8 + i * 18, 198));
			else addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 198));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		boolean can = bagInv.isUseableByPlayer(player);
		if(!can)
			onContainerClosed(player);

		return can;
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		bagInv.pushInventory();
	}
}

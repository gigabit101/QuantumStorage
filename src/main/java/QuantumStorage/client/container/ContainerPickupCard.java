package QuantumStorage.client.container;

import QuantumStorage.client.QuantumStorageContainer;
import QuantumStorage.client.inventory.InventoryPickupCard;
import QuantumStorage.client.slot.SlotLocked;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerPickupCard extends QuantumStorageContainer
{	
	InventoryPickupCard bagInv;
	public ContainerPickupCard(EntityPlayer player) 
	{
		int slot = player.inventory.currentItem;
		IInventory playerInv = player.inventory;
		bagInv = new InventoryPickupCard(player, slot);
		
        int i;
        int j;
        
        addSlotToContainer(new Slot(bagInv, 0, 84, 44));
		
		//player inv
		for (i = 0; i < 3; ++i)
		{
			for (j = 0; j < 9; ++j)
			{
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 12 + j * 18, 102 + i * 18));
			}
		}
		//player hotbar
		for(i = 0; i < 9; ++i) 
		{
			if(player.inventory.currentItem == i)
				addSlotToContainer(new SlotLocked(playerInv, i, 12 + i * 18, 160));
			else addSlotToContainer(new Slot(playerInv, i, 12 + i * 18, 160));
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

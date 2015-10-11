package QuantumStorage.client.container;

import QuantumStorage.client.QuantumStorageContainer;
import QuantumStorage.client.inventory.InventoryCraftingCard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.world.World;

public class ContainerQuantumBag extends QuantumStorageContainer
{
	public ContainerQuantumBag(InventoryPlayer p_i1808_1_, World p_i1808_2_) 
	{
		super();
		inventorySlots.clear();
		inventoryItemStacks.clear();

		// Le copypasta
		int l;
		int i1;
        int j;
        int k;

		for (l = 0; l < 3; ++l)
		{
			for (i1 = 0; i1 < 9; ++i1)
			{
				addSlotToContainer(new Slot(p_i1808_1_, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for (l = 0; l < 9; ++l)
		{
			addSlotToContainer(new Slot(p_i1808_1_, l, 8 + l * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
}

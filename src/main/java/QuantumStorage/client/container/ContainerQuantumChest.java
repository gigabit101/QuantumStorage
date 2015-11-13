package QuantumStorage.client.container;

import QuantumStorage.block.tile.TileQuantumChest;
import QuantumStorage.client.QuantumStorageContainer;
import QuantumStorage.client.slot.SlotFake;
import QuantumStorage.client.slot.SlotLocked;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerQuantumChest extends QuantumStorageContainer 
{
	public TileQuantumChest tile;
	public EntityPlayer player;
	public int stackamount;

	public ContainerQuantumChest(TileQuantumChest tilesimple, EntityPlayer player)
	{
		super();
		this.tile = tilesimple;
		this.player = player;

		int i;	
		int j;
        //Bag inv
		for(i = 0; i < 6; ++i)
		{
			for(j = 0; j < 9; ++j) 
			{
				addSlotToContainer(new Slot(tilesimple.inventory, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}
		//player inv
		for (i = 0; i < 3; ++i)
		{
			for (j = 0; j < 9; ++j)
			{
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}
		//player hotbar
		for(i = 0; i < 9; ++i) 
		{
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 198));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return true;
	}
}

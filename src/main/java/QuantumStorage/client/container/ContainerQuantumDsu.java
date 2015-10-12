package QuantumStorage.client.container;

import QuantumStorage.block.tile.TileQuantumDsu;
import QuantumStorage.client.QuantumStorageContainer;
import QuantumStorage.client.slot.SlotFake;
import QuantumStorage.client.slot.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerQuantumDsu extends QuantumStorageContainer 
{
	public TileQuantumDsu tile;
	public EntityPlayer player;
	public int stackamount;

	public ContainerQuantumDsu(TileQuantumDsu tilesimple, EntityPlayer player)
	{
		super();
		this.tile = tilesimple;
		this.player = player;

		this.addSlotToContainer(new Slot(tilesimple.inventory, 0, 80, 17));
		this.addSlotToContainer(new SlotOutput(tilesimple.inventory, 1, 80, 53));
		this.addSlotToContainer(new SlotFake(tilesimple.inventory, 2, 59, 42, false, false, Integer.MAX_VALUE));

		int i;

		for (i = 0; i < 3; ++i) 
		{
			for (int j = 0; j < 9; ++j) 
			{
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) 
		{
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return true;
	}
}

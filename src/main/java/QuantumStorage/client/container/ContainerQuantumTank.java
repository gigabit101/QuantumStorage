package QuantumStorage.client.container;

import QuantumStorage.client.QuantumStorageContainer;
import QuantumStorage.client.slot.SlotFake;
import QuantumStorage.client.slot.SlotOutput;
import QuantumStorage.tile.TileQuantumTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import reborncore.client.gui.SlotFluid;

public class ContainerQuantumTank extends QuantumStorageContainer
{
	public TileQuantumTank tile;
	public EntityPlayer player;
	
	public ContainerQuantumTank(TileQuantumTank tile, EntityPlayer player) 
	{
		super();
		this.tile = tile;
		this.player = player;
		
        this.addSlotToContainer(new SlotFluid(tile.inventory, 0, 80, 17));
        this.addSlotToContainer(new SlotOutput(tile.inventory, 1, 80, 53));
        this.addSlotToContainer(new SlotFake(tile.inventory, 2, 59, 42, false, false, 1));

        int i;

        for (i = 0; i < 3; ++i) 
        {
            for (int j = 0; j < 9; ++j) 
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9
                        + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) 
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18,
                    142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return true;
	}
}

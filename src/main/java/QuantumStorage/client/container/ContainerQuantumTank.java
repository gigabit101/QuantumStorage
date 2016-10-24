package QuantumStorage.client.container;

import QuantumStorage.client.slot.SlotFake;
import QuantumStorage.client.slot.SlotOutput;
import QuantumStorage.tile.TileQuantumTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.client.gui.slots.SlotFluid;
import reborncore.common.container.RebornContainer;

public class ContainerQuantumTank extends RebornContainer
{
	public TileQuantumTank tile;
	public EntityPlayer player;
    public int stackamount = -1;
    public int stackSizeType = -1;
	
	public ContainerQuantumTank(TileQuantumTank tile, EntityPlayer player) 
	{
		super();
		this.tile = tile;
		this.player = player;
		
        this.addSlotToContainer(new SlotFluid(tile.inventory, 0, 116, 17));
        this.addSlotToContainer(new SlotOutput(tile.inventory, 1, 116, 53));
        this.addSlotToContainer(new SlotFake(tile.inventory, 2, 95, 51, false, false, 1));

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

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); i++) {
            IContainerListener IContainerListener = this.listeners.get(i);
            if(tile.tank != null && tile.tank.getFluidAmount() != 0){
                if (this.stackamount != getHStackAmount(tile.tank.getFluidAmount())) {
                    IContainerListener.sendProgressBarUpdate(this, 0, getHStackAmount(tile.tank.getFluidAmount()));
                }
                if (this.stackSizeType != getHStackAmountType(tile.tank.getFluidAmount())) {
                    IContainerListener.sendProgressBarUpdate(this, 1, getHStackAmountType(tile.tank.getFluidAmount()));
                }
            } else if(tile.tank.getFluidAmount() == 0)  {
                if (this.stackamount != 0) {
                    IContainerListener.sendProgressBarUpdate(this, 0, 0);
                }
                if (this.stackSizeType != 0) {
                    IContainerListener.sendProgressBarUpdate(this, 1, 0);
                }
            }
        }
    }

    @Override
    public void addListener(IContainerListener crafting) {
        super.addListener(crafting);
        if(tile.tank != null){
            crafting.sendProgressBarUpdate(this, 0, getHStackAmount(tile.tank.getFluidAmount()));
            crafting.sendProgressBarUpdate(this, 1, getHStackAmountType(tile.tank.getFluidAmount()));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            this.stackamount = value;
        } else if (id == 1) {
            this.stackSizeType = value;
        }
    }

    public int getHStackAmount(int amount) {
        if (amount <= 1000) {
            return amount;
        } else if (amount >= 1000 && amount < 1000000) {
            return amount / 100;
        } else if (amount >= 1000000 && amount < 1000000000) {
            return amount / 100000;
        } else if (amount >= 1000000000) {
            return amount / 100000000;
        }
        return amount;
    }

    public int getHStackAmountType(int amount) {
        if (amount <= 1000) {
            return 0;
        } else if (amount >= 1000 && amount < 1000000) {
            return 1;
        } else if (amount >= 1000000 && amount < 1000000000) {
            return 2;
        } else if (amount >= 1000000000) {
            return 3;
        }
        return 0;
    }
}

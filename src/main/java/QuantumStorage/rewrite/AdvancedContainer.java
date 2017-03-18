package QuantumStorage.rewrite;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import reborncore.common.container.RebornContainer;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class AdvancedContainer extends RebornContainer
{
    public AdvancedTileEntity machine;

    public AdvancedContainer(EntityPlayer player, AdvancedTileEntity machine)
    {
        super();
        this.machine = machine;
        if(machine.getSlots() != null)
        {
            for(Slot s : machine.getSlots())
            {
                addSlotToContainer(s);
            }
        }
        drawPlayersInv(player, 8, 94);
        drawPlayersHotBar(player, 8, 152);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}

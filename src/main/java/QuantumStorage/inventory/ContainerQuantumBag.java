package QuantumStorage.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.container.RebornContainer;

public class ContainerQuantumBag extends RebornContainer
{
    public ContainerQuantumBag(EntityPlayer player, IItemHandlerModifiable invBag)
    {
        int i = 0;
        for (int l = 0; l < 7; ++l)
        {
            for (int j1 = 0; j1 < 13; ++j1)
            {
                this.addSlotToContainer(new SlotItemHandler(invBag, i, 8 + j1 * 18, 11 + l * 18));
                i++;
            }
        }
        drawPlayersInv(player, 45, 151);
        drawPlayersHotBar(player, 45, 151 + 58);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}

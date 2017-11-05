package QuantumStorage.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.container.RebornContainer;

/**
 * Created by Gigabit101 on 12/05/2017.
 */
public class ContainerMultiBlockCrate extends RebornContainer
{
    MultiBlockCrate multiBlockCrate;

    public ContainerMultiBlockCrate(EntityPlayer player, MultiBlockCrate multiBlockCrate)
    {
        this.multiBlockCrate = multiBlockCrate;
        if (multiBlockCrate != null)
        {
            ItemStackHandler handler = multiBlockCrate.getInv();

            drawSlotsForPage(handler);

            drawPlayersInv(player, 45, 141);
            drawPlayersHotBar(player, 45, 199);
        }
    }

    public void drawSlotsForPage(ItemStackHandler handler)
    {
        int i = 0;
        for (int l = 0; l < 6; ++l)
        {
            for (int j1 = 0; j1 < 13; ++j1)
            {
                if (i != handler.getSlots())
                {
                    this.addSlotToContainer(new SlotItemHandler(handler, i, 9 + j1 * 18, 21 + l * 18));
                    i++;
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}

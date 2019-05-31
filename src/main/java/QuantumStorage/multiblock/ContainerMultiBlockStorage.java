package QuantumStorage.multiblock;

import QuantumStorage.inventory.CachingItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.container.RebornContainer;
import reborncore.common.util.Inventory;

public class ContainerMultiBlockStorage extends RebornContainer
{
    int page = 0;
    MultiBlockStorage storage;
    
    public ContainerMultiBlockStorage(EntityPlayer player, MultiBlockStorage storage, int page)
    {
        this.page = page;
        this.storage = storage;
        if (storage != null)
        {
            CachingItemHandler handler = storage.getInvForPage(page);
            if (handler != null)
            {
                drawSlotsForPage(page, handler);
            }
            
            drawPlayersInv(player, 45, 141);
            drawPlayersHotBar(player, 45, 199);
        }
    }
    
    public void drawSlotsForPage(int page, CachingItemHandler handler)
    {
        int i = 0;
        for (int l = 0; l < 6; ++l)
        {
            for (int j1 = 0; j1 < 13; ++j1)
            {
                this.addSlotToContainer(new SlotItemHandler(handler, i, 9 + j1 * 18, 21 + l * 18));
                i++;
            }
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}

package QuantumStorage.multiblock;

import QuantumStorage.inventory.CachingItemHandler;
import QuantumStorage.tiles.chests.TileChestDiamond;
import QuantumStorage.tiles.chests.TileChestGold;
import QuantumStorage.tiles.chests.TileChestIron;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.common.container.RebornContainer;

@ChestContainer(isLargeChest = true)
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
    
    @ChestContainer.RowSizeCallback
    public int getNumColumns()
    {
        return 78 * storage.pages;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}

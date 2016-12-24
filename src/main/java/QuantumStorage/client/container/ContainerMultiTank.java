package QuantumStorage.client.container;

import QuantumStorage.tile.TileMultiTank;
import net.minecraft.entity.player.EntityPlayer;
import reborncore.common.container.RebornContainer;

/**
 * Created by Gigabit101 on 16/11/2016.
 */
public class ContainerMultiTank extends RebornContainer
{
    TileMultiTank tank;
    public ContainerMultiTank(TileMultiTank tank, EntityPlayer player)
    {
        this.tank = tank;
        addPlayersInventory(player);
        addPlayersHotbar(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}

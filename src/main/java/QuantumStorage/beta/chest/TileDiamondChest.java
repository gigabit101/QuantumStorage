package QuantumStorage.beta.chest;

import reborncore.common.util.Inventory;

/**
 * Created by Gigabit101 on 05/11/2016.
 */
public class TileDiamondChest extends TileQChest
{
    Inventory inv = new Inventory(78, "", 64, this);

    public TileDiamondChest()
    {
        setInv(inv);
    }
}

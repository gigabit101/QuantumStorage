package QuantumStorage.beta.chest;

import reborncore.common.util.Inventory;

/**
 * Created by Gigabit101 on 05/11/2016.
 */
public class TileGoldChest extends TileQChest
{
    Inventory inv = new Inventory(65, "", 64, this);

    public TileGoldChest()
    {
        setInv(inv);
    }
}

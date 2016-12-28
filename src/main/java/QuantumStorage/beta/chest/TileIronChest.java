package QuantumStorage.beta.chest;

import net.minecraft.nbt.NBTTagCompound;
import reborncore.common.util.Inventory;

/**
 * Created by Gigabit101 on 05/11/2016.
 */
public class TileIronChest extends TileQChest
{
    Inventory inv = new Inventory(52, "TileIronChest", 64, this);

    public TileIronChest()
    {
        setInv(inv);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
//        super.readFromNBT(compound);
        inv.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        inv.writeToNBT(compound);
        return compound;
    }
}

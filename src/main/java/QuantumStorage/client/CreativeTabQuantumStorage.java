package QuantumStorage.client;

import QuantumStorage.QuantumStorage;
import QuantumStorage.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
public class CreativeTabQuantumStorage extends CreativeTabs
{
    public static CreativeTabQuantumStorage INSTANCE = new CreativeTabQuantumStorage();

    public CreativeTabQuantumStorage()
    {
        super(QuantumStorage.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(Item.getItemFromBlock(ModBlocks.DSU));
    }
}

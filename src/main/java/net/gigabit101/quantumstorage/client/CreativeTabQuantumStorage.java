package net.gigabit101.quantumstorage.client;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
public class CreativeTabQuantumStorage extends ItemGroup
{
    public static CreativeTabQuantumStorage INSTANCE = new CreativeTabQuantumStorage();

    public CreativeTabQuantumStorage()
    {
        super(QuantumStorage.MOD_ID);
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(Item.getItemFromBlock(QuantumStorage.blockQsu));
    }
}

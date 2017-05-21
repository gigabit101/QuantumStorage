package QuantumStorage.init;

import QuantumStorage.items.ItemCrate;
import QuantumStorage.items.ItemUpgrade;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Gigabit101 on 28/01/2017.
 */
public class ModItems
{
    public static Item UPGRADE;
    public static Item CRATE;

    public static void init()
    {
        UPGRADE = new ItemUpgrade();
        GameRegistry.register(UPGRADE);

        CRATE = new ItemCrate();
        GameRegistry.register(CRATE);
    }
}

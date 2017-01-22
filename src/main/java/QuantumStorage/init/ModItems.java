package QuantumStorage.init;

import QuantumStorage.items.upgrades.ItemRenderUpgrade;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Gigabit101 on 28/12/2016.
 */
public class ModItems
{
    public static Item renderUpgrade;

    public static void init()
    {
        renderUpgrade = new ItemRenderUpgrade();
        GameRegistry.register(renderUpgrade);
    }
}

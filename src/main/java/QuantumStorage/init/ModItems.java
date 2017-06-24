package QuantumStorage.init;

import QuantumStorage.items.ItemCrate;
import QuantumStorage.items.ItemUpgrade;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Gigabit101 on 28/01/2017.
 */
@Mod.EventBusSubscriber
public class ModItems
{
    public static Item UPGRADE = new ItemUpgrade();
    public static Item CRATE = new ItemCrate();

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(CRATE);
        event.getRegistry().register(UPGRADE);
    }
}

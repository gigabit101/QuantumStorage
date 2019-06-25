//package QuantumStorage.init;
//
//import QuantumStorage.items.ItemCrate;
//import QuantumStorage.items.ItemQuantumBag;
//import QuantumStorage.items.ItemQuantumBattery;
//import QuantumStorage.upgrades.ItemUpgrade;
//import net.minecraft.item.Item;
//import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
///**
// * Created by Gigabit101 on 28/01/2017.
// */
//@Mod.EventBusSubscriber
//public class ModItems
//{
//    public static Item UPGRADE = new ItemUpgrade();
//    public static Item CRATE = new ItemCrate();
//
//    public static Item BATTERY = new ItemQuantumBattery();
//    public static Item BAG = new ItemQuantumBag();
//    public static Item REMOTE = new ItemRemote();
//
//    @SubscribeEvent
//    public static void init(RegistryEvent.Register<Item> event)
//    {
//        event.getRegistry().register(CRATE);
//        event.getRegistry().register(UPGRADE);
//        event.getRegistry().register(BATTERY);
//        event.getRegistry().register(BAG);
//        event.getRegistry().register(REMOTE);
//    }
//}

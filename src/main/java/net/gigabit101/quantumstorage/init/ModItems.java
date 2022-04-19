package net.gigabit101.quantumstorage.init;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.items.ItemHammer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final Item.Properties ITEM_GROUP = new Item.Properties().tab(CreativeTabQuantumStorage.INSTANCE);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, QuantumStorage.MOD_ID);
    
    public static final RegistryObject<Item> CHEST_DIAMOND_ITEM = ITEMS.register("chestdiamond", () -> new BlockItem(ModBlocks.CHEST_DIAMOND.get(), ITEM_GROUP));
    public static final RegistryObject<Item> CHEST_GOLD_ITEM = ITEMS.register("chestgold", () -> new BlockItem(ModBlocks.CHEST_GOLD.get(), ITEM_GROUP));
    public static final RegistryObject<Item> CHEST_IRON_ITEM = ITEMS.register("chestiron", () -> new BlockItem(ModBlocks.CHEST_IRON.get(), ITEM_GROUP));
    
    public static final RegistryObject<Item> TRASH_CAN_ITEM = ITEMS.register("trashcan", () -> new BlockItem(ModBlocks.TRASH_CAN.get(), ITEM_GROUP));
    public static final RegistryObject<Item> QSU_ITEM = ITEMS.register("qsu", () -> new BlockItem(ModBlocks.QSU.get(), ITEM_GROUP));
    public static final RegistryObject<Item> TANK_ITEM = ITEMS.register("tank", () -> new BlockItem(ModBlocks.TANK.get(), ITEM_GROUP));

    public static final RegistryObject<Item> CONTROLLER_ITEM = ITEMS.register("controller", () -> new BlockItem(ModBlocks.CONTROLLER.get(), ITEM_GROUP));
    public static final RegistryObject<Item> HAMMER_ITEM = ITEMS.register("hammer", ItemHammer::new);

//    public static final RegistryObject<Item> KEY_ITEM = ITEMS.register("key", ItemKey::new);

//    public static final RegistryObject<Item> NOMMER_ITEM = ITEMS.register("nommer", ItemNommer::new);

//    public static final RegistryObject<Item> BATTERY_ITEM = ITEMS.register("battery", ItemQuantumBattery::new);
}

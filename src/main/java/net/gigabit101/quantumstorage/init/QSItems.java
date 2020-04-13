package net.gigabit101.quantumstorage.init;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.items.ItemQuantumBattery;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class QSItems
{
    public static final Item.Properties ITEM_GROUP = new Item.Properties().group(CreativeTabQuantumStorage.INSTANCE);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, QuantumStorage.MOD_ID);
    
    public static final RegistryObject<Item> CHEST_DIAMOND_ITEM = ITEMS.register("chestdiamond", () -> new BlockItem(QSBlocks.CHEST_DIAMOND.get(), ITEM_GROUP));
    public static final RegistryObject<Item> CHEST_GOLD_ITEM = ITEMS.register("chestgold", () -> new BlockItem(QSBlocks.CHEST_GOLD.get(), ITEM_GROUP));
    public static final RegistryObject<Item> CHEST_IRON_ITEM = ITEMS.register("chestiron", () -> new BlockItem(QSBlocks.CHEST_IRON.get(), ITEM_GROUP));
    
    public static final RegistryObject<Item> TRASH_CAN_ITEM = ITEMS.register("trashcan", () -> new BlockItem(QSBlocks.TRASH_CAN.get(), ITEM_GROUP));
    public static final RegistryObject<Item> QSU_ITEM = ITEMS.register("qsu", () -> new BlockItem(QSBlocks.QSU.get(), ITEM_GROUP));
    public static final RegistryObject<Item> TANK_ITEM = ITEMS.register("tank", () -> new BlockItem(QSBlocks.TANK.get(), ITEM_GROUP));
    
    public static final RegistryObject<Item> BATTERY_ITEM = ITEMS.register("battery", ItemQuantumBattery::new);
    
}

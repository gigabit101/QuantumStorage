package net.gigabit101.quantumstorage.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.gigabit101.quantumstorage.QuantumStorage;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(QuantumStorage.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> QUANTUM_STORAGE_ITEM = ITEMS.register("qsu",
            () -> new ItemNameBlockItem(ModBlocks.QUANTUM_STORAGE_UNIT.get(), new Item.Properties().tab(QuantumStorage.CREATIVE_TAB)));

    public static final RegistrySupplier<Item> TRASH_CAN = ITEMS.register("trashcan",
            () -> new ItemNameBlockItem(ModBlocks.TRASH_CAN.get(), new Item.Properties().tab(QuantumStorage.CREATIVE_TAB)));
}

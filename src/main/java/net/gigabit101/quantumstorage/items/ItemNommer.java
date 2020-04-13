package net.gigabit101.quantumstorage.items;

import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class ItemNommer extends Item {
    public ItemNommer() {
        super(new Item.Properties().rarity(Rarity.EPIC).maxStackSize(1).group(CreativeTabQuantumStorage.INSTANCE));    }
}

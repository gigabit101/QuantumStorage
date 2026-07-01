package net.gigabit101.quantumstorage.compat.jei;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public record CratingMachineJeiRecipe(Identifier id, ItemStack input, ItemStack output) {
}

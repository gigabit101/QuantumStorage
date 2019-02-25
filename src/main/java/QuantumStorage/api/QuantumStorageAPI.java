package QuantumStorage.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
public class QuantumStorageAPI
{
    public static List<RecipeQuantumCrafter> RECIPES = new ArrayList<>();

    public static RecipeQuantumCrafter addAltarRecipe(Object input, ItemStack output, int time)
    {
        RecipeQuantumCrafter recipe = new RecipeQuantumCrafter(input, output, time);
        RECIPES.add(recipe);
        return recipe;
    }

    @CapabilityInject(IQuantumBagProvider.class)
    public static final Capability<IQuantumBagProvider> QUANTUM_BAG_PROVIDER_CAPABILITY = null;
}

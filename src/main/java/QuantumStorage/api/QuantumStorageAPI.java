package QuantumStorage.api;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
public class QuantumStorageAPI
{
    public static List<RecipeQuantumCrafter> RECIPES = new ArrayList<>();

    public static RecipeQuantumCrafter addAltarRecipe(Object input, ItemStack output, int mana)
    {
        RecipeQuantumCrafter recipe = new RecipeQuantumCrafter(input, output, mana);
        RECIPES.add(recipe);
        return recipe;
    }

}

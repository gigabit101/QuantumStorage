package net.gigabit101.quantumstorage.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
public class QuantumStorageAPI
{
    public static List<RecipeQuantumTank> TANK_RECIPES = new ArrayList<>();

    public static RecipeQuantumTank addTankRecipe(Object input, ItemStack output, FluidStack fluidStack)
    {
        RecipeQuantumTank recipe = new RecipeQuantumTank(input, output, fluidStack);
        TANK_RECIPES.add(recipe);
        return recipe;
    };
}

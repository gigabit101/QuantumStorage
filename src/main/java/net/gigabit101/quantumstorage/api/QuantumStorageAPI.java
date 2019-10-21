package net.gigabit101.quantumstorage.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
public class QuantumStorageAPI
{
    public static List<RecipeQuantumCrafter> CRAFTER_RECIPES = new ArrayList<>();
    public static List<RecipeQuantumTank> TANK_RECIPES = new ArrayList<>();
    
    
    public static RecipeQuantumCrafter addCrafterRecipe(Object input, ItemStack output, int time)
    {
        RecipeQuantumCrafter recipe = new RecipeQuantumCrafter(input, output, time);
        CRAFTER_RECIPES.add(recipe);
        return recipe;
    }
    
    public static RecipeQuantumTank addTankRecipe(Object input, ItemStack output, FluidStack fluidStack)
    {
        RecipeQuantumTank recipe = new RecipeQuantumTank(input, output, fluidStack);
        TANK_RECIPES.add(recipe);
        return recipe;
    }
    
    @CapabilityInject(IQuantumBagProvider.class)
    public static final Capability<IQuantumBagProvider> QUANTUM_BAG_PROVIDER_CAPABILITY = null;
}

package QuantumStorage.compat.jei;

import net.gigabit101.quantumstorage.api.QuantumStorageAPI;
import net.gigabit101.quantumstorage.init.ModBlocks;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
@JEIPlugin
public class QuantumStorageJeiPlugin extends BlankModPlugin
{
    private IJeiRuntime runtime;
    
    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        
        registry.addRecipeCategories(new CrafterRecipeCategory(jeiHelpers.getGuiHelper()));
        
        registry.addRecipeHandlers(new CrafterRecipeHandler(jeiHelpers));
        
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.QUANTUM_CRAFTER), CrafterRecipeCategory.ID);
        
        registry.addRecipes(QuantumStorageAPI.CRAFTER_RECIPES);
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
    {
        this.runtime = jeiRuntime;
    }
    
    public IJeiRuntime getRuntime()
    {
        return runtime;
    }
}

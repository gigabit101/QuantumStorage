package QuantumStorage.compat.jei;

import QuantumStorage.api.RecipeQuantumCrafter;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
public class CrafterRecipeHandler implements IRecipeHandler<RecipeQuantumCrafter>
{
    private final IJeiHelpers jeiHelpers;

    public CrafterRecipeHandler(IJeiHelpers helpers)
    {
        this.jeiHelpers = helpers;
    }

    @Override
    public Class<RecipeQuantumCrafter> getRecipeClass()
    {
        return RecipeQuantumCrafter.class;
    }

    @Override
    public String getRecipeCategoryUid(RecipeQuantumCrafter crafter)
    {
        return CrafterRecipeCategory.ID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(RecipeQuantumCrafter crafter)
    {
        return new CrafterRecipeWrapper(jeiHelpers, crafter);
    }

    @Override
    public boolean isRecipeValid(RecipeQuantumCrafter crafter)
    {
        return true;
    }
}

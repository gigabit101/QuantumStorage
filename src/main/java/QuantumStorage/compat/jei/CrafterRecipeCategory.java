package QuantumStorage.compat.jei;

import QuantumStorage.QuantumStorage;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
public class CrafterRecipeCategory extends BlankRecipeCategory<CrafterRecipeWrapper>
{
    public static final String ID = QuantumStorage.MOD_ID + ".quantumcrafter";
    public static final ResourceLocation texture = new ResourceLocation(QuantumStorage.MOD_ID.toLowerCase() + ":" + "textures/gui/gui_sheet.png");

    private IDrawable background;

    public CrafterRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.createDrawable(texture, 43, 19, 101, 54);
    }

    @Override
    public String getUid()
    {
        return ID;
    }

    @Override
    public String getTitle()
    {
        return I18n.format("gui.quantumstorage.crafter");
    }

    @Override
    public String getModName()
    {
        return QuantumStorage.MOD_NAME;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, CrafterRecipeWrapper crafterRecipeWrapper, IIngredients iIngredients)
    {
        IGuiItemStackGroup group = iRecipeLayout.getItemStacks();

        group.init(0, true, 0, 20);
        group.init(1, false, 80, 20);

        group.set(0, iIngredients.getInputs(ItemStack.class).get(0));

        group.set(1, iIngredients.getOutputs(ItemStack.class).get(0));
    }
}

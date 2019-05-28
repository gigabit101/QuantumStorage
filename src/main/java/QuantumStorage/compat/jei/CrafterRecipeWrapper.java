package QuantumStorage.compat.jei;

import QuantumStorage.api.RecipeQuantumCrafter;
import QuantumStorage.client.GuiBuilderQuantumStorage;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

/**
 * Created by Gigabit101 on 10/05/2017.
 */
public class CrafterRecipeWrapper extends BlankRecipeWrapper
{
    private final List input;
    private final ItemStack output;
    private final int time;
    private IDrawableAnimated progress;

    @SuppressWarnings("unchecked")
    public CrafterRecipeWrapper(IJeiHelpers guiHelper, RecipeQuantumCrafter recipe)
    {
        ImmutableList.Builder builder = ImmutableList.builder();
        IDrawableStatic progressStatic = guiHelper.getGuiHelper().createDrawable(GuiBuilderQuantumStorage.GUI_SHEET, 100, 151, 16, 10);

        int ticksPerCycle = 1000 / 4;
        this.progress = guiHelper.getGuiHelper().createAnimatedDrawable(progressStatic, ticksPerCycle, IDrawableAnimated.StartDirection.LEFT, false);

        if (recipe.getInput() instanceof ItemStack)
        {
            builder.add(recipe.getInput());
        }
        if (recipe.getInput() instanceof String)
        {
            builder.add(OreDictionary.getOres(((String) recipe.getInput())));
        }
        input = builder.build();
        output = recipe.getOutput();
        time = recipe.getTime();
    }


    @Override
    public void getIngredients(IIngredients iIngredients)
    {
        iIngredients.setInputs(ItemStack.class, input);
        iIngredients.setOutput(ItemStack.class, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int i, int i1, int i2, int i3)
    {
        super.drawInfo(minecraft, i, i1, i2, i3);
        progress.draw(minecraft, 40, 24);
        minecraft.fontRenderer.drawString(time / 20 + " Seconds", 10, 45, TextFormatting.BLACK.getColorIndex());
    }
}

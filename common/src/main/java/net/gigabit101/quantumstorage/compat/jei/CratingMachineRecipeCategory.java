package net.gigabit101.quantumstorage.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.gigabit101.quantumstorage.block.entity.CratingMachineBlockEntity;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CratingMachineRecipeCategory implements IRecipeCategory<CratingMachineJeiRecipe> {
    private static final int WIDTH = 118;
    private static final int HEIGHT = 36;

    private final IDrawable icon;
    private final IDrawable arrow;

    public CratingMachineRecipeCategory(IGuiHelper guiHelper) {
        icon = guiHelper.createDrawableItemLike(QuantumStorageContent.CRATER.get());
        arrow = guiHelper.createAnimatedRecipeArrow(CratingMachineBlockEntity.WORK_TIME);
    }

    @Override
    public IRecipeType<CratingMachineJeiRecipe> getRecipeType() {
        return QuantumStorageJeiPlugin.CRATING_MACHINE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.quantumstorage.crating_machine");
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CratingMachineJeiRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(4, 10)
                .setStandardSlotBackground()
                .add(new ItemStack(QuantumStorageContent.CRATE_ITEM.get()));
        builder.addInputSlot(30, 10)
                .setStandardSlotBackground()
                .add(recipe.input());
        builder.addOutputSlot(92, 10)
                .setOutputSlotBackground()
                .add(recipe.output());
    }

    @Override
    public void draw(CratingMachineJeiRecipe recipe, mezz.jei.api.gui.ingredient.IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, 61, 10);
    }

    @Override
    public @Nullable net.minecraft.resources.Identifier getIdentifier(CratingMachineJeiRecipe recipe) {
        return recipe.id();
    }
}

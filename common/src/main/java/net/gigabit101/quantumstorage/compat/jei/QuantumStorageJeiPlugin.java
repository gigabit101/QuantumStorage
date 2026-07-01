package net.gigabit101.quantumstorage.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.gigabit101.quantumstorage.Constants;
import net.gigabit101.quantumstorage.item.CrateItem;
import net.gigabit101.quantumstorage.registry.QuantumStorageBlocks;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class QuantumStorageJeiPlugin implements IModPlugin {
    public static final IRecipeType<CratingMachineJeiRecipe> CRATING_MACHINE = IRecipeType.create(
            Constants.MOD_ID,
            "crating_machine",
            CratingMachineJeiRecipe.class
    );

    private static final Identifier UID = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "jei");

    @Override
    public Identifier getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CratingMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CRATING_MACHINE, createCratingRecipes(registration));
        registration.addItemStackInfo(
                new ItemStack(QuantumStorageContent.block(QuantumStorageBlocks.CRATER)),
                Component.translatable("jei.quantumstorage.crating_machine.info")
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(CRATING_MACHINE, QuantumStorageContent.block(QuantumStorageBlocks.CRATER));
    }

    private static List<CratingMachineJeiRecipe> createCratingRecipes(IRecipeRegistration registration) {
        HolderLookup.Provider registries = registryAccess();
        if (registries == null) {
            return List.of();
        }

        List<CratingMachineJeiRecipe> recipes = new ArrayList<>();
        int index = 0;
        for (ItemStack stack : registration.getIngredientManager().getAllItemStacks()) {
            if (!canCrate(stack)) {
                continue;
            }

            ItemStack input = stack.copy();
            input.setCount(input.getMaxStackSize());
            ItemStack output = CrateItem.createFilledCrate(input, registries);
            if (!output.isEmpty()) {
                recipes.add(new CratingMachineJeiRecipe(recipeId(input, index++), input, output));
            }
        }
        return recipes;
    }

    private static boolean canCrate(ItemStack stack) {
        return !stack.isEmpty()
                && !stack.is(QuantumStorageContent.CRATE_ITEM.get())
                && stack.getMaxStackSize() > 0;
    }

    private static HolderLookup.Provider registryAccess() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            return minecraft.level.registryAccess();
        }
        if (minecraft.player != null) {
            return minecraft.player.registryAccess();
        }
        if (minecraft.getConnection() != null) {
            return minecraft.getConnection().registryAccess();
        }
        return null;
    }

    private static Identifier recipeId(ItemStack input, int index) {
        Identifier itemId = BuiltInRegistries.ITEM.getKey(input.getItem());
        String itemPath = itemId == null ? "unknown" : itemId.toString().replace(':', '/');
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, "crating/" + itemPath + "/" + index);
    }
}

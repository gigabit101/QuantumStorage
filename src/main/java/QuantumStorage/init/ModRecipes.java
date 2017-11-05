package QuantumStorage.init;

import QuantumStorage.config.ConfigQuantumStorage;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import reborncore.common.util.RebornCraftingHelper;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ModRecipes
{
    public static void init()
    {
        RebornCraftingHelper.addShapedOreRecipe(new ItemStack(ModItems.CRATE, 16),
                " W ",
                "WPW",
                " W ",
                'W', "plankWood",
                'P', new ItemStack(Items.PAPER));

        RebornCraftingHelper.addShapedOreRecipe(new ItemStack(ModItems.UPGRADE, 1, 3),
                " W ",
                "WPW",
                " W ",
                'W', new ItemStack(Items.PAPER),
                'P', new ItemStack(Items.WATER_BUCKET));

        if(!ConfigQuantumStorage.disableQuantumBattery)
        {
            RebornCraftingHelper.addShapedOreRecipe(new ItemStack(ModItems.BATTERY, 1),
                    " I ",
                    "IRI",
                    "IRI",
                    'I', new ItemStack(Items.IRON_INGOT),
                    'R', new ItemStack(Blocks.REDSTONE_BLOCK));
        }
    }
}

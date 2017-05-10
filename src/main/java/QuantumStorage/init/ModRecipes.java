package QuantumStorage.init;

import QuantumStorage.api.QuantumStorageAPI;
import QuantumStorage.compat.CompatHandler;
import QuantumStorage.config.ConfigQuantumStorage;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import reborncore.common.util.CraftingHelper;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ModRecipes
{
    public static void init()
    {
        CraftingHelper.addShapedOreRecipe(new ItemStack(ModItems.CRATE, 16),
                " W ",
                "WPW",
                " W ",
                'W', "plankWood",
                'P', new ItemStack(Items.PAPER));

        QuantumStorageAPI.addAltarRecipe(new ItemStack(Items.NETHER_STAR, 1), new ItemStack(ModItems.DISK, 1), ConfigQuantumStorage.defaultDiskTime);
    }
}

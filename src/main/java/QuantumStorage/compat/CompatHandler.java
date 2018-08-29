package QuantumStorage.compat;


import QuantumStorage.api.QuantumStorageAPI;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import reborncore.RebornRegistry;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class CompatHandler
{
    public static Item DISK;
    public static Item DISK_FLUID;

    public static void init()
    {
        DISK = new ItemQuantumStorageDisk();
        RebornRegistry.registerItem(DISK);

        QuantumStorageAPI.addAltarRecipe(new ItemStack(ModBlocks.DSU, 1, 0), new ItemStack(DISK, 1), ConfigQuantumStorage.defaultDiskTime);

        DISK_FLUID = new ItemQuantumStorageDiskFluid();
        RebornRegistry.registerItem(DISK_FLUID);

        QuantumStorageAPI.addAltarRecipe(new ItemStack(ModBlocks.TANK, 1, 0), new ItemStack(DISK_FLUID, 1), ConfigQuantumStorage.defaultDiskTime);
    }
}

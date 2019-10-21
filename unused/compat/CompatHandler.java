package QuantumStorage.compat;


import net.gigabit101.quantumstorage.api.QuantumStorageAPI;
import net.gigabit101.quantumstorage.config.ConfigQuantumStorage;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        
        QuantumStorageAPI.addCrafterRecipe(new ItemStack(ModBlocks.DSU, 1, 0), new ItemStack(DISK, 1), ConfigQuantumStorage.defaultDiskTime);
        
        DISK_FLUID = new ItemQuantumStorageDiskFluid();
        RebornRegistry.registerItem(DISK_FLUID);
        
        QuantumStorageAPI.addCrafterRecipe(new ItemStack(ModBlocks.TANK, 1, 0), new ItemStack(DISK_FLUID, 1), ConfigQuantumStorage.defaultDiskTime);
    }
}

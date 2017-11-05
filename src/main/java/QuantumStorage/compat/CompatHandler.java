package QuantumStorage.compat;


import net.minecraft.item.Item;
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

        DISK_FLUID = new ItemQuantumStorageDiskFluid();
        RebornRegistry.registerItem(DISK_FLUID);
    }
}

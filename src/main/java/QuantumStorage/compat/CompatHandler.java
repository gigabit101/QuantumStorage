package QuantumStorage.compat;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class CompatHandler
{
    public static Item DISK;

    public static void init()
    {
        if(Loader.isModLoaded("refinedstorage"))
        {
            DISK = new ItemQuantumStorageDisk();
            GameRegistry.register(DISK);
        }
    }
}

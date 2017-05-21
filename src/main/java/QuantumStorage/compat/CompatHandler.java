package QuantumStorage.compat;


import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class CompatHandler
{
    public static Item DISK;

    public static void init()
    {
        DISK = new ItemQuantumStorageDisk();
        GameRegistry.register(DISK);
    }
}

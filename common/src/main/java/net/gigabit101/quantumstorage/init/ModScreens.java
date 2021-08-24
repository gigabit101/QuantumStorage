package net.gigabit101.quantumstorage.init;

import dev.architectury.registry.menu.MenuRegistry;
import net.gigabit101.quantumstorage.screens.ScreenQuantumStorageUnit;

public class ModScreens
{
    public static void init()
    {
        MenuRegistry.registerScreenFactory(ModMenus.QUANTUM_STORAGE_UNIT_MENU.get(), ScreenQuantumStorageUnit::new);
    }
}

package net.gigabit101.quantumstorage.init;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.guis.*;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreens
{
    public static void init()
    {
        MenuScreens.register(ModContainers.CHEST_DIAMOND_CONTAINER.get(), GuiChestDiamond::new);
        MenuScreens.register(ModContainers.CHEST_GOLD_CONTAINER.get(), GuiChestGold::new);
        MenuScreens.register(ModContainers.CHEST_IRON_CONTAINER.get(), GuiChestIron::new);
        MenuScreens.register(ModContainers.TRASHCAN_CONTAINER.get(), GuiTrashcan::new);
        MenuScreens.register(ModContainers.QSU_CONTAINER.get(), GuiQSU::new);
        MenuScreens.register(ModContainers.TANK_CONTAINER.get(), GuiTank::new);
        //TODO
//        MenuScreens.register(ModContainers.BAG_CONTAINER.get(), GuiBag::new);
    }
}

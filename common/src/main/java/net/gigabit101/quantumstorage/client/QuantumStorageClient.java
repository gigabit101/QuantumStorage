package net.gigabit101.quantumstorage.client;

import net.creeperhost.polylib.client.modulargui.ModularGuiContainer;
import net.creeperhost.polylib.registry.PolyScreens;
import net.gigabit101.quantumstorage.menu.CratingMachineMenu;
import net.gigabit101.quantumstorage.menu.FluidTrashCanMenu;
import net.gigabit101.quantumstorage.menu.QuantumTankMenu;
import net.gigabit101.quantumstorage.menu.TrashCanMenu;
import net.gigabit101.quantumstorage.menu.QuantumStorageUnitMenu;
import net.gigabit101.quantumstorage.menu.StorageCrateMenu;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;

public final class QuantumStorageClient {
    private static boolean initialized;

    private QuantumStorageClient() {
    }

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        PolyScreens.<QuantumStorageUnitMenu, ModularGuiContainer<QuantumStorageUnitMenu>>register(
                QuantumStorageContent.QUANTUM_STORAGE_UNIT_MENU,
                (menu, inventory, title) -> new ModularGuiContainer<>(menu, inventory, new QuantumStorageUnitGui())
        );
        PolyScreens.<QuantumTankMenu, ModularGuiContainer<QuantumTankMenu>>register(
                QuantumStorageContent.QUANTUM_TANK_MENU,
                (menu, inventory, title) -> new ModularGuiContainer<>(menu, inventory, new QuantumTankGui())
        );
        PolyScreens.<TrashCanMenu, ModularGuiContainer<TrashCanMenu>>register(
                QuantumStorageContent.TRASH_CAN_MENU,
                (menu, inventory, title) -> new ModularGuiContainer<>(menu, inventory, new TrashCanGui())
        );
        PolyScreens.<FluidTrashCanMenu, ModularGuiContainer<FluidTrashCanMenu>>register(
                QuantumStorageContent.FLUID_TRASH_CAN_MENU,
                (menu, inventory, title) -> new ModularGuiContainer<>(menu, inventory, new FluidTrashCanGui())
        );
        PolyScreens.<StorageCrateMenu, ModularGuiContainer<StorageCrateMenu>>register(
                QuantumStorageContent.STORAGE_CRATE_MENU,
                (menu, inventory, title) -> new ModularGuiContainer<>(menu, inventory, new StorageCrateGui())
        );
        PolyScreens.<CratingMachineMenu, ModularGuiContainer<CratingMachineMenu>>register(
                QuantumStorageContent.CRATING_MACHINE_MENU,
                (menu, inventory, title) -> new ModularGuiContainer<>(menu, inventory, new CratingMachineGui())
        );
    }
}

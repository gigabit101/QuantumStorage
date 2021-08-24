package net.gigabit101.quantumstorage.init;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.menu.MenuQuantumStorageUnit;
import net.gigabit101.quantumstorage.menu.MenuTrashCan;
import net.gigabit101.quantumstorage.tiles.TileQuantumStorageUnit;
import net.gigabit101.quantumstorage.tiles.TileTrashCan;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ModMenus
{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(QuantumStorage.MOD_ID, Registry.MENU_REGISTRY);

    public static final RegistrySupplier<MenuType<MenuQuantumStorageUnit>> QUANTUM_STORAGE_UNIT_MENU = MENUS.register("qsu",
            () -> MenuRegistry.ofExtended((id, inventory, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity tileEntity = inventory.player.getCommandSenderWorld().getBlockEntity(pos);
                TileQuantumStorageUnit tileQuantumStorageUnit = (TileQuantumStorageUnit) tileEntity;
                if (!(tileEntity instanceof TileQuantumStorageUnit))
                {
                    return null;
                }
                return new MenuQuantumStorageUnit(id, inventory, tileQuantumStorageUnit);
            }));

    public static final RegistrySupplier<MenuType<MenuTrashCan>> TRASHCAN_MENU = MENUS.register("trashcan",
            () -> MenuRegistry.ofExtended((id, inventory, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity tileEntity = inventory.player.getCommandSenderWorld().getBlockEntity(pos);
                TileTrashCan tileQuantumStorageUnit = (TileTrashCan) tileEntity;
                if (!(tileEntity instanceof TileTrashCan))
                {
                    return null;
                }
                return new MenuTrashCan(id, inventory, tileQuantumStorageUnit);
            }));
}

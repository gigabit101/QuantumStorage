package net.gigabit101.quantumstorage.init;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers
{
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, QuantumStorage.MOD_ID);

    public static final RegistryObject<MenuType<ContainerQSU>> QSU_CONTAINER = CONTAINERS.register("container_qsu", () -> IForgeMenuType.create(ContainerQSU::new));
    public static final RegistryObject<MenuType<ContainerTank>> TANK_CONTAINER = CONTAINERS.register("container_tank", () -> IForgeMenuType.create(ContainerTank::new));
    public static final RegistryObject<MenuType<ContainerTrashcan>> TRASHCAN_CONTAINER = CONTAINERS.register("container_trashcan", () -> IForgeMenuType.create(ContainerTrashcan::new));

    public static final RegistryObject<MenuType<ContainerChestDiamond>> CHEST_DIAMOND_CONTAINER = CONTAINERS.register("container_diamond_chest", () -> IForgeMenuType.create(ContainerChestDiamond::new));
    public static final RegistryObject<MenuType<ContainerChestGold>> CHEST_GOLD_CONTAINER = CONTAINERS.register("container_gold_chest", () -> IForgeMenuType.create(ContainerChestGold::new));
    public static final RegistryObject<MenuType<ContainerChestIron>> CHEST_IRON_CONTAINER = CONTAINERS.register("container_iron_chest", () -> IForgeMenuType.create(ContainerChestIron::new));

    //TODO
//    public static final RegistryObject<MenuType<ContainerBag>> BAG_CONTAINER = CONTAINERS.register("container_bag", () -> IForgeMenuType.create(ContainerBag::new));

}

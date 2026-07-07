package net.gigabit101.quantumstorage.registry;

import net.gigabit101.quantumstorage.Constants;
import net.gigabit101.quantumstorage.block.MultiblockStorageBlock;
import net.gigabit101.quantumstorage.block.QuantumStorageBlock;
import net.gigabit101.quantumstorage.block.QuantumStorageUnitBlock;
import net.gigabit101.quantumstorage.block.StorageCrateBlock;
import net.gigabit101.quantumstorage.block.entity.CratingMachineBlockEntity;
import net.gigabit101.quantumstorage.block.entity.FluidTrashCanBlockEntity;
import net.gigabit101.quantumstorage.block.entity.MultiblockStorageBlockEntity;
import net.gigabit101.quantumstorage.block.entity.QuantumTankBlockEntity;
import net.gigabit101.quantumstorage.block.entity.QuantumStorageUnitBlockEntity;
import net.gigabit101.quantumstorage.block.entity.StorageCrateBlockEntity;
import net.gigabit101.quantumstorage.block.entity.TrashCanBlockEntity;
import net.gigabit101.quantumstorage.item.CrateItem;
import net.gigabit101.quantumstorage.item.QuantumStorageBlockItem;
import net.gigabit101.quantumstorage.item.UpgradeItem;
import net.gigabit101.quantumstorage.menu.CratingMachineMenu;
import net.gigabit101.quantumstorage.menu.FluidTrashCanMenu;
import net.gigabit101.quantumstorage.menu.MultiblockStorageMenu;
import net.gigabit101.quantumstorage.menu.QuantumTankMenu;
import net.gigabit101.quantumstorage.menu.QuantumStorageUnitMenu;
import net.gigabit101.quantumstorage.menu.StorageCrateMenu;
import net.gigabit101.quantumstorage.menu.TrashCanMenu;
import net.creeperhost.polylib.registry.PolyRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class QuantumStorageContent {
    private static final PolyRegistry<Block> BLOCKS = PolyRegistry.create(Registries.BLOCK, Constants.MOD_ID);
    private static final PolyRegistry<Item> ITEMS = PolyRegistry.create(Registries.ITEM, Constants.MOD_ID);
    private static final PolyRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = PolyRegistry.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);
    private static final PolyRegistry<MenuType<?>> MENUS = PolyRegistry.create(Registries.MENU, Constants.MOD_ID);
    private static final PolyRegistry<CreativeModeTab> CREATIVE_TABS = PolyRegistry.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    private static final Map<QuantumStorageBlockEntry, Supplier<Block>> REGISTERED_BLOCKS = new LinkedHashMap<>();
    private static boolean registered;

    public static Supplier<CreativeModeTab> MAIN_TAB;
    public static Supplier<BlockEntityType<QuantumTankBlockEntity>> QUANTUM_TANK_BLOCK_ENTITY;
    public static Supplier<BlockEntityType<QuantumStorageUnitBlockEntity>> QUANTUM_STORAGE_UNIT_BLOCK_ENTITY;
    public static Supplier<BlockEntityType<TrashCanBlockEntity>> TRASH_CAN_BLOCK_ENTITY;
    public static Supplier<BlockEntityType<FluidTrashCanBlockEntity>> FLUID_TRASH_CAN_BLOCK_ENTITY;
    public static Supplier<BlockEntityType<StorageCrateBlockEntity>> STORAGE_CRATE_BLOCK_ENTITY;
    public static Supplier<BlockEntityType<CratingMachineBlockEntity>> CRATING_MACHINE_BLOCK_ENTITY;
    public static Supplier<BlockEntityType<MultiblockStorageBlockEntity>> MULTIBLOCK_STORAGE_BLOCK_ENTITY;
    public static Supplier<MenuType<QuantumTankMenu>> QUANTUM_TANK_MENU;
    public static Supplier<MenuType<QuantumStorageUnitMenu>> QUANTUM_STORAGE_UNIT_MENU;
    public static Supplier<MenuType<TrashCanMenu>> TRASH_CAN_MENU;
    public static Supplier<MenuType<FluidTrashCanMenu>> FLUID_TRASH_CAN_MENU;
    public static Supplier<MenuType<StorageCrateMenu>> STORAGE_CRATE_MENU;
    public static Supplier<MenuType<CratingMachineMenu>> CRATING_MACHINE_MENU;
    public static Supplier<MenuType<MultiblockStorageMenu>> MULTIBLOCK_STORAGE_MENU;
    public static Supplier<Item> CRATE_ITEM;
    public static Supplier<Item> RENDER_UPGRADE_ITEM;
    public static Supplier<Item> VOID_UPGRADE_ITEM;
    public static Supplier<Item> CREATIVE_UPGRADE_ITEM;
    public static Supplier<Item> WATER_UPGRADE_ITEM;

    private QuantumStorageContent() {
    }

    public static void register() {
        if (registered) {
            return;
        }
        registered = true;

        for (QuantumStorageBlockEntry entry : QuantumStorageBlocks.ALL) {
            Supplier<Block> block = BLOCKS.registerBlock(entry.id(), properties -> createBlock(entry, properties));
            ITEMS.registerItem(entry.id(), properties -> createBlockItem(entry, block.get(), properties));
            REGISTERED_BLOCKS.put(entry, block);
        }
        CRATE_ITEM = ITEMS.registerItem("crate", CrateItem::new);
        RENDER_UPGRADE_ITEM = ITEMS.registerItem(UpgradeItem.Type.RENDER.itemId(), properties -> new UpgradeItem(UpgradeItem.Type.RENDER, properties));
        VOID_UPGRADE_ITEM = ITEMS.registerItem(UpgradeItem.Type.VOID.itemId(), properties -> new UpgradeItem(UpgradeItem.Type.VOID, properties));
        CREATIVE_UPGRADE_ITEM = ITEMS.registerItem(UpgradeItem.Type.CREATIVE.itemId(), properties -> new UpgradeItem(UpgradeItem.Type.CREATIVE, properties));
        WATER_UPGRADE_ITEM = ITEMS.registerItem(UpgradeItem.Type.WATER.itemId(), properties -> new UpgradeItem(UpgradeItem.Type.WATER, properties));

        QUANTUM_TANK_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
                QuantumStorageBlocks.QUANTUM_TANK.id(),
                () -> new BlockEntityType<>(
                        QuantumTankBlockEntity::new,
                        Set.of(REGISTERED_BLOCKS.get(QuantumStorageBlocks.QUANTUM_TANK).get())
                )
        );
        QUANTUM_STORAGE_UNIT_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
                QuantumStorageBlocks.QUANTUM_STORAGE_UNIT.id(),
                () -> new BlockEntityType<>(
                        QuantumStorageUnitBlockEntity::new,
                        Set.of(REGISTERED_BLOCKS.get(QuantumStorageBlocks.QUANTUM_STORAGE_UNIT).get())
                )
        );
        TRASH_CAN_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
                QuantumStorageBlocks.TRASH_CAN.id(),
                () -> new BlockEntityType<>(
                        TrashCanBlockEntity::new,
                        Set.of(REGISTERED_BLOCKS.get(QuantumStorageBlocks.TRASH_CAN).get())
                )
        );
        FLUID_TRASH_CAN_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
                QuantumStorageBlocks.TRASH_CAN_FLUID.id(),
                () -> new BlockEntityType<>(
                        FluidTrashCanBlockEntity::new,
                        Set.of(REGISTERED_BLOCKS.get(QuantumStorageBlocks.TRASH_CAN_FLUID).get())
                )
        );
        STORAGE_CRATE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
                "storage_crate",
                () -> new BlockEntityType<>(
                        StorageCrateBlockEntity::new,
                        Set.of(
                                REGISTERED_BLOCKS.get(QuantumStorageBlocks.CHEST_IRON).get(),
                                REGISTERED_BLOCKS.get(QuantumStorageBlocks.CHEST_GOLD).get(),
                                REGISTERED_BLOCKS.get(QuantumStorageBlocks.CHEST_DIAMOND).get(),
                                REGISTERED_BLOCKS.get(QuantumStorageBlocks.CHEST_QUANTUM).get()
                        )
                )
        );
        CRATING_MACHINE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
                QuantumStorageBlocks.CRATER.id(),
                () -> new BlockEntityType<>(
                        CratingMachineBlockEntity::new,
                        Set.of(REGISTERED_BLOCKS.get(QuantumStorageBlocks.CRATER).get())
                )
        );
        MULTIBLOCK_STORAGE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
                "multiblock_storage",
                () -> new BlockEntityType<>(
                        MultiblockStorageBlockEntity::new,
                        Set.of(
                                REGISTERED_BLOCKS.get(QuantumStorageBlocks.MULTISTORAGE).get(),
                                REGISTERED_BLOCKS.get(QuantumStorageBlocks.MULTISTORAGE_FRAME).get(),
                                REGISTERED_BLOCKS.get(QuantumStorageBlocks.MULTISTORAGE_HEAT).get(),
                                REGISTERED_BLOCKS.get(QuantumStorageBlocks.MULTISTORAGE_IO).get()
                        )
                )
        );
        QUANTUM_TANK_MENU = MENUS.registerMenu(QuantumStorageBlocks.QUANTUM_TANK.id(), QuantumTankMenu::new);
        QUANTUM_STORAGE_UNIT_MENU = MENUS.registerMenu(QuantumStorageBlocks.QUANTUM_STORAGE_UNIT.id(), QuantumStorageUnitMenu::new);
        TRASH_CAN_MENU = MENUS.registerMenu(QuantumStorageBlocks.TRASH_CAN.id(), TrashCanMenu::new);
        FLUID_TRASH_CAN_MENU = MENUS.registerMenu(QuantumStorageBlocks.TRASH_CAN_FLUID.id(), FluidTrashCanMenu::new);
        STORAGE_CRATE_MENU = MENUS.registerMenu("storage_crate", StorageCrateMenu::new);
        CRATING_MACHINE_MENU = MENUS.registerMenu(QuantumStorageBlocks.CRATER.id(), CratingMachineMenu::new);
        MULTIBLOCK_STORAGE_MENU = MENUS.registerMenu("multiblock_storage", MultiblockStorageMenu::new);

        MAIN_TAB = CREATIVE_TABS.registerCreativeTab(
                "main",
                "itemGroup.quantumstorage",
                () -> new ItemStack(REGISTERED_BLOCKS.get(QuantumStorageBlocks.QUANTUM_STORAGE_UNIT).get()),
                creativeTabItems()
        );
    }

    private static Block createBlock(QuantumStorageBlockEntry entry, BlockBehaviour.Properties properties) {
        BlockBehaviour.Properties quantumProperties = QuantumStorageBlock.properties(properties);
        if (entry == QuantumStorageBlocks.QUANTUM_STORAGE_UNIT) {
            return new QuantumStorageUnitBlock(quantumProperties)
                    .setBlockEntity(() -> QUANTUM_STORAGE_UNIT_BLOCK_ENTITY.get(), true);
        }
        if (entry == QuantumStorageBlocks.QUANTUM_TANK) {
            return new net.creeperhost.polylib.blocks.PolyEntityBlock(quantumProperties.noOcclusion())
                    .setBlockEntity(() -> QUANTUM_TANK_BLOCK_ENTITY.get(), true);
        }
        if (entry == QuantumStorageBlocks.CRATER) {
            return new net.creeperhost.polylib.blocks.PolyEntityBlock(quantumProperties.noOcclusion())
                    .setBlockEntity(() -> CRATING_MACHINE_BLOCK_ENTITY.get(), true);
        }
        if (entry == QuantumStorageBlocks.TRASH_CAN) {
            return new net.creeperhost.polylib.blocks.PolyEntityBlock(quantumProperties.noOcclusion())
                    .setBlockEntity(() -> TRASH_CAN_BLOCK_ENTITY.get(), false);
        }
        if (entry == QuantumStorageBlocks.TRASH_CAN_FLUID) {
            return new net.creeperhost.polylib.blocks.PolyEntityBlock(quantumProperties.noOcclusion())
                    .setBlockEntity(() -> FLUID_TRASH_CAN_BLOCK_ENTITY.get(), true);
        }
        if (entry == QuantumStorageBlocks.CHEST_IRON
                || entry == QuantumStorageBlocks.CHEST_GOLD
                || entry == QuantumStorageBlocks.CHEST_DIAMOND
                || entry == QuantumStorageBlocks.CHEST_QUANTUM) {
            return new StorageCrateBlock(quantumProperties.noOcclusion())
                    .setBlockEntity(() -> STORAGE_CRATE_BLOCK_ENTITY.get(), false);
        }
        if (entry == QuantumStorageBlocks.MULTISTORAGE
                || entry == QuantumStorageBlocks.MULTISTORAGE_FRAME
                || entry == QuantumStorageBlocks.MULTISTORAGE_HEAT
                || entry == QuantumStorageBlocks.MULTISTORAGE_IO) {
            return new MultiblockStorageBlock(quantumProperties);
        }
        return new QuantumStorageBlock(quantumProperties);
    }

    private static BlockItem createBlockItem(QuantumStorageBlockEntry entry, Block block, Item.Properties properties) {
        String infoKey = "tooltip.quantumstorage.block." + entry.id();
        if (entry == QuantumStorageBlocks.QUANTUM_STORAGE_UNIT) {
            return new QuantumStorageBlockItem(block, properties, QuantumStorageBlockItem.TooltipType.QUANTUM_STORAGE_UNIT, infoKey);
        }
        if (entry == QuantumStorageBlocks.QUANTUM_TANK) {
            return new QuantumStorageBlockItem(block, properties, QuantumStorageBlockItem.TooltipType.QUANTUM_TANK, infoKey);
        }
        if (entry == QuantumStorageBlocks.CHEST_IRON) {
            return new QuantumStorageBlockItem(block, properties, StorageCrateBlockEntity.CrateTier.IRON, infoKey);
        }
        if (entry == QuantumStorageBlocks.CHEST_GOLD) {
            return new QuantumStorageBlockItem(block, properties, StorageCrateBlockEntity.CrateTier.GOLD, infoKey);
        }
        if (entry == QuantumStorageBlocks.CHEST_DIAMOND) {
            return new QuantumStorageBlockItem(block, properties, StorageCrateBlockEntity.CrateTier.DIAMOND, infoKey);
        }
        if (entry == QuantumStorageBlocks.CHEST_QUANTUM) {
            return new QuantumStorageBlockItem(block, properties, StorageCrateBlockEntity.CrateTier.QUANTUM, infoKey);
        }
        return new QuantumStorageBlockItem(block, properties, QuantumStorageBlockItem.TooltipType.NONE, infoKey);
    }

    @SuppressWarnings("unchecked")
    private static CreativeModeTab.DisplayItemsGenerator creativeTabItems() {
        try {
            Class<?> outputClass = Class.forName("net.minecraft.world.item.CreativeModeTab$Output");
            Method accept = outputClass.getMethod("accept", ItemLike.class);

            return (CreativeModeTab.DisplayItemsGenerator) Proxy.newProxyInstance(
                    CreativeModeTab.DisplayItemsGenerator.class.getClassLoader(),
                    new Class<?>[]{CreativeModeTab.DisplayItemsGenerator.class},
                    (proxy, method, args) -> {
                        if ("accept".equals(method.getName()) && args != null && args.length == 2) {
                            Object output = args[1];
                            for (Supplier<Block> block : REGISTERED_BLOCKS.values()) {
                                accept.invoke(output, block.get());
                            }
                            accept.invoke(output, CRATE_ITEM.get());
                            accept.invoke(output, RENDER_UPGRADE_ITEM.get());
                            accept.invoke(output, VOID_UPGRADE_ITEM.get());
                            accept.invoke(output, CREATIVE_UPGRADE_ITEM.get());
                            accept.invoke(output, WATER_UPGRADE_ITEM.get());
                        }
                        return null;
                    }
            );
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to create QuantumStorage creative tab contents", exception);
        }
    }

    public static void init() {
        BLOCKS.init();
        ITEMS.init();
        BLOCK_ENTITY_TYPES.init();
        MENUS.init();
        CREATIVE_TABS.init();
    }

    public static Block block(QuantumStorageBlockEntry entry) {
        Supplier<Block> block = REGISTERED_BLOCKS.get(entry);
        if (block == null) {
            throw new IllegalArgumentException("Unknown QuantumStorage block entry: " + entry);
        }
        return block.get();
    }
}

package net.gigabit101.quantumstorage.registry;

import net.creeperhost.polylib.blocks.PolyEntityBlock;
import net.creeperhost.polylib.registry.PolyRegistry;
import net.gigabit101.quantumstorage.Constants;
import net.gigabit101.quantumstorage.block.MultiblockStorageBlock;
import net.gigabit101.quantumstorage.block.QuantumStorageBlock;
import net.gigabit101.quantumstorage.block.QuantumStorageUnitBlock;
import net.gigabit101.quantumstorage.block.StorageCrateBlock;
import net.gigabit101.quantumstorage.block.entity.CratingMachineBlockEntity;
import net.gigabit101.quantumstorage.block.entity.FluidTrashCanBlockEntity;
import net.gigabit101.quantumstorage.block.entity.MultiblockStorageBlockEntity;
import net.gigabit101.quantumstorage.block.entity.QuantumStorageUnitBlockEntity;
import net.gigabit101.quantumstorage.block.entity.QuantumTankBlockEntity;
import net.gigabit101.quantumstorage.block.entity.StorageCrateBlockEntity;
import net.gigabit101.quantumstorage.block.entity.TrashCanBlockEntity;
import net.gigabit101.quantumstorage.item.CrateItem;
import net.gigabit101.quantumstorage.item.QuantumStorageBlockItem;
import net.gigabit101.quantumstorage.item.UpgradeItem;
import net.gigabit101.quantumstorage.menu.CratingMachineMenu;
import net.gigabit101.quantumstorage.menu.FluidTrashCanMenu;
import net.gigabit101.quantumstorage.menu.MultiblockStorageMenu;
import net.gigabit101.quantumstorage.menu.QuantumStorageUnitMenu;
import net.gigabit101.quantumstorage.menu.QuantumTankMenu;
import net.gigabit101.quantumstorage.menu.StorageCrateMenu;
import net.gigabit101.quantumstorage.menu.TrashCanMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;
import java.util.function.Supplier;

public final class QuantumStorageContent {
    public static final int BLOCK_DEFINITION_COUNT = 14;

    public static final String QUANTUM_TANK_ID = "quantum_tank";
    public static final String CRATER_ID = "crater";
    public static final String CHEST_IRON_ID = "chest_iron";
    public static final String CHEST_GOLD_ID = "chest_gold";
    public static final String CHEST_DIAMOND_ID = "chest_diamond";
    public static final String QUANTUM_STORAGE_UNIT_ID = "quantum_storage_unit";
    public static final String QUANTUM_CRAFTER_ID = "quantumcrafter";
    public static final String TRASH_CAN_ID = "trashcan";
    public static final String TRASH_CAN_FLUID_ID = "trashcanfluid";
    public static final String CHEST_QUANTUM_ID = "chest_quantum";
    public static final String MULTISTORAGE_ID = "multistorage";
    public static final String MULTISTORAGE_FRAME_ID = "multistorage_frame";
    public static final String MULTISTORAGE_HEAT_ID = "multistorage_heat";
    public static final String MULTISTORAGE_IO_ID = "multistorage_io";

    public static final PolyRegistry<Block> BLOCKS = PolyRegistry.create(Registries.BLOCK, Constants.MOD_ID);
    public static final PolyRegistry<Item> ITEMS = PolyRegistry.create(Registries.ITEM, Constants.MOD_ID);
    public static final PolyRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = PolyRegistry.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);
    public static final PolyRegistry<MenuType<?>> MENUS = PolyRegistry.create(Registries.MENU, Constants.MOD_ID);
    public static final PolyRegistry<CreativeModeTab> CREATIVE_TABS = PolyRegistry.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final Supplier<Block> QUANTUM_TANK = BLOCKS.registerBlock(QUANTUM_TANK_ID, "Quantum Tank", properties -> new PolyEntityBlock(QuantumStorageBlock.properties(properties).noOcclusion()).setBlockEntity(() -> QuantumStorageContent.QUANTUM_TANK_BLOCK_ENTITY.get(), true));
    public static final Supplier<Block> CRATER = BLOCKS.registerBlock(CRATER_ID, "Crating Machine", properties -> new PolyEntityBlock(QuantumStorageBlock.properties(properties).noOcclusion()).setBlockEntity(() -> QuantumStorageContent.CRATING_MACHINE_BLOCK_ENTITY.get(), true));
    public static final Supplier<Block> CHEST_IRON = BLOCKS.registerBlock(CHEST_IRON_ID, "Iron Storage Crate", properties -> new StorageCrateBlock(QuantumStorageBlock.properties(properties).noOcclusion()).setBlockEntity(() -> QuantumStorageContent.STORAGE_CRATE_BLOCK_ENTITY.get(), false));
    public static final Supplier<Block> CHEST_GOLD = BLOCKS.registerBlock(CHEST_GOLD_ID, "Gold Storage Crate", properties -> new StorageCrateBlock(QuantumStorageBlock.properties(properties).noOcclusion()).setBlockEntity(() -> QuantumStorageContent.STORAGE_CRATE_BLOCK_ENTITY.get(), false));
    public static final Supplier<Block> CHEST_DIAMOND = BLOCKS.registerBlock(CHEST_DIAMOND_ID, "Diamond Storage Crate", properties -> new StorageCrateBlock(QuantumStorageBlock.properties(properties).noOcclusion()).setBlockEntity(() -> QuantumStorageContent.STORAGE_CRATE_BLOCK_ENTITY.get(), false));
    public static final Supplier<Block> QUANTUM_STORAGE_UNIT = BLOCKS.registerBlock(QUANTUM_STORAGE_UNIT_ID, "Quantum Storage Unit", properties -> new QuantumStorageUnitBlock(QuantumStorageBlock.properties(properties)).setBlockEntity(() -> QuantumStorageContent.QUANTUM_STORAGE_UNIT_BLOCK_ENTITY.get(), true));
    public static final Supplier<Block> QUANTUM_CRAFTER = BLOCKS.registerBlock(QUANTUM_CRAFTER_ID, "Quantum Crafter", properties -> new QuantumStorageBlock(QuantumStorageBlock.properties(properties)));
    public static final Supplier<Block> TRASH_CAN = BLOCKS.registerBlock(TRASH_CAN_ID, "Trash Can", properties -> new PolyEntityBlock(QuantumStorageBlock.properties(properties).noOcclusion()).setBlockEntity(() -> QuantumStorageContent.TRASH_CAN_BLOCK_ENTITY.get(), false));
    public static final Supplier<Block> TRASH_CAN_FLUID = BLOCKS.registerBlock(TRASH_CAN_FLUID_ID, "Fluid Trash Can", properties -> new PolyEntityBlock(QuantumStorageBlock.properties(properties).noOcclusion()).setBlockEntity(() -> QuantumStorageContent.FLUID_TRASH_CAN_BLOCK_ENTITY.get(), true));
    public static final Supplier<Block> CHEST_QUANTUM = BLOCKS.registerBlock(CHEST_QUANTUM_ID, "Quantum Storage Crate", properties -> new StorageCrateBlock(QuantumStorageBlock.properties(properties).noOcclusion()).setBlockEntity(() -> QuantumStorageContent.STORAGE_CRATE_BLOCK_ENTITY.get(), false));
    public static final Supplier<Block> MULTISTORAGE = BLOCKS.registerBlock(MULTISTORAGE_ID, "Storage Unit", properties -> new MultiblockStorageBlock(QuantumStorageBlock.properties(properties)));
    public static final Supplier<Block> MULTISTORAGE_FRAME = BLOCKS.registerBlock(MULTISTORAGE_FRAME_ID, "Storage Frame", properties -> new MultiblockStorageBlock(QuantumStorageBlock.properties(properties)));
    public static final Supplier<Block> MULTISTORAGE_HEAT = BLOCKS.registerBlock(MULTISTORAGE_HEAT_ID, "Heat Conductor", properties -> new MultiblockStorageBlock(QuantumStorageBlock.properties(properties)));
    public static final Supplier<Block> MULTISTORAGE_IO = BLOCKS.registerBlock(MULTISTORAGE_IO_ID, "Storage IO", properties -> new MultiblockStorageBlock(QuantumStorageBlock.properties(properties)));

    public static final Supplier<Item> QUANTUM_TANK_ITEM = ITEMS.registerItem(QUANTUM_TANK_ID, "Quantum Tank", properties -> new QuantumStorageBlockItem(QUANTUM_TANK.get(), properties, QuantumStorageBlockItem.TooltipType.QUANTUM_TANK, "tooltip.quantumstorage.block.quantum_tank"));
    public static final Supplier<Item> CRATER_ITEM = ITEMS.registerItem(CRATER_ID, "Crating Machine", properties -> new QuantumStorageBlockItem(CRATER.get(), properties, QuantumStorageBlockItem.TooltipType.NONE, "tooltip.quantumstorage.block.crater"));
    public static final Supplier<Item> CHEST_IRON_ITEM = ITEMS.registerItem(CHEST_IRON_ID, "Iron Storage Crate", properties -> new QuantumStorageBlockItem(CHEST_IRON.get(), properties, StorageCrateBlockEntity.CrateTier.IRON, "tooltip.quantumstorage.block.chest_iron"));
    public static final Supplier<Item> CHEST_GOLD_ITEM = ITEMS.registerItem(CHEST_GOLD_ID, "Gold Storage Crate", properties -> new QuantumStorageBlockItem(CHEST_GOLD.get(), properties, StorageCrateBlockEntity.CrateTier.GOLD, "tooltip.quantumstorage.block.chest_gold"));
    public static final Supplier<Item> CHEST_DIAMOND_ITEM = ITEMS.registerItem(CHEST_DIAMOND_ID, "Diamond Storage Crate", properties -> new QuantumStorageBlockItem(CHEST_DIAMOND.get(), properties, StorageCrateBlockEntity.CrateTier.DIAMOND, "tooltip.quantumstorage.block.chest_diamond"));
    public static final Supplier<Item> QUANTUM_STORAGE_UNIT_ITEM = ITEMS.registerItem(QUANTUM_STORAGE_UNIT_ID, "Quantum Storage Unit", properties -> new QuantumStorageBlockItem(QUANTUM_STORAGE_UNIT.get(), properties, QuantumStorageBlockItem.TooltipType.QUANTUM_STORAGE_UNIT, "tooltip.quantumstorage.block.quantum_storage_unit"));
    public static final Supplier<Item> QUANTUM_CRAFTER_ITEM = ITEMS.registerItem(QUANTUM_CRAFTER_ID, "Quantum Crafter", properties -> new QuantumStorageBlockItem(QUANTUM_CRAFTER.get(), properties, QuantumStorageBlockItem.TooltipType.NONE, "tooltip.quantumstorage.block.quantumcrafter"));
    public static final Supplier<Item> TRASH_CAN_ITEM = ITEMS.registerItem(TRASH_CAN_ID, "Trash Can", properties -> new QuantumStorageBlockItem(TRASH_CAN.get(), properties, QuantumStorageBlockItem.TooltipType.NONE, "tooltip.quantumstorage.block.trashcan"));
    public static final Supplier<Item> TRASH_CAN_FLUID_ITEM = ITEMS.registerItem(TRASH_CAN_FLUID_ID, "Fluid Trash Can", properties -> new QuantumStorageBlockItem(TRASH_CAN_FLUID.get(), properties, QuantumStorageBlockItem.TooltipType.NONE, "tooltip.quantumstorage.block.trashcanfluid"));
    public static final Supplier<Item> CHEST_QUANTUM_ITEM = ITEMS.registerItem(CHEST_QUANTUM_ID, "Quantum Storage Crate", properties -> new QuantumStorageBlockItem(CHEST_QUANTUM.get(), properties, StorageCrateBlockEntity.CrateTier.QUANTUM, "tooltip.quantumstorage.block.chest_quantum"));
    public static final Supplier<Item> MULTISTORAGE_ITEM = ITEMS.registerItem(MULTISTORAGE_ID, "Storage Unit", properties -> new QuantumStorageBlockItem(MULTISTORAGE.get(), properties, QuantumStorageBlockItem.TooltipType.NONE, "tooltip.quantumstorage.block.multistorage"));
    public static final Supplier<Item> MULTISTORAGE_FRAME_ITEM = ITEMS.registerItem(MULTISTORAGE_FRAME_ID, "Storage Frame", properties -> new QuantumStorageBlockItem(MULTISTORAGE_FRAME.get(), properties, QuantumStorageBlockItem.TooltipType.NONE, "tooltip.quantumstorage.block.multistorage_frame"));
    public static final Supplier<Item> MULTISTORAGE_HEAT_ITEM = ITEMS.registerItem(MULTISTORAGE_HEAT_ID, "Heat Conductor", properties -> new QuantumStorageBlockItem(MULTISTORAGE_HEAT.get(), properties, QuantumStorageBlockItem.TooltipType.NONE, "tooltip.quantumstorage.block.multistorage_heat"));
    public static final Supplier<Item> MULTISTORAGE_IO_ITEM = ITEMS.registerItem(MULTISTORAGE_IO_ID, "Storage IO", properties -> new QuantumStorageBlockItem(MULTISTORAGE_IO.get(), properties, QuantumStorageBlockItem.TooltipType.NONE, "tooltip.quantumstorage.block.multistorage_io"));

    public static final Supplier<Item> CRATE_ITEM = ITEMS.registerItem("crate", "Crate", CrateItem::new);
    public static final Supplier<Item> RENDER_UPGRADE_ITEM = ITEMS.registerItem(UpgradeItem.Type.RENDER.itemId(), "Render Upgrade", properties -> new UpgradeItem(UpgradeItem.Type.RENDER, properties));
    public static final Supplier<Item> VOID_UPGRADE_ITEM = ITEMS.registerItem(UpgradeItem.Type.VOID.itemId(), "Void Upgrade", properties -> new UpgradeItem(UpgradeItem.Type.VOID, properties));
    public static final Supplier<Item> CREATIVE_UPGRADE_ITEM = ITEMS.registerItem(UpgradeItem.Type.CREATIVE.itemId(), "Creative Upgrade", properties -> new UpgradeItem(UpgradeItem.Type.CREATIVE, properties));
    public static final Supplier<Item> WATER_UPGRADE_ITEM = ITEMS.registerItem(UpgradeItem.Type.WATER.itemId(), "Infinite Water Upgrade", properties -> new UpgradeItem(UpgradeItem.Type.WATER, properties));

    public static final Supplier<BlockEntityType<QuantumTankBlockEntity>> QUANTUM_TANK_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            QUANTUM_TANK_ID,
            () -> new BlockEntityType<>(QuantumTankBlockEntity::new, Set.of(QUANTUM_TANK.get()))
    );
    public static final Supplier<BlockEntityType<QuantumStorageUnitBlockEntity>> QUANTUM_STORAGE_UNIT_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            QUANTUM_STORAGE_UNIT_ID,
            () -> new BlockEntityType<>(QuantumStorageUnitBlockEntity::new, Set.of(QUANTUM_STORAGE_UNIT.get()))
    );
    public static final Supplier<BlockEntityType<TrashCanBlockEntity>> TRASH_CAN_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            TRASH_CAN_ID,
            () -> new BlockEntityType<>(TrashCanBlockEntity::new, Set.of(TRASH_CAN.get()))
    );
    public static final Supplier<BlockEntityType<FluidTrashCanBlockEntity>> FLUID_TRASH_CAN_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            TRASH_CAN_FLUID_ID,
            () -> new BlockEntityType<>(FluidTrashCanBlockEntity::new, Set.of(TRASH_CAN_FLUID.get()))
    );
    public static final Supplier<BlockEntityType<StorageCrateBlockEntity>> STORAGE_CRATE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "storage_crate",
            () -> new BlockEntityType<>(StorageCrateBlockEntity::new, Set.of(CHEST_IRON.get(), CHEST_GOLD.get(), CHEST_DIAMOND.get(), CHEST_QUANTUM.get()))
    );
    public static final Supplier<BlockEntityType<CratingMachineBlockEntity>> CRATING_MACHINE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            CRATER_ID,
            () -> new BlockEntityType<>(CratingMachineBlockEntity::new, Set.of(CRATER.get()))
    );
    public static final Supplier<BlockEntityType<MultiblockStorageBlockEntity>> MULTIBLOCK_STORAGE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "multiblock_storage",
            () -> new BlockEntityType<>(MultiblockStorageBlockEntity::new, Set.of(MULTISTORAGE.get(), MULTISTORAGE_FRAME.get(), MULTISTORAGE_HEAT.get(), MULTISTORAGE_IO.get()))
    );

    public static final Supplier<MenuType<QuantumTankMenu>> QUANTUM_TANK_MENU = MENUS.registerMenu(QUANTUM_TANK_ID, QuantumTankMenu::new);
    public static final Supplier<MenuType<QuantumStorageUnitMenu>> QUANTUM_STORAGE_UNIT_MENU = MENUS.registerMenu(QUANTUM_STORAGE_UNIT_ID, QuantumStorageUnitMenu::new);
    public static final Supplier<MenuType<TrashCanMenu>> TRASH_CAN_MENU = MENUS.registerMenu(TRASH_CAN_ID, TrashCanMenu::new);
    public static final Supplier<MenuType<FluidTrashCanMenu>> FLUID_TRASH_CAN_MENU = MENUS.registerMenu(TRASH_CAN_FLUID_ID, FluidTrashCanMenu::new);
    public static final Supplier<MenuType<StorageCrateMenu>> STORAGE_CRATE_MENU = MENUS.registerMenu("storage_crate", StorageCrateMenu::new);
    public static final Supplier<MenuType<CratingMachineMenu>> CRATING_MACHINE_MENU = MENUS.registerMenu(CRATER_ID, CratingMachineMenu::new);
    public static final Supplier<MenuType<MultiblockStorageMenu>> MULTIBLOCK_STORAGE_MENU = MENUS.registerMenu("multiblock_storage", MultiblockStorageMenu::new);

    public static final Supplier<CreativeModeTab> MAIN_TAB = CREATIVE_TABS.registerCreativeTab(
            "main",
            "Quantum Storage",
            () -> new ItemStack(QUANTUM_STORAGE_UNIT.get()),
            (params, output) -> {
                output.accept(QUANTUM_TANK_ITEM.get());
                output.accept(CRATER_ITEM.get());
                output.accept(CHEST_IRON_ITEM.get());
                output.accept(CHEST_GOLD_ITEM.get());
                output.accept(CHEST_DIAMOND_ITEM.get());
                output.accept(QUANTUM_STORAGE_UNIT_ITEM.get());
                output.accept(QUANTUM_CRAFTER_ITEM.get());
                output.accept(TRASH_CAN_ITEM.get());
                output.accept(TRASH_CAN_FLUID_ITEM.get());
                output.accept(CHEST_QUANTUM_ITEM.get());
                output.accept(MULTISTORAGE_ITEM.get());
                output.accept(MULTISTORAGE_FRAME_ITEM.get());
                output.accept(MULTISTORAGE_HEAT_ITEM.get());
                output.accept(MULTISTORAGE_IO_ITEM.get());
                output.accept(CRATE_ITEM.get());
                output.accept(RENDER_UPGRADE_ITEM.get());
                output.accept(VOID_UPGRADE_ITEM.get());
                output.accept(CREATIVE_UPGRADE_ITEM.get());
                output.accept(WATER_UPGRADE_ITEM.get());
            }
    );

    public static void init() {
        BLOCKS.init();
        ITEMS.init();
        BLOCK_ENTITY_TYPES.init();
        MENUS.init();
        CREATIVE_TABS.init();
    }
}

package net.gigabit101.quantumstorage.init;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.blocks.*;
import net.gigabit101.quantumstorage.tiles.TileController;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.gigabit101.quantumstorage.tiles.TileTank;
import net.gigabit101.quantumstorage.tiles.TileTrashcan;
import net.gigabit101.quantumstorage.tiles.chests.TileChestDiamond;
import net.gigabit101.quantumstorage.tiles.chests.TileChestGold;
import net.gigabit101.quantumstorage.tiles.chests.TileChestIron;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, QuantumStorage.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILES_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, QuantumStorage.MOD_ID);
    
    public static final RegistryObject<Block> CHEST_DIAMOND = BLOCKS.register("chestdiamond", BlockChestDiamond::new);
    public static final RegistryObject<Block> CHEST_GOLD = BLOCKS.register("chestgold", BlockChestGold::new);
    public static final RegistryObject<Block> CHEST_IRON = BLOCKS.register("chestiron", BlockChestIron::new);
    
    public static final RegistryObject<Block> TRASH_CAN = BLOCKS.register("trashcan", BlockTrashcan::new);
    public static final RegistryObject<Block> QSU = BLOCKS.register("qsu", BlockQSU::new);
    public static final RegistryObject<Block> TANK = BLOCKS.register("tank", BlockTank::new);

    public static final RegistryObject<Block> CONTROLLER = BLOCKS.register("controller", BlockController::new);

    public static final RegistryObject<BlockEntityType<TileChestDiamond>> CHEST_DIAMOND_TILE =
            TILES_ENTITIES.register("chestdiamond", () -> BlockEntityType.Builder.of(TileChestDiamond::new, ModBlocks.CHEST_DIAMOND.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<TileChestGold>> CHEST_GOLD_TILE =
            TILES_ENTITIES.register("chestgold", () -> BlockEntityType.Builder.of(TileChestGold::new, ModBlocks.CHEST_GOLD.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<TileChestIron>> CHEST_IRON_TILE =
            TILES_ENTITIES.register("chestiron", () -> BlockEntityType.Builder.of(TileChestIron::new, ModBlocks.CHEST_IRON.get()).build(null));
    
    
    public static final RegistryObject<BlockEntityType<TileTrashcan>> TRASH_CAN_TILE =
            TILES_ENTITIES.register("trashcan", () -> BlockEntityType.Builder.of(TileTrashcan::new, ModBlocks.TRASH_CAN.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<TileQsu>> QSU_TILE =
            TILES_ENTITIES.register("qsu", () -> BlockEntityType.Builder.of(TileQsu::new, ModBlocks.QSU.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<TileTank>> TANK_TILE =
            TILES_ENTITIES.register("tank", () -> BlockEntityType.Builder.of(TileTank::new, ModBlocks.TANK.get()).build(null));

    public static final RegistryObject<BlockEntityType<TileController>> CONTROLLER_TILE =
            TILES_ENTITIES.register("controller", () -> BlockEntityType.Builder.of(TileController::new, ModBlocks.CONTROLLER.get()).build(null));
}

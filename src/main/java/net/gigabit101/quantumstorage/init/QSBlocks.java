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
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class QSBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, QuantumStorage.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, QuantumStorage.MOD_ID);
    
    public static final RegistryObject<Block> CHEST_DIAMOND = BLOCKS.register("chestdiamond", BlockChestDiamond::new);
    public static final RegistryObject<Block> CHEST_GOLD = BLOCKS.register("chestgold", BlockChestGold::new);
    public static final RegistryObject<Block> CHEST_IRON = BLOCKS.register("chestiron", BlockChestIron::new);
    
    public static final RegistryObject<Block> TRASH_CAN = BLOCKS.register("trashcan", BlockTrashcan::new);
    public static final RegistryObject<Block> QSU = BLOCKS.register("qsu", BlockQSU::new);
    public static final RegistryObject<Block> TANK = BLOCKS.register("tank", BlockTank::new);

    public static final RegistryObject<Block> CONTROLLER = BLOCKS.register("controller", BlockController::new);

    public static final RegistryObject<TileEntityType<TileChestDiamond>> CHEST_DIAMOND_TILE =
            TILES_ENTITIES.register("chestdiamond", () -> TileEntityType.Builder.create(TileChestDiamond::new, QSBlocks.CHEST_DIAMOND.get()).build(null));
    
    public static final RegistryObject<TileEntityType<TileChestGold>> CHEST_GOLD_TILE =
            TILES_ENTITIES.register("chestgold", () -> TileEntityType.Builder.create(TileChestGold::new, QSBlocks.CHEST_GOLD.get()).build(null));
    
    public static final RegistryObject<TileEntityType<TileChestIron>> CHEST_IRON_TILE =
            TILES_ENTITIES.register("chestiron", () -> TileEntityType.Builder.create(TileChestIron::new, QSBlocks.CHEST_IRON.get()).build(null));
    
    
    public static final RegistryObject<TileEntityType<TileTrashcan>> TRASH_CAN_TILE =
            TILES_ENTITIES.register("trashcan", () -> TileEntityType.Builder.create(TileTrashcan::new, QSBlocks.TRASH_CAN.get()).build(null));
    
    public static final RegistryObject<TileEntityType<TileQsu>> QSU_TILE =
            TILES_ENTITIES.register("qsu", () -> TileEntityType.Builder.create(TileQsu::new, QSBlocks.QSU.get()).build(null));
    
    public static final RegistryObject<TileEntityType<TileTank>> TANK_TILE =
            TILES_ENTITIES.register("tank", () -> TileEntityType.Builder.create(TileTank::new, QSBlocks.TANK.get()).build(null));

    public static final RegistryObject<TileEntityType<TileController>> CONTROLLER_TILE =
            TILES_ENTITIES.register("controller", () -> TileEntityType.Builder.create(TileController::new, QSBlocks.CONTROLLER.get()).build(null));

}

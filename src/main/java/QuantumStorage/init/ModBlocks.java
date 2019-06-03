package QuantumStorage.init;

import QuantumStorage.QuantumStorage;
import QuantumStorage.blocks.AdvancedBlock;
import QuantumStorage.multiblock.*;
import QuantumStorage.tiles.*;
import QuantumStorage.tiles.chests.TileChestDiamond;
import QuantumStorage.tiles.chests.TileChestGold;
import QuantumStorage.tiles.chests.TileChestIron;
import QuantumStorage.tiles.chests.TileChestQuantum;
import QuantumStorage.tiles.trashcans.TileTrashcan;
import QuantumStorage.tiles.trashcans.TileTrashcanFluid;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import reborncore.RebornRegistry;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
public class ModBlocks
{
    public static Block DSU;
    public static Block TANK;
    public static Block CRATER;
    public static Block CHEST_IRON;
    public static Block CHEST_GOLD;
    public static Block CHEST_DIAMOND;
    public static Block CHEST_QUANIUM;
    public static Block QUANTUM_CRAFTER;
    public static Block CRATE;
    public static Block TRASH_CAN;
    public static Block TRASH_CAN_FLUID;
    public static Block SAFE;
    
    public static Block MULTIBLOCK_STORAGE;
    
    public static Block CONTROLLER;
    
    public static void init()
    {
        TANK = new AdvancedBlock(new TileQuantumTank()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_tank");
        registerAdvanced(TANK, new TileQuantumTank());
        
        CRATER = new AdvancedBlock(new TileCrater()).setUnlocalizedName(QuantumStorage.MOD_ID + ".crater");
        registerAdvanced(CRATER, new TileCrater());
        
        CHEST_IRON = new AdvancedBlock(new TileChestIron()).setUnlocalizedName(QuantumStorage.MOD_ID + ".chest_iron");
        registerAdvanced(CHEST_IRON, new TileChestIron());
        
        CHEST_GOLD = new AdvancedBlock(new TileChestGold()).setUnlocalizedName(QuantumStorage.MOD_ID + ".chest_gold");
        registerAdvanced(CHEST_GOLD, new TileChestGold());
        
        CHEST_DIAMOND = new AdvancedBlock(new TileChestDiamond()).setUnlocalizedName(QuantumStorage.MOD_ID + ".chest_diamond");
        registerAdvanced(CHEST_DIAMOND, new TileChestDiamond());
        
        DSU = new AdvancedBlock(new TileQuantumStorageUnit()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_storage_unit");
        registerAdvanced(DSU, new TileQuantumStorageUnit());
        
        QUANTUM_CRAFTER = new AdvancedBlock(new TileQuantumCrafter()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantumcrafter");
        registerAdvanced(QUANTUM_CRAFTER, new TileQuantumCrafter());
        
        TRASH_CAN = new AdvancedBlock(new TileTrashcan()).setUnlocalizedName(QuantumStorage.MOD_ID + ".trashcan");
        registerAdvanced(TRASH_CAN, new TileTrashcan());
        
        TRASH_CAN_FLUID = new AdvancedBlock(new TileTrashcanFluid()).setUnlocalizedName(QuantumStorage.MOD_ID + ".trashcanfluid");
        registerAdvanced(TRASH_CAN_FLUID, new TileTrashcanFluid());
        
        CHEST_QUANIUM = new AdvancedBlock(new TileChestQuantum()).setUnlocalizedName(QuantumStorage.MOD_ID + ".chest_quantum");
        registerAdvanced(CHEST_QUANIUM, new TileChestQuantum());
        
//        CONTROLLER = new AdvancedBlock(new TileController()).setUnlocalizedName(QuantumStorage.MOD_ID + "controller");
//        registerAdvanced(CONTROLLER, new TileController());
        
        registerMultiBlocks();
    }
    
    public static void registerMultiBlocks()
    {
        MULTIBLOCK_STORAGE = new BlockMultiStorage();
        RebornRegistry.registerBlock(MULTIBLOCK_STORAGE, ItemMultiBlockStorage.class, "multistorage");
        GameRegistry.registerTileEntity(TileMultiStorage.class, QuantumStorage.MOD_ID + "tilemultistorage");
        GameRegistry.registerTileEntity(TileIoPort.class, QuantumStorage.MOD_ID + "tileioport");
    }
    
    static void registerAdvanced(Block block, AdvancedTileEntity advancedTileEntity)
    {
        RebornRegistry.registerBlock(block, advancedTileEntity.getName());
        GameRegistry.registerTileEntity(advancedTileEntity.getClass(), advancedTileEntity.getName());
        advancedTileEntity.addRecipe();
    }
}

package QuantumStorage.init;

import QuantumStorage.QuantumStorage;
import QuantumStorage.blocks.AdvancedBlock;
import QuantumStorage.tiles.*;
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
    public static Block BARREL;
    public static Block CRATER;
    public static Block CHEST_IRON;
    public static Block CHEST_GOLD;
    public static Block CHEST_DIAMOND;
    public static Block QUANTUM_CRAFTER;

    public static Block CONTROLLER;
    public static Block CABLE;

    public static void init()
    {
        TANK = new AdvancedBlock(new TileQuantumTank()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_tank");
        registerAdvanced(TANK, new TileQuantumTank());

        BARREL = new AdvancedBlock(new TileQuantumBarrel()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_barrel");
        registerAdvanced(BARREL, new TileQuantumBarrel());

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

//        CONTROLLER = new AdvancedBlock(new TileController()).setUnlocalizedName(QuantumStorage.MOD_ID + ".controller");
//        registerAdvanced(CONTROLLER, new TileController());
//
//        CABLE = new BlockCable();
//        RebornRegistry.registerBlock(CABLE, "cable");
    }

    static void registerAdvanced(Block block, AdvancedTileEntity advancedTileEntity)
    {
        RebornRegistry.registerBlock(block, advancedTileEntity.getName());
        GameRegistry.registerTileEntity(advancedTileEntity.getClass(), advancedTileEntity.getName());
        advancedTileEntity.addRecipe();
    }
}

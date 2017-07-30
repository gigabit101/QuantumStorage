package QuantumStorage.init;

import QuantumStorage.QuantumStorage;
import QuantumStorage.blocks.AdvancedBlock;
import QuantumStorage.multiblock.BlockCrate;
import QuantumStorage.multiblock.TileCrate;
import QuantumStorage.tiles.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
    public static Block CRATE;
    public static Block TRASH_CAN;
    public static Block TRASH_CAN_FLUID;
    public static Block SAFE;

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

        CRATE = new BlockCrate();
        RebornRegistry.registerBlock(CRATE, "multiblockcrate");
        GameRegistry.registerTileEntity(TileCrate.class, QuantumStorage.MOD_ID + "multiblockcrate");

        TRASH_CAN = new AdvancedBlock(new TileTrashcan()).setUnlocalizedName(QuantumStorage.MOD_ID + ".trashcan");
        registerAdvanced(TRASH_CAN, new TileTrashcan());

        TRASH_CAN_FLUID = new AdvancedBlock(new TileTrashcanFluid()).setUnlocalizedName(QuantumStorage.MOD_ID + ".trashcanfluid");
        registerAdvanced(TRASH_CAN_FLUID, new TileTrashcanFluid());

        SAFE = new AdvancedBlock(new TileSafe()).setUnlocalizedName(QuantumStorage.MOD_ID + ".safe");
        registerAdvanced(SAFE, new TileSafe());

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

package QuantumStorage.init;

import QuantumStorage.QuantumStorage;
import QuantumStorage.blocks.AdvancedBlock;
import QuantumStorage.items.ItemBlockQStorage;
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

    public static void init()
    {
        DSU = new AdvancedBlock(new TileQuantumStorageUnit()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_storage_unit");
        registerAdvanced(DSU, new TileQuantumStorageUnit());

        TANK = new AdvancedBlock(new TileQuantumTank()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_tank");
        registerAdvanced(TANK, new TileQuantumTank());

        BARREL = new AdvancedBlock(new TileQuantumBarrel()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_barrel");
        registerAdvanced(BARREL, new TileQuantumBarrel());

        CRATER = new AdvancedBlock(new TileCrater()).setUnlocalizedName(QuantumStorage.MOD_ID + ".crater");
        registerAdvanced(CRATER, new TileCrater());
    }

    static void registerAdvanced(Block block, AdvancedTileEntity advancedTileEntity)
    {
        RebornRegistry.registerBlock(block, ItemBlockQStorage.class, advancedTileEntity.getName());
        GameRegistry.registerTileEntity(advancedTileEntity.getClass(), advancedTileEntity.getName());
        advancedTileEntity.addRecipe();
    }
}

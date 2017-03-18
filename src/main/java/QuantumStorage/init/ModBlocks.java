package QuantumStorage.init;

import QuantumStorage.QuantumStorage;
import QuantumStorage.items.ItemBlockQStorage;
import QuantumStorage.rewrite.AdvancedBlock;
import QuantumStorage.rewrite.AdvancedTileEntity;
import QuantumStorage.rewrite.tiles.TileQuantumBarrel;
import QuantumStorage.rewrite.tiles.TileQuantumStorageUnit;
import QuantumStorage.rewrite.tiles.TileQuantumTank;
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

    public static void init()
    {
        DSU = new AdvancedBlock(new TileQuantumStorageUnit()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_storage_unit");
        registerAdvanced(DSU, new TileQuantumStorageUnit());

        TANK = new AdvancedBlock(new TileQuantumTank()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_tank");
        registerAdvanced(TANK, new TileQuantumTank());

        BARREL = new AdvancedBlock(new TileQuantumBarrel()).setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_barrel");
        registerAdvanced(BARREL, new TileQuantumBarrel());
    }

    static void registerAdvanced(Block block, AdvancedTileEntity advancedTileEntity)
    {
        RebornRegistry.registerBlock(block, ItemBlockQStorage.class, advancedTileEntity.getName());
        GameRegistry.registerTileEntity(advancedTileEntity.getClass(), advancedTileEntity.getName());
        advancedTileEntity.addRecipe();
    }
}

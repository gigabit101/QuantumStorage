package QuantumStorage.init;

import QuantumStorage.block.BlockQuantumDsu;
import QuantumStorage.block.BlockQuantumTank;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsu;
import QuantumStorage.items.itemblocks.ItemBlockQuantumTank;
import QuantumStorage.tile.TileQuantumDsu;
import QuantumStorage.tile.TileQuantumTank;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks 
{
	public static Block QuantumDsu;
	
	public static Block QuantumTank;

	public static void init() 
	{
		//MK1
		QuantumDsu = new BlockQuantumDsu(Material.iron);
		GameRegistry.registerBlock(QuantumDsu, ItemBlockQuantumDsu.class, "quantumdsu");
		GameRegistry.registerTileEntity(TileQuantumDsu.class, "tilequantumdsu");

		//Tanks
		QuantumTank = new BlockQuantumTank(Material.iron);
		GameRegistry.registerBlock(QuantumTank, ItemBlockQuantumTank.class, "quantumtank");
		GameRegistry.registerTileEntity(TileQuantumTank.class, "tilequantumtank");
	}
}

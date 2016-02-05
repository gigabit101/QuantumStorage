package QuantumStorage.init;

import QuantumStorage.block.qsu.BlockQuantumDsuMk1;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsuMk1;
import QuantumStorage.tile.qsu.TileQuantumDsuMk1;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks 
{
	public static Block QuantumDsuMk1;
	public static Block QuantumDsuMk2;
	public static Block QuantumDsuMk3;
	public static Block QuantumDsuMk4;
	
	public static Block QuantumTankMk1;
	public static Block QuantumTankMk2;
	public static Block QuantumTankMk3;
	public static Block QuantumTankMk4;
	public static Block QuantumChest;

	public static void init() 
	{
		//MK1
		QuantumDsuMk1 = new BlockQuantumDsuMk1(Material.iron);
		GameRegistry.registerBlock(QuantumDsuMk1, ItemBlockQuantumDsuMk1.class, "quantumdsumk1");
		GameRegistry.registerTileEntity(TileQuantumDsuMk1.class, "tilequantumdsumk1");
//		//MK2
//		QuantumDsuMk2 = new BlockQuantumDsuMk2(Material.iron);
//		GameRegistry.registerBlock(QuantumDsuMk2, ItemBlockQuantumDsuMk2.class, "quantumdsumk2");
//		GameRegistry.registerTileEntity(TileQuantumDsuMk2.class, "tilequantumdsumk2");
//		//MK3
//		QuantumDsuMk3 = new BlockQuantumDsuMk3(Material.iron);
//		GameRegistry.registerBlock(QuantumDsuMk3, ItemBlockQuantumDsuMk3.class, "quantumdsumk3");
//		GameRegistry.registerTileEntity(TileQuantumDsuMk3.class, "tilequantumdsumk3");
//		//Mk4
//		QuantumDsuMk4 = new BlockQuantumDsuMk4(Material.iron);
//		GameRegistry.registerBlock(QuantumDsuMk4, ItemBlockQuantumDsuMk4.class, "quantumdsu");
//		GameRegistry.registerTileEntity(TileQuantumDsuMk4.class, "tilequantumdsu");
//		
//		//Tanks
//		QuantumTankMk1 = new BlockQuantumTankMk1(Material.iron);
//		GameRegistry.registerBlock(QuantumTankMk1, ItemBlockQuantumTankMk1.class, "quantumtankmk1");
//		GameRegistry.registerTileEntity(TileQuantumTankMk1.class, "tilequantumtankmk1");
//		
//		QuantumTankMk2 = new BlockQuantumTankMk2(Material.iron);
//		GameRegistry.registerBlock(QuantumTankMk2, ItemBlockQuantumTankMk2.class, "quantumtankmk2");
//		GameRegistry.registerTileEntity(TileQuantumTankMk2.class, "tilequantumtankmk2");
//		
//		QuantumTankMk3 = new BlockQuantumTankMk3(Material.iron);
//		GameRegistry.registerBlock(QuantumTankMk3, ItemBlockQuantumTankMk3.class, "quantumtankmk3");
//		GameRegistry.registerTileEntity(TileQuantumTankMk3.class, "tilequantumtankmk3");
//		
//		QuantumTankMk4 = new BlockQuantumTankMk4(Material.iron);
//		GameRegistry.registerBlock(QuantumTankMk4, ItemBlockQuantumTankMk4.class, "quantumtankmk4");
//		GameRegistry.registerTileEntity(TileQuantumTankMk4.class, "tilequantumtankmk4");
		//Chest
//		QuantumChest = new BlockQuantumChest(Material.iron);
//		GameRegistry.registerBlock(QuantumChest, ItemBlockQuantumChest.class ,"quantumchest");
//		GameRegistry.registerTileEntity(TileQuantumChest.class, "tilequantumchest");
	}
}

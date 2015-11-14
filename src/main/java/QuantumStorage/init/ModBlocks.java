package QuantumStorage.init;

import QuantumStorage.block.qst.BlockQuantumTankMk1;
import QuantumStorage.block.qst.BlockQuantumTankMk2;
import QuantumStorage.block.qst.BlockQuantumTankMk3;
import QuantumStorage.block.qst.BlockQuantumTankMk4;
import QuantumStorage.block.qsu.BlockQuantumDsuMk1;
import QuantumStorage.block.qsu.BlockQuantumDsuMk2;
import QuantumStorage.block.qsu.BlockQuantumDsuMk3;
import QuantumStorage.block.qsu.BlockQuantumDsuMk4;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsuMk1;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsuMk2;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsuMk3;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsuMk4;
import QuantumStorage.items.itemblocks.ItemBlockQuantumTankMk1;
import QuantumStorage.items.itemblocks.ItemBlockQuantumTankMk2;
import QuantumStorage.items.itemblocks.ItemBlockQuantumTankMk3;
import QuantumStorage.items.itemblocks.ItemBlockQuantumTankMk4;
import QuantumStorage.tile.qst.TileQuantumTankMk1;
import QuantumStorage.tile.qst.TileQuantumTankMk2;
import QuantumStorage.tile.qst.TileQuantumTankMk3;
import QuantumStorage.tile.qst.TileQuantumTankMk4;
import QuantumStorage.tile.qsu.TileQuantumDsuMk1;
import QuantumStorage.tile.qsu.TileQuantumDsuMk2;
import QuantumStorage.tile.qsu.TileQuantumDsuMk3;
import QuantumStorage.tile.qsu.TileQuantumDsuMk4;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

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
		//MK2
		QuantumDsuMk2 = new BlockQuantumDsuMk2(Material.iron);
		GameRegistry.registerBlock(QuantumDsuMk2, ItemBlockQuantumDsuMk2.class, "quantumdsumk2");
		GameRegistry.registerTileEntity(TileQuantumDsuMk2.class, "tilequantumdsumk2");
		//MK3
		QuantumDsuMk3 = new BlockQuantumDsuMk3(Material.iron);
		GameRegistry.registerBlock(QuantumDsuMk3, ItemBlockQuantumDsuMk3.class, "quantumdsumk3");
		GameRegistry.registerTileEntity(TileQuantumDsuMk3.class, "tilequantumdsumk3");
		//Mk4
		QuantumDsuMk4 = new BlockQuantumDsuMk4(Material.iron);
		GameRegistry.registerBlock(QuantumDsuMk4, ItemBlockQuantumDsuMk4.class, "quantumdsu");
		GameRegistry.registerTileEntity(TileQuantumDsuMk4.class, "tilequantumdsu");
		
		//Tanks
		QuantumTankMk1 = new BlockQuantumTankMk1(Material.iron);
		GameRegistry.registerBlock(QuantumTankMk1, ItemBlockQuantumTankMk1.class, "quantumtankmk1");
		GameRegistry.registerTileEntity(TileQuantumTankMk1.class, "tilequantumtankmk1");
		
		QuantumTankMk2 = new BlockQuantumTankMk2(Material.iron);
		GameRegistry.registerBlock(QuantumTankMk2, ItemBlockQuantumTankMk2.class, "quantumtankmk2");
		GameRegistry.registerTileEntity(TileQuantumTankMk2.class, "tilequantumtankmk2");
		
		QuantumTankMk3 = new BlockQuantumTankMk3(Material.iron);
		GameRegistry.registerBlock(QuantumTankMk3, ItemBlockQuantumTankMk3.class, "quantumtankmk3");
		GameRegistry.registerTileEntity(TileQuantumTankMk3.class, "tilequantumtankmk3");
		
		QuantumTankMk4 = new BlockQuantumTankMk4(Material.iron);
		GameRegistry.registerBlock(QuantumTankMk4, ItemBlockQuantumTankMk4.class, "quantumtankmk4");
		GameRegistry.registerTileEntity(TileQuantumTankMk4.class, "tilequantumtankmk4");
		//Chest
//		QuantumChest = new BlockQuantumChest(Material.iron);
//		GameRegistry.registerBlock(QuantumChest, ItemBlockQuantumChest.class ,"quantumchest");
//		GameRegistry.registerTileEntity(TileQuantumChest.class, "tilequantumchest");
	}
}

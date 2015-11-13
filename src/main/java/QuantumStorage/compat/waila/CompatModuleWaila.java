package QuantumStorage.compat.waila;

import QuantumStorage.block.tile.TileQuantumDsuMk1;
import QuantumStorage.block.tile.TileQuantumDsuMk2;
import QuantumStorage.block.tile.TileQuantumDsuMk3;
import QuantumStorage.block.tile.TileQuantumDsuMk4;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;

public class CompatModuleWaila {
	public void init(FMLInitializationEvent event) 
	{
		FMLInterModComms.sendMessage("Waila", "register", getClass().getName() + ".callbackRegister");
	}

	public static void callbackRegister(IWailaRegistrar registrar) 
	{
		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumDsuMk1.class);
		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumDsuMk2.class);
		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumDsuMk3.class);
		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumDsuMk4.class);
	}
}

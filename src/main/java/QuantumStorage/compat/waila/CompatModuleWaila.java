package QuantumStorage.compat.waila;

import QuantumStorage.block.tile.TileQuantumDsuMk4;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;

public class CompatModuleWaila {
	public void init(FMLInitializationEvent event) {
		FMLInterModComms.sendMessage("Waila", "register", getClass().getName() + ".callbackRegister");
	}

	public static void callbackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumDsuMk4.class);
	}
}

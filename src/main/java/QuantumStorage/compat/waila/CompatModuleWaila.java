package QuantumStorage.compat.waila;

import QuantumStorage.tile.TileQuantumDsu;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CompatModuleWaila {
	public void init(FMLInitializationEvent event) 
	{
		FMLInterModComms.sendMessage("Waila", "register", getClass().getName() + ".callbackRegister");
	}

//	public static void callbackRegister(IWailaRegistrar registrar) 
//	{
//		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumDsuMk1.class);
//	}
}

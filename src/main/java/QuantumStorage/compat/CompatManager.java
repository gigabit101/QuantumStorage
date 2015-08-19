package QuantumStorage.compat;

import org.lwjgl.Sys;

import QuantumStorage.compat.waila.CompatModuleWaila;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class CompatManager {
	
	public static void init(FMLInitializationEvent event)
	{
		if(Loader.isModLoaded("Waila"))
		{
			new CompatModuleWaila().init(event);
		}
		if(Loader.isModLoaded("Applied Energistics 2"));
		{
			System.out.print("AE2 Detected");
		}	
	}
}

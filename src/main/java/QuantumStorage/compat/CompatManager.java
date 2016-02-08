package QuantumStorage.compat;

import QuantumStorage.compat.waila.CompatModuleWaila;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class CompatManager {
	
	public static void init(FMLInitializationEvent event)
	{
		if(Loader.isModLoaded("Waila"))
		{
			new CompatModuleWaila().init(event);
		}
	}
}

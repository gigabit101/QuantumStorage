package QuantumStorage.compat.waila;

import QuantumStorage.tile.prefab.TileQuantumStorage;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CompatModuleWaila 
{
	public void init(FMLInitializationEvent event) 
	{
		FMLInterModComms.sendMessage("Waila", "register", getClass().getName() + ".callbackRegister");
	}

	public static void callbackRegister(IWailaRegistrar registar)
	{
		registar.registerBodyProvider(new WailaProviderQuantumStorage(), TileQuantumStorage.class);
	}
}

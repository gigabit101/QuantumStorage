package QuantumStorage.compat.waila;

import QuantumStorage.tile.qst.TileQuantumTankMk1;
import QuantumStorage.tile.qst.TileQuantumTankMk2;
import QuantumStorage.tile.qst.TileQuantumTankMk3;
import QuantumStorage.tile.qst.TileQuantumTankMk4;
import QuantumStorage.tile.qsu.TileQuantumDsuMk1;
import QuantumStorage.tile.qsu.TileQuantumDsuMk2;
import QuantumStorage.tile.qsu.TileQuantumDsuMk3;
import QuantumStorage.tile.qsu.TileQuantumDsuMk4;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;

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
		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumTankMk1.class);
		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumTankMk2.class);
		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumTankMk3.class);
		registrar.registerBodyProvider(new WailaProviderMachines(), TileQuantumTankMk4.class);
	}
}

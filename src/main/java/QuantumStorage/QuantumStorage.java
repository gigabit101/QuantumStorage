package QuantumStorage;

import java.io.File;

import QuantumStorage.client.GuiHandler;
import QuantumStorage.compat.CompatManager;
import QuantumStorage.compat.waila.CompatModuleWaila;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.init.ModItems;
import QuantumStorage.init.ModRecipes;
import QuantumStorage.lib.ModInfo;
import QuantumStorage.packet.PacketHandler;
import QuantumStorage.tile.qsu.TileQuantumDsuMk4;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.common.ForgeChunkManager;

@Mod(name = ModInfo.MOD_NAME, modid = ModInfo.MOD_ID, version = ModInfo.MOD_VERSION)
public class QuantumStorage 
{
	public static ConfigQuantumStorage config;
	
	@Mod.Instance
	public static QuantumStorage INSTANCE;
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent event)
	{
		String path = event.getSuggestedConfigurationFile().getAbsolutePath().replace(ModInfo.MOD_ID, "QuantumStorage");
		config = ConfigQuantumStorage.initialize(new File(path));
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) 
	{	
		ModItems.init();
		ModBlocks.init();
		ModRecipes.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
		PacketHandler.setChannels(NetworkRegistry.INSTANCE.newChannel(ModInfo.MOD_ID + "_packets", new PacketHandler()));
		CompatManager.init(event);		
	}
}
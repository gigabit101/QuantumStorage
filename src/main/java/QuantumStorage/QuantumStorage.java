package QuantumStorage;

import QuantumStorage.client.GuiHandler;
import QuantumStorage.compat.CompatManager;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.init.ModItems;
import QuantumStorage.init.ModRecipes;
import QuantumStorage.lib.ModInfo;
import QuantumStorage.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod(name = ModInfo.MOD_NAME, modid = ModInfo.MOD_ID, version = ModInfo.MOD_VERSION, dependencies = ModInfo.MOD_DEPENDENCUIES)
public class QuantumStorage 
{
	public static ConfigQuantumStorage config;
	
	@Mod.Instance
	public static QuantumStorage INSTANCE;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY_LOC, serverSide = ModInfo.COMMON_PROXY_LOC)
    public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		String path = event.getSuggestedConfigurationFile().getAbsolutePath().replace(ModInfo.MOD_ID, "QuantumStorage");
		config = ConfigQuantumStorage.initialize(new File(path));
        ModBlocks.init();
		ModItems.init();
        ModRecipes.init();
        proxy.registerRenders();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) 
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
		CompatManager.init(event);	
	}
}
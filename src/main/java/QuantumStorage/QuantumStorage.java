package QuantumStorage;

import QuantumStorage.client.GuiHandler;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.lib.ModInfo;
import QuantumStorage.packet.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(name = ModInfo.MOD_NAME, modid = ModInfo.MOD_ID, version = ModInfo.MOD_VERSION)
public class QuantumStorage {
	@Mod.Instance
	public static QuantumStorage INSTANCE;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ModBlocks.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
		PacketHandler
				.setChannels(NetworkRegistry.INSTANCE.newChannel(ModInfo.MOD_ID + "_packets", new PacketHandler()));
	}
}
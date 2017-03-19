package QuantumStorage;

import QuantumStorage.client.RenderBarrel;
import QuantumStorage.client.RenderDsu;
import QuantumStorage.compat.CompatHandler;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.init.ModItems;
import QuantumStorage.init.ModRecipes;
import QuantumStorage.init.ModelHandler;
import QuantumStorage.proxy.CommonProxy;
import QuantumStorage.rewrite.tiles.TileQuantumBarrel;
import QuantumStorage.rewrite.tiles.TileQuantumStorageUnit;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
@Mod(name = QuantumStorage.MOD_NAME, modid = QuantumStorage.MOD_ID, version = QuantumStorage.MOD_VERSION, dependencies = QuantumStorage.MOD_DEPENDENCUIES)
public class QuantumStorage
{
    public static final String MOD_ID = "quantumstorage";
    public static final String MOD_NAME  = "QuantumStorage";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCUIES ="required-after:reborncore";

    @Mod.Instance
    public static QuantumStorage INSTANCE;

    @SidedProxy(clientSide = "QuantumStorage.proxy.CommonProxy", serverSide = "QuantumStorage.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        ModItems.init();
        ModRecipes.init();
        CompatHandler.init();
        if(event.getSide() == Side.CLIENT)
        {
            ModelHandler.init();
//            ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumStorageUnit.class, new RenderDsu());
//            ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumBarrel.class, new RenderBarrel());
        }
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
    }
}

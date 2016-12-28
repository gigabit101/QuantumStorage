package QuantumStorage.proxy;

import QuantumStorage.client.RenderQSU;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.init.ModelHelper;
import QuantumStorage.tile.TileQuantumDsu;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by Gigabit101 on 09/09/2016.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
        ModelHelper.init();
        if(!ConfigQuantumStorage.disableRender)
        {
            ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumDsu.class, new RenderQSU());
        }
    }
}

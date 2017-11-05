package QuantumStorage.proxy;

import QuantumStorage.client.render.RenderBarrel;
import QuantumStorage.client.render.RenderDsu;
import QuantumStorage.client.render.TankRender;
import QuantumStorage.tiles.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumStorageUnit.class, new RenderDsu());
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumBarrel.class, new RenderBarrel());
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumTank.class, new TankRender());
    }
}

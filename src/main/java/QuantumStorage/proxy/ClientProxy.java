package QuantumStorage.proxy;

import QuantumStorage.client.RenderBarrel;
import QuantumStorage.client.RenderChest;
import QuantumStorage.client.RenderDsu;
import QuantumStorage.init.ModelHandler;
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
        ModelHandler.init();
        ClientRegistry.bindTileEntitySpecialRenderer(TileChestIron.class, new RenderChest());
        ClientRegistry.bindTileEntitySpecialRenderer(TileChestGold.class, new RenderChest());
        ClientRegistry.bindTileEntitySpecialRenderer(TileChestDiamond.class, new RenderChest());

        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumStorageUnit.class, new RenderDsu());
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumBarrel.class, new RenderBarrel());
    }
}

package QuantumStorage.proxy;

import QuantumStorage.QuantumStorage;
import QuantumStorage.client.render.RenderBarrel;
import QuantumStorage.client.render.RenderChest;
import QuantumStorage.client.render.RenderDsu;
import QuantumStorage.init.ModelHandler;
import QuantumStorage.tiles.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
//        ModelHandler.init();
        ClientRegistry.bindTileEntitySpecialRenderer(TileChestIron.class, new RenderChest(new ResourceLocation(QuantumStorage.MOD_ID + ":textures/blocks/chest_iron.png")));
        ClientRegistry.bindTileEntitySpecialRenderer(TileChestGold.class, new RenderChest(new ResourceLocation(QuantumStorage.MOD_ID + ":textures/blocks/chest_gold.png")));
        ClientRegistry.bindTileEntitySpecialRenderer(TileChestDiamond.class, new RenderChest(new ResourceLocation(QuantumStorage.MOD_ID + ":textures/blocks/chest_diamond.png")));

        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumStorageUnit.class, new RenderDsu());
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumBarrel.class, new RenderBarrel());
    }
}

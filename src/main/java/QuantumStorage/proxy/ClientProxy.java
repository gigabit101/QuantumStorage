package QuantumStorage.proxy;

import QuantumStorage.api.IColorable;
import QuantumStorage.api.IQuantumBagProvider;
import QuantumStorage.api.QuantumStorageAPI;
import QuantumStorage.client.keybinding.KeyBindings;
import QuantumStorage.client.keybinding.KeyInputEventHandler;
import QuantumStorage.client.render.RenderDsu;
import QuantumStorage.client.render.TankRender;
import QuantumStorage.init.ModItems;
import QuantumStorage.tiles.TileQuantumStorageUnit;
import QuantumStorage.tiles.TileQuantumTank;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumStorageUnit.class, new RenderDsu());
        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumTank.class, new TankRender());
    }
    
    @Override
    public IQuantumBagProvider getClientBagProps()
    {
        return FMLClientHandler.instance().getClientPlayerEntity().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null);
    }
    
    @Override
    public void registerColors()
    {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) ->
        {
            Item item = stack.getItem();
            if (item instanceof IColorable)
            {
                return ((IColorable) item).getColorFromItemStack(stack, tintIndex);
            } else
            {
                return 0xFFFFFF;
            }
        }, ModItems.BAG);
    }
    
    @Override
    public void registerKeybindings()
    {
        ClientRegistry.registerKeyBinding(KeyBindings.openBag);
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
    }
}

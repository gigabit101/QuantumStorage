package QuantumStorage.proxy;

import QuantumStorage.QuantumStorage;
import QuantumStorage.api.IColorable;
import QuantumStorage.client.render.RenderDsu;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.guis.*;
import QuantumStorage.tiles.TileQsu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
//        if(ConfigQuantumStorage.INSTANCE.specialRender.get())
//        {
            ClientRegistry.bindTileEntitySpecialRenderer(TileQsu.class, new RenderDsu());
//        }
//        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumTank.class, new TankRender());
    }
    
//    @Override
//    public IQuantumBagProvider getClientBagProps()
//    {
//        return FMLClientHandler.instance().getClientPlayerEntity().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null);
//    }
    
    @Override
    public void registerColors()
    {
        Minecraft.getInstance().getItemColors().register(new IItemColor() {
            @Override
            public int getColor(ItemStack stack, int tintIndex) {
                return ((IColorable) stack.getItem()).getColor(stack, tintIndex);
            }
        }, QuantumStorage.BAGS.values().toArray(new Item[0]));
    }
    
    @Override
    public void registerKeybindings()
    {
//        ClientRegistry.registerKeyBinding(KeyBindings.openBag);
//        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
    }

    @Override
    public void registerGuis()
    {
        ScreenManager.registerFactory(QuantumStorage.containerChestDiamondContainerType, GuiChestDiamond::new);
        ScreenManager.registerFactory(QuantumStorage.containerChestGoldContainerType, GuiChestGold::new);
        ScreenManager.registerFactory(QuantumStorage.containerChestIronContainerType, GuiChestIron::new);

        ScreenManager.registerFactory(QuantumStorage.containerTrashcanContainerType, GuiTrashcan::new);
        ScreenManager.registerFactory(QuantumStorage.containerQsuContainerType, GuiQSU::new);
        ScreenManager.registerFactory(QuantumStorage.containerTankContainerType, GuiTank::new);
    }
}

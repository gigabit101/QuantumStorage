package QuantumStorage.proxy;

import QuantumStorage.QuantumStorage;
import QuantumStorage.guis.GuiChestDiamond;
import QuantumStorage.guis.GuiChestGold;
import QuantumStorage.guis.GuiChestIron;
import QuantumStorage.guis.GuiTrashcan;
import net.minecraft.client.gui.ScreenManager;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
//        ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumStorageUnit.class, new RenderDsu());
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
//        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) ->
//        {
//            Item item = stack.getItem();
//            if (item instanceof IColorable)
//            {
//                return ((IColorable) item).getColorFromItemStack(stack, tintIndex);
//            } else
//            {
//                return 0xFFFFFF;
//            }
//        }, ModItems.BAG);
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
    }
}

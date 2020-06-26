package net.gigabit101.quantumstorage.proxy;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.api.IColorable;
import net.gigabit101.quantumstorage.client.render.RenderDsu;
import net.gigabit101.quantumstorage.client.render.TankRender;
import net.gigabit101.quantumstorage.guis.*;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
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
        RenderTypeLookup.setRenderLayer(QSBlocks.QSU.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(QSBlocks.TANK.get(), RenderType.getCutoutMipped());
    
        RenderTypeLookup.setRenderLayer(QSBlocks.TRASH_CAN.get(), RenderType.getCutout());
    
        ClientRegistry.bindTileEntityRenderer(QSBlocks.QSU_TILE.get(), RenderDsu::new);
        ClientRegistry.bindTileEntityRenderer(QSBlocks.TANK_TILE.get(), TankRender::new);
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

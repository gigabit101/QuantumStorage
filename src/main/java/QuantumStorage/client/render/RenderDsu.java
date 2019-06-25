//package QuantumStorage.client.render;
//
//import QuantumStorage.tiles.TileQuantumStorageUnit;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemStack;
//import reborncore.client.RenderUtil;
//
///**
// * Created by Gigabit101 on 07/03/2017.
// */
//public class RenderDsu extends TileEntitySpecialRenderer<TileQuantumStorageUnit>
//{
//    @Override
//    public void render(TileQuantumStorageUnit te, double x, double y, double z, float p_192841_8_, int p_192841_9_, float p_192841_10_)
//    {
//        if (te.getFacing() != null)
//        {
//            if (!te.inv.getStackInSlot(2).isEmpty())
//            {
//                GlStateManager.pushMatrix();
//
//                ItemStack stack = te.inv.getStackInSlot(2);
//                if (stack != null)
//                {
//                    double spin = Minecraft.getSystemTime() / 1000D;
//
//                    if (stack.getItem() instanceof ItemBlock)
//                    {
//                        GlStateManager.translate(x + .5, y + 0.65, z + .5);
//                    } else
//                    {
//                        GlStateManager.translate(x + .5, y + 0.65, z + .5);
//                    }
//                    GlStateManager.rotate((float) (((spin * 40D) % 360)), 0, 1, 0);
//
//                    RenderUtil.renderItemInWorld(stack);
//                }
//                GlStateManager.popMatrix();
//            }
//        }
//    }
//}

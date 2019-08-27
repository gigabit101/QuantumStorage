package QuantumStorage.client.render;

import QuantumStorage.tiles.TileQsu;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class RenderDsu extends TileEntityRenderer<TileQsu>
{
    @Override
    public void render(TileQsu te, double x, double y, double z, float p_192841_8_, int p_192841_9_)
    {
        if (!te.inventory.getStackInSlot(2).isEmpty())
        {
            GlStateManager.pushMatrix();

            ItemStack stack = te.inventory.getStackInSlot(2);
            if (!stack.isEmpty())
            {
                double spin = System.currentTimeMillis() / 1000D;

                if (stack.getItem() instanceof BlockItem)
                {
                    GlStateManager.translated(x + .5, y + 0.65, z + .5);
                }
                else
                {
                    GlStateManager.translated(x + .5, y + 0.65, z + .5);
                }
                GlStateManager.rotatef((float) (((spin * 40D) % 360)), 0, 1, 0);

                renderItemInWorld(stack);
            }
            GlStateManager.popMatrix();
        }
    }
    
    private static void renderItemInWorld(ItemStack stack)
    {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.color3f(1.0F, 1.0F, 1.0F);
        if (stack.getItem() instanceof BlockItem)
        {
            GlStateManager.scaled(.6, .6, .6);
        }
        else {
            GlStateManager.scaled(.5, .5, .5);
        }
        
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}

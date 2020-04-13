package net.gigabit101.quantumstorage.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.gigabit101.quantumstorage.config.ConfigQuantumStorage;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class RenderDsu extends TileEntityRenderer<TileQsu>
{
    public RenderDsu(TileEntityRendererDispatcher p_i226006_1_)
    {
        super(p_i226006_1_);
    }
    
    @Override
    public void render(TileQsu te, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1)
    {
        if(ConfigQuantumStorage.SPECIAL_RENDER_QSU.get())
        {
            if (!te.inventory.getStackInSlot(2).isEmpty())
            {
                RenderSystem.pushMatrix();
        
                ItemStack stack = te.inventory.getStackInSlot(2);
                if (!stack.isEmpty())
                {
                    renderItemInWorld(te, stack, matrixStack, iRenderTypeBuffer, i, i1);
                }
                RenderSystem.popMatrix();
            }
        }
    }
    
    private static void renderItemInWorld(TileQsu te, ItemStack stack, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        matrixStackIn.push();
        RenderSystem.disableLighting();
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        matrixStackIn.translate(.5, .7, .5);
        matrixStackIn.scale(.4f, .4f, .4f);
        float rotation = (float) (te.getWorld().getGameTime() % 80);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(360f * rotation / 80f));
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
        RenderSystem.enableLighting();
        matrixStackIn.pop();
    }
}

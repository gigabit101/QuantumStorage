package net.gigabit101.quantumstorage.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.gigabit101.quantumstorage.config.ConfigQuantumStorage;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

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
    public void render(TileQsu te, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int ov)
    {
        if(!ConfigQuantumStorage.SPECIAL_RENDER_QSU.get())
        {
            if (!te.inventory.getStackInSlot(2).isEmpty())
            {
                matrixStack.push();
        
                ItemStack stack = te.inventory.getStackInSlot(2);
                if (!stack.isEmpty())
                {
                    matrixStack.push();
                    matrixStack.translate(0.5D, 0.6D, 0.5D);
                    float rotation = (float) (te.getWorld().getGameTime() % 80);
                    matrixStack.scale(.4f, .4f, .4f);

                    matrixStack.rotate(Vector3f.YP.rotationDegrees(360f * rotation / 80f));

                    Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, light, ov, matrixStack, iRenderTypeBuffer);
                    matrixStack.pop();
                }
                matrixStack.pop();
            }
        }
    }
}

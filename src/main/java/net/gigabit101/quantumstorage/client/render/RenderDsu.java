package net.gigabit101.quantumstorage.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.gigabit101.quantumstorage.config.ConfigQuantumStorage;
import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemStack;


/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class RenderDsu implements BlockEntityRenderer<TileQsu>
{
    public RenderDsu() {}
    
    @Override
    public void render(TileQsu te, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int light, int ov)
    {
        if(!ConfigQuantumStorage.SPECIAL_RENDER_QSU.get())
        {
            if (!te.inventory.getStackInSlot(2).isEmpty())
            {
                matrixStack.pushPose();
        
                ItemStack stack = te.inventory.getStackInSlot(2);
                if (!stack.isEmpty())
                {
                    matrixStack.pushPose();
                    matrixStack.translate(0.5D, 0.6D, 0.5D);
                    float rotation = (float) (te.getLevel().getGameTime() % 80);
                    matrixStack.scale(.4f, .4f, .4f);
                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(360f * rotation / 80f));

                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, ov, matrixStack, iRenderTypeBuffer, 0);
                    matrixStack.popPose();
                }
                matrixStack.popPose();
            }
        }
    }
}

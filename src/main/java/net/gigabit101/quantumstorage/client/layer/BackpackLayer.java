package net.gigabit101.quantumstorage.client.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.gigabit101.quantumstorage.init.QSItems;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class BackpackLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
{
    private static ItemStack itemStack = ItemStack.EMPTY;

    public BackpackLayer(PlayerRenderer playerRenderer)
    {
        super(playerRenderer);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if(shouldRender(entitylivingbaseIn) && !itemStack.isEmpty())
        {
            matrixStackIn.push();

            getEntityModel().bipedBody.translateRotate(matrixStackIn);

            matrixStackIn.translate(0.0D, 0.3D, 0.3D);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180f));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180f));

            renderItem(itemStack, matrixStackIn, bufferIn, 0xF000F0);

            matrixStackIn.pop();
        }
    }

    public static void renderItem(ItemStack stack, MatrixStack ms, IRenderTypeBuffer buffers, int light) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, ms, buffers);
    }

    public boolean shouldRender(AbstractClientPlayerEntity entitylivingbaseIn)
    {
        return hasBag(entitylivingbaseIn) && !ItemUtils.isItemEqual(entitylivingbaseIn.getHeldItemMainhand(), itemStack, false);
    }

    public boolean hasBag(AbstractClientPlayerEntity entitylivingbaseIn)
    {
        AtomicBoolean returnValue = new AtomicBoolean(false);
        QuantumStorage.BAGS.forEach((dyeColor, itemQuantumBag) ->
        {
            ItemStack stack = new ItemStack(itemQuantumBag);
           if(entitylivingbaseIn.inventory.hasItemStack(stack))
           {
               returnValue.getAndSet(true);
               itemStack = stack;
               return;
           }
        });
        return returnValue.get();
    }
}

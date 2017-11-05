package QuantumStorage.client.render;

import QuantumStorage.tiles.TileQuantumBarrel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by Gigabit101 on 18/03/2017.
 */
@Deprecated
public class RenderBarrel extends TileEntitySpecialRenderer<TileQuantumBarrel>
{
    public static Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void render(TileQuantumBarrel te, double x, double y, double z, float p_192841_8_, int destroyStage, float partialTicks)
    {
        final ItemStackHandler stackHandler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (!stackHandler.getStackInSlot(0).isEmpty())
        {
            ItemStack stack = stackHandler.getStackInSlot(0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);

            renderItem(getWorld(), te, stack, te.getFacing(), partialTicks);

            GlStateManager.popMatrix();
        }
    }

    private void renderItem(World world, TileQuantumBarrel te, ItemStack stack, EnumFacing f, float partialTicks)
    {
        RenderItem itemRenderer = mc.getRenderItem();

        if (stack != null)
        {
            float f1 = 0.6666667F;
            float f3 = 0.015625F * f1;
            GlStateManager.pushMatrix();

            if (f == EnumFacing.SOUTH)
            {
                GlStateManager.translate(0.8, 0.4 + (0.5F * f1), 0.96 + (0.01f * f1));
            } else if (f == EnumFacing.NORTH)
            {
                GlStateManager.rotate(180, 0.0F, 1.0F, 0);
                GlStateManager.translate(-0.8, 0.4 + (0.5F * f1), -0.04 + (0.01f * f1));
            } else if (f == EnumFacing.WEST)
            {
                GlStateManager.translate(0.8, 0.4 + (0.5F * f1), 0.5 + (0.01f * f1));
                GlStateManager.rotate(90, 0.0F, 1.0F, 0);
            } else if (f == EnumFacing.EAST)
            {
                GlStateManager.rotate(-90, 0.0F, 1.0F, 0);
                GlStateManager.translate(0.8, 0.4 + (0.5F * f1), -0.04 + (0.01f * f1));
            }

            GlStateManager.scale(f3, -f3, f3);
            GlStateManager.glNormal3f(0.0F, 0.0F, -1.0F * f3);
            GlStateManager.depthMask(false);

            GlStateManager.popMatrix();
            if (f == EnumFacing.SOUTH)
            {
                GlStateManager.translate(0.5, 0.4, 0.96);
            } else if (f == EnumFacing.NORTH)
            {
                GlStateManager.translate(0.5, 0.4, 0.04);
            } else if (f == EnumFacing.EAST)
            {
                GlStateManager.translate(0.96, 0.4, 0.5);
                GlStateManager.rotate(90, 0.0F, 1.0F, 0);
            } else if (f == EnumFacing.WEST)
            {
                GlStateManager.translate(0.0, 0.4, 0.5);
                GlStateManager.rotate(90, 0.0F, 1.0F, 0);
            }

            EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, stack);
            entityitem.getItem().setCount(1);

            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();

            GlStateManager.rotate(180f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(180f, 0.0f, 0.0f, 1.0f);
            GlStateManager.scale(0.4F, 0.4F, 0.4F);
            GlStateManager.depthMask(true);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();

            itemRenderer.renderItem(entityitem.getItem(), ItemCameraTransforms.TransformType.FIXED);

            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}

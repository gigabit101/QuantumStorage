package QuantumStorage.client;

import QuantumStorage.tiles.TileQuantumBarrel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import reborncore.client.RenderUtil;

/**
 * Created by Gigabit101 on 18/03/2017.
 */
public class RenderBarrel extends TileEntitySpecialRenderer<TileQuantumBarrel>
{
    @Override
    public void renderTileEntityAt(TileQuantumBarrel te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        final ItemStackHandler stackHandler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if(stackHandler.getStackInSlot(0) != ItemStack.EMPTY)
        {
            ItemStack stack = stackHandler.getStackInSlot(0);
            GlStateManager.pushMatrix();

            if (stack.getItem() instanceof ItemBlock)
            {
                GlStateManager.translate(x + .5, y + 1.45, z + .5);
            }
            else
            {
                GlStateManager.translate(x + .5, y + 1.45, z + .5);
            }

            RenderUtil.renderItemInWorld(stack);

//            GlStateManager.translate(x + .5, y + 0.45, z + .5);
//            renderText("BOOP!!!", te, 10F);

            GlStateManager.popMatrix();
        }
    }

    private void renderText (String text, TileQuantumBarrel tile, float alpha)
    {
        if (text == null || text.isEmpty())
            return;

        int textWidth = getFontRenderer().getStringWidth(text);

        GlStateManager.pushMatrix();

        GlStateManager.disableLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.doPolygonOffset(-1, -20);
        GlStateManager.rotate(180, 0, 0, 0);
        GlStateManager.scale(0.02, 0.02, 0.02);

        getFontRenderer().drawString(text, -textWidth / 2, 0, (int)(255 * alpha) << 24 | 255 << 16 | 255 << 8 | 255);

        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableLighting();

        GlStateManager.popMatrix();
    }
}

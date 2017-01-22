package QuantumStorage.client;

import QuantumStorage.items.upgrades.ItemRenderUpgrade;
import QuantumStorage.tile.TileQuantumDsu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import reborncore.client.RenderUtil;

/**
 * Created by Gigabit101 on 28/12/2016.
 */
public class RenderQSU extends TileEntitySpecialRenderer<TileQuantumDsu>
{
    @Override
    public void renderTileEntityAt(TileQuantumDsu te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if (te != null && te.getTileData().hasKey(ItemRenderUpgrade.NBT_KEY))
        {
            if (te.getStackInSlot(1) != null)
            {
                GlStateManager.pushMatrix();

                ItemStack stack = te.getStackInSlot(1);
                if(stack != null)
                {
                    double spin = Minecraft.getSystemTime() / 1000D;

                    if (stack.getItem() instanceof ItemBlock)
                    {
                        GlStateManager.translate(x + .5, y + 1.45, z + .5);
                    } else
                    {
                        GlStateManager.translate(x + .5, y + 1.45, z + .5);
                    }
                    GlStateManager.rotate((float) (((spin * 40D) % 360)), 0, 1, 0);

                    RenderUtil.renderItemInWorld(stack);
                }
                GlStateManager.popMatrix();
            }
        }
    }
}

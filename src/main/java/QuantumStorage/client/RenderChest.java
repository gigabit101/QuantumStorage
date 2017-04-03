package QuantumStorage.client;

import QuantumStorage.QuantumStorage;
import QuantumStorage.tiles.TileChestIron;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Gigabit101 on 29/03/2017.
 */
public class RenderChest extends TileEntitySpecialRenderer<TileChestIron>
{
    private ModelChest model;
    private static float halfPI = (float) (Math.PI / 2D);
    private static float[][] shifts = { { 0.3F, 0.45F, 0.3F }, { 0.7F, 0.45F, 0.3F }, { 0.3F, 0.45F, 0.7F }, { 0.7F, 0.45F, 0.7F }, { 0.3F, 0.1F, 0.3F },
            { 0.7F, 0.1F, 0.3F }, { 0.3F, 0.1F, 0.7F }, { 0.7F, 0.1F, 0.7F }, { 0.5F, 0.32F, 0.5F }, };

    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(QuantumStorage.MOD_ID + ":textures/blocks/chest_iron.png");

    public RenderChest()
    {
        this.model = new ModelChest();
    }

    @Override
    public void renderTileEntityAt(TileChestIron te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if (te == null || te.isInvalid())
        {
            return;
        }

        EnumFacing facing = EnumFacing.NORTH;
        if(te.hasWorld())
        {
            facing = te.getFacing();
        }

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4F, 4F, 1F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            this.bindTexture(TEXTURE_NORMAL);
        }

        GlStateManager.pushMatrix();

        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.translate((float) x, (float) y + 1F, (float) z + 1F);
        GlStateManager.scale(1F, -1F, -1F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);

        switch (facing)
        {
            case NORTH:
            {
                GlStateManager.rotate(180F, 0F, 1F, 0F);
                break;
            }
            case SOUTH:
            {
                GlStateManager.rotate(0F, 0F, 1F, 0F);
                break;
            }
            case WEST:
            {
                GlStateManager.rotate(90F, 0F, 1F, 0F);
                break;
            }
            case EAST:
            {
                GlStateManager.rotate(270F, 0F, 1F, 0F);
                break;
            }
            default:
            {
                GlStateManager.rotate(0F, 0F, 1F, 0F);
                break;
            }
        }

        GlStateManager.translate(-0.5F, -0.5F, -0.5F);

        float lidangle = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;

        lidangle = 1F - lidangle;
        lidangle = 1F - lidangle * lidangle * lidangle;

        this.model.chestLid.rotateAngleX = -lidangle * halfPI;
        this.model.renderAll();

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }

        GlStateManager.popMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
    }
}

package QuantumStorage.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

public class RenderUtils
{
    public static final ResourceLocation TEXTURE_MAP = TextureMap.LOCATION_BLOCKS_TEXTURE;
    
    public static void renderFluidCuboid(FluidStack fluid, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2)
    {
        int color = fluid.getFluid().getColor(fluid);
        renderFluidCuboid(fluid, pos, x, y, z, x1, y1, z1, x2, y2, z2, color);
    }
    
    public static void renderFluidCuboid(FluidStack fluid, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2, int color)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        bindTexture1(TEXTURE_MAP);
        
        int brightness = FMLClientHandler.instance().getClient().world.getCombinedLight(pos, fluid.getFluid().getLuminosity());
        
        pre(x, y, z);
        
        TextureAtlasSprite still = FMLClientHandler.instance().getClient().getTextureMapBlocks().getTextureExtry(fluid.getFluid().getStill(fluid).toString());
        TextureAtlasSprite flowing = FMLClientHandler.instance().getClient().getTextureMapBlocks().getTextureExtry(fluid.getFluid().getFlowing(fluid).toString());
        
        putTexturedQuad(renderer, still, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.DOWN, color, brightness, false);
        putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.NORTH, color, brightness, true);
        putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.EAST, color, brightness, true);
        putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.SOUTH, color, brightness, true);
        putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.WEST, color, brightness, true);
        putTexturedQuad(renderer, still, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.UP, color, brightness, false);
        
        tessellator.draw();
        
        post();
    }
    
    public static void bindTexture1(ResourceLocation rl)
    {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(rl);
    }
    
    public static void putTexturedQuad(BufferBuilder renderer, TextureAtlasSprite sprite, double x, double y, double z, double w, double h, double d, EnumFacing face, int color, int brightness, boolean flowing)
    {
        int l1 = brightness >> 0x10 & 0xFFFF;
        int l2 = brightness & 0xFFFF;
        
        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        
        putTexturedQuad(renderer, sprite, x, y, z, w, h, d, face, r, g, b, a, l1, l2, flowing);
    }
    
    public static void putTexturedQuad(BufferBuilder renderer, TextureAtlasSprite sprite, double x, double y, double z, double w, double h, double d, EnumFacing face, int r, int g, int b, int a, int light1, int light2, boolean flowing)
    {
        if (sprite == null)
            return;
        double minU;
        double maxU;
        double minV;
        double maxV;
        
        double size = 16f;
        if (flowing) size = 8f;
        
        double x1 = x;
        double x2 = x + w;
        double y1 = y;
        double y2 = y + h;
        double z1 = z;
        double z2 = z + d;
        
        double xt1 = x1 % 1d;
        double xt2 = xt1 + w;
        while (xt2 > 1f) xt2 -= 1f;
        double yt1 = y1 % 1d;
        double yt2 = yt1 + h;
        while (yt2 > 1f) yt2 -= 1f;
        double zt1 = z1 % 1d;
        double zt2 = zt1 + d;
        while (zt2 > 1f) zt2 -= 1f;
        
        if (flowing)
        {
            double tmp = 1d - yt1;
            yt1 = 1d - yt2;
            yt2 = tmp;
        }
        
        switch (face)
        {
            case DOWN:
            case UP:
                minU = sprite.getInterpolatedU(xt1 * size);
                maxU = sprite.getInterpolatedU(xt2 * size);
                minV = sprite.getInterpolatedV(zt1 * size);
                maxV = sprite.getInterpolatedV(zt2 * size);
                break;
            case NORTH:
            case SOUTH:
                minU = sprite.getInterpolatedU(xt2 * size);
                maxU = sprite.getInterpolatedU(xt1 * size);
                minV = sprite.getInterpolatedV(yt1 * size);
                maxV = sprite.getInterpolatedV(yt2 * size);
                break;
            case WEST:
            case EAST:
                minU = sprite.getInterpolatedU(zt2 * size);
                maxU = sprite.getInterpolatedU(zt1 * size);
                minV = sprite.getInterpolatedV(yt1 * size);
                maxV = sprite.getInterpolatedV(yt2 * size);
                break;
            default:
                minU = sprite.getMinU();
                maxU = sprite.getMaxU();
                minV = sprite.getMinV();
                maxV = sprite.getMaxV();
        }
        
        switch (face)
        {
            case DOWN:
                renderer.pos(x1, y1, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y1, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y1, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x1, y1, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                break;
            case UP:
                renderer.pos(x1, y2, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x1, y2, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                break;
            case NORTH:
                renderer.pos(x1, y1, z1).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x1, y2, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y1, z1).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                break;
            case SOUTH:
                renderer.pos(x1, y1, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y1, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z2).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x1, y2, z2).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                break;
            case WEST:
                renderer.pos(x1, y1, z1).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x1, y1, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x1, y2, z2).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x1, y2, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                break;
            case EAST:
                renderer.pos(x2, y1, z1).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y1, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                break;
        }
    }
    
    public static void pre(double x, double y, double z)
    {
        GlStateManager.pushMatrix();
        
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GL11.glShadeModel(GL11.GL_SMOOTH);
        } else
        {
            GL11.glShadeModel(GL11.GL_FLAT);
        }
        
        GlStateManager.translate(x, y, z);
    }
    
    public static void post()
    {
        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}

//package QuantumStorage.client.render;
//
//import QuantumStorage.tiles.TileQuantumTank;
//import QuantumStorage.utils.RenderUtils;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
//import net.minecraftforge.fluids.FluidStack;
//import net.minecraftforge.fluids.FluidTank;
//import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
//import org.lwjgl.opengl.GL11;
//
//public class TankRender extends TileEntitySpecialRenderer<TileQuantumTank>
//{
//    int renderOffset = 10;
//
//    @Override
//    public void render(TileQuantumTank te, double posX, double posY, double posZ, float partialTicks, int destroyStage, float alpha)
//    {
//        TileQuantumTank tileEntityTank = (TileQuantumTank) te;
//        if (te == null) return;
//        GlStateManager.pushMatrix();
//
//        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(0x302, 0x303);
//
//        GlStateManager.translate(posX + 0.5f, posY + 1.5f, posZ + 0.5f);
//        GlStateManager.rotate(180f, 1f, 0f, 0f);
//
//        GL11.glColor4f(1f, 1f, 1f, 1f);
//
//        GL11.glColor4f(1f, 1f, 1f, 1f);
//
//        GlStateManager.disableBlend();
//        GlStateManager.popMatrix();
//        renderFluid(te, posX, posY, posZ, partialTicks);
//    }
//
//    private void renderFluid(TileQuantumTank tile, double x, double y, double z, float partialTicks)
//    {
//        FluidTank tank = (FluidTank) tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
//        if (tank.getFluid() != null)
//        {
//            FluidStack fluid = tank.getFluid();
//
//            float height = (((float) fluid.amount - renderOffset) / (float) tank.getInfo().capacity) * 0.6f;
//
//            if (renderOffset > 1.2f || renderOffset < -1.2f)
//            {
//                renderOffset -= (renderOffset / 12f + 0.1f) * partialTicks;
//            } else
//            {
//                renderOffset = 0;
//            }
//
//            float d = 0.005f;
//            GlStateManager.pushMatrix();
//            RenderUtils.renderFluidCuboid(fluid, tile.getPos(), x + 0.05f, y + 0.38f, z + 0.05f, d, d, d, 0.9d - d, height, 0.9d - d);
//            GlStateManager.popMatrix();
//        }
//    }
//}

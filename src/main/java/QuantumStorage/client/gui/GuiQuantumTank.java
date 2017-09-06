package QuantumStorage.client.gui;

import QuantumStorage.client.container.ContainerQuantumTank;
import QuantumStorage.tile.TileQuantumTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import reborncore.client.RenderUtil;

public class GuiQuantumTank extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation("quantumstorage", "textures/gui/dsu.png");
    private static Minecraft mc = Minecraft.getMinecraft();

    TileQuantumTank tile;
    public int amoauntStored;
    ContainerQuantumTank containerQuantumTank;

    public GuiQuantumTank(EntityPlayer player, TileQuantumTank tile) {
        super(new ContainerQuantumTank(tile, player));
        this.xSize = 176;
        this.ySize = 167;
        this.tile = tile;
        containerQuantumTank = (ContainerQuantumTank) this.inventorySlots;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        FluidTank tank = (FluidTank) tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

        RenderUtil.renderGuiTank(tank.getFluid(), 1, 1, k + 95, l + 51, zLevel, 16, 16);
    }

    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        FluidTank tank = (FluidTank) tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

        String name = I18n.translateToLocal("tile.quantumtank.name");
        this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.translateToLocalFormatted("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        if (tank.getFluid() != null)
            this.fontRendererObj.drawString(tank.getFluid().getLocalizedName(), 10, 20, 16448255);
        this.fontRendererObj.drawString(getStringToDraw(containerQuantumTank.stackamount, containerQuantumTank.stackSizeType) + " mb", 10, 30, 16448255);
    }

    private String getStringToDraw(int size, int sizeType){
        String sizeStrType = "";
        String sizeStr = Integer.toString(size);
        if(sizeType == 0){
            return Integer.toString(size);
        } else if(sizeType == 1){
            sizeStrType = "K";
        } else if(sizeType == 2){
            sizeStrType = "M";
        } else if(sizeType == 3){
            sizeStrType = "B";
        }

        String number = sizeStr.substring(0, sizeStr.length() -1);
        String decimal = sizeStr.substring(sizeStr.length() -1);
        if(decimal.equals("0")){
            return number + sizeStrType;
        }
        return number + "." + decimal + sizeStrType;
    }
}

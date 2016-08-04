package QuantumStorage.client.gui;

import QuantumStorage.client.container.ContainerQuantumTank;
import QuantumStorage.tile.TileQuantumTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiQuantumTank extends GuiContainer 
{
	private static final ResourceLocation texture = new ResourceLocation("quantumstorage", "textures/gui/dsu.png");
	private static Minecraft mc = Minecraft.getMinecraft();

	TileQuantumTank tile;
	public int amoauntStored;

	public GuiQuantumTank(EntityPlayer player, TileQuantumTank tile) 
	{
		super(new ContainerQuantumTank(tile, player));
		this.xSize = 176;
		this.ySize = 167;
		this.tile = tile;
	}
	
	@Override
	public void initGui() 
	{
		super.initGui();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) 
	{
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) 
	{
		String name = I18n.translateToLocal("tile.quantumtank.name");
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.translateToLocalFormatted("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
		if(tile.tank.getFluid() != null)
			this.fontRendererObj.drawString(tile.tank.getFluid().getLocalizedName() + " Amount", 10, 20, 16448255);
        this.fontRendererObj.drawString(tile.tank.getFluidAmount() + " mb", 10, 30, 16448255);
	}
}

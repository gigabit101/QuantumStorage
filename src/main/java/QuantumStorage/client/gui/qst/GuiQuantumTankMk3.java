package QuantumStorage.client.gui.qst;

import QuantumStorage.client.container.qst.ContainerQuantumTankMk3;
import QuantumStorage.tile.qst.TileQuantumTankMk3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiQuantumTankMk3 extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("quantumstorage", "textures/gui/dsu.png");
	private static Minecraft mc = Minecraft.getMinecraft();

	TileQuantumTankMk3 tile;
	public int amoauntStored;

	public GuiQuantumTankMk3(EntityPlayer player, TileQuantumTankMk3 tile) 
	{
		super(new ContainerQuantumTankMk3(tile, player));
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
		String name = StatCollector.translateToLocal("tile.quantumtankmk3.name");
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
		if(tile.tank.getFluid() != null)
			this.fontRendererObj.drawString(tile.tank.getFluid().getLocalizedName() + " Amount", 10, 20, 16448255);
        this.fontRendererObj.drawString(tile.tank.getFluidAmount() + " mb", 10, 30, 16448255);
	}
}

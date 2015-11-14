package QuantumStorage.client.gui;

import QuantumStorage.client.container.ContainerQuantumChest;
import QuantumStorage.client.container.dsu.ContainerQuantumDsuMk1;
import QuantumStorage.tile.TileQuantumChest;
import QuantumStorage.tile.qsu.TileQuantumDsuMk1;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiQuantumChest extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
	private static Minecraft mc = Minecraft.getMinecraft();

	TileQuantumChest tile;
	public int amoauntStored;

	public GuiQuantumChest(EntityPlayer player, TileQuantumChest tile) 
	{
		super(new ContainerQuantumChest(tile, player));
		this.xSize = 192;
		this.ySize = 222;
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
		String name = StatCollector.translateToLocal("tile.quantumchest.name");
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}
}

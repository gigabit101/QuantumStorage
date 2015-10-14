package QuantumStorage.client.gui;

import org.lwjgl.opengl.GL11;

import QuantumStorage.client.container.ContainerCrafingCard;
import QuantumStorage.client.container.ContainerQuantumBag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiQuantumBag extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation("quantumstorage", "textures/gui/quantumbag.png");

	public GuiQuantumBag(EntityPlayer player) 
	{
		super(new ContainerQuantumBag(player));
		this.xSize = 192;
		this.ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) 
	{
		fontRendererObj.drawString(I18n.format("item.quantumBag.name", new Object[0]), 58, this.ySize - 215, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 95, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l,  0, 0, this.xSize, this.ySize);
	}
}

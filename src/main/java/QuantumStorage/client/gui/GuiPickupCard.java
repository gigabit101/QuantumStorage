package QuantumStorage.client.gui;

import org.lwjgl.opengl.GL11;

import QuantumStorage.client.container.ContainerPickupCard;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPickupCard extends GuiContainer{
	
	private static final ResourceLocation texture = new ResourceLocation("quantumstorage", "textures/gui/pickupcard.png");

	public GuiPickupCard(EntityPlayer player) 
	{
		super(new ContainerPickupCard(player));
		this.xSize = 186;
		this.ySize = 186;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) 
	{
		fontRendererObj.drawString(I18n.format("container.pickupcard", new Object[0]), 28, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}

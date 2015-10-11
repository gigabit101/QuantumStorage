package QuantumStorage.client.gui;

import org.lwjgl.opengl.GL11;

import QuantumStorage.client.container.ContainerCrafingCard;
import QuantumStorage.client.container.ContainerQuantumBag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiQuantumBag extends GuiContainer
{
	private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("textures/gui/container/generic_54.png");

	public GuiQuantumBag(InventoryPlayer p_i1084_1_, World p_i1084_2_) 
	{
		super(new ContainerQuantumBag(p_i1084_1_, p_i1084_2_));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) 
	{
		fontRendererObj.drawString(I18n.format("container.quantumbag", new Object[0]), 28, ySize - 180, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 60, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(craftingTableGuiTextures);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l - 22, 0, 0, xSize, ySize + 60);
	}
}

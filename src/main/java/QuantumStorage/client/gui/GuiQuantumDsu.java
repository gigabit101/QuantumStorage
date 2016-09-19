package QuantumStorage.client.gui;

import java.io.IOException;

import QuantumStorage.client.container.ContainerQuantumDsu;
import QuantumStorage.tile.TileQuantumDsu;
import QuantumStorage.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiQuantumDsu extends GuiContainer 
{
	private static final ResourceLocation texture = new ResourceLocation("quantumstorage", "textures/gui/dsu.png");
	private static Minecraft mc = Minecraft.getMinecraft();

	TileQuantumDsu tile;
	public int amoauntStored;
	public String buttontxt;

	public GuiQuantumDsu(EntityPlayer player, TileQuantumDsu tile) 
	{
		super(new ContainerQuantumDsu(tile, player));
		this.xSize = 176;
		this.ySize = 167;
		this.tile = tile;
		buttonList.clear();
	}
	
	@Override
	public void initGui() 
	{
		super.initGui();
		int k = (this.width - this.xSize) / 2 + 132;
		int l = (this.height - this.ySize) / 2 + 60;
//		if(tile.isLocked)
//			buttontxt = "UNLOCK";
//		if(!tile.isLocked)
//			buttontxt = "LOCK";
//
//		buttonList.add(new GuiButton(0, k, l, 40 , 20, buttontxt));
	}
	
	@Override
	public void actionPerformed(GuiButton button) throws IOException 
	{
		if(button.id == 0)
		{
			if(!tile.isLocked)
			{
				tile.lock();
			}
			else if(tile.isLocked)
			{
				tile.unlock();
			}
		}
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
		String name = I18n.translateToLocal("tile.quantumdsu.name");
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.translateToLocalFormatted("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString("Amount", 10, 20, 16448255);
		if (tile.storedItem != null && tile.getStackInSlot(1) != null)
			this.fontRendererObj.drawString(tile.storedItem.stackSize + tile.getStackInSlot(1).stackSize + "", 10, 30, 16448255);
		if (tile.storedItem == null && tile.getStackInSlot(1) != null)
			this.fontRendererObj.drawString(StringUtil.getRoundedString(tile.getStackInSlot(1).stackSize, ""), 10, 30, 16448255);
	}
}

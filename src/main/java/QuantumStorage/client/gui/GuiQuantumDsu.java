package QuantumStorage.client.gui;

import QuantumStorage.client.container.ContainerQuantumDsu;
import QuantumStorage.tile.TileQuantumDsu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import java.io.IOException;

public class GuiQuantumDsu extends GuiContainer 
{
	private static final ResourceLocation texture = new ResourceLocation("quantumstorage", "textures/gui/dsu.png");
	private static Minecraft mc = Minecraft.getMinecraft();

	TileQuantumDsu tile;
	public int amoauntStored;
	public String buttontxt;
	ContainerQuantumDsu containerQuantumDsu;
    EntityPlayer player;

	public GuiQuantumDsu(EntityPlayer player, TileQuantumDsu tile) 
	{
		super(new ContainerQuantumDsu(tile, player));
		this.xSize = 176;
		this.ySize = 167;
		this.tile = tile;
        this.player = player;
		buttonList.clear();
		containerQuantumDsu = (ContainerQuantumDsu) this.inventorySlots;
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

        if(player.getDisplayNameString().toLowerCase().contains("darkosto"))
        {
            this.fontRendererObj.drawString("HAPPY BIRTHDAY", 10, 40, 16448255);
            this.fontRendererObj.drawString("DARKOSTO", 10, 50, 16448255);
        }

		if ((tile.storedItem != null && tile.storedItem.stackSize != 0) && tile.getStackInSlot(1) != null){
			this.fontRendererObj.drawString(getStringToDraw(containerQuantumDsu.stackamount, containerQuantumDsu.stackSizeType), 10, 30, 16448255);
		}

		if ((tile.storedItem == null || tile.storedItem.stackSize == 0)  && tile.getStackInSlot(1) != null){
			this.fontRendererObj.drawString(getStringToDraw(tile.getStackInSlot(1).stackSize, 0), 10, 30, 16448255);
		}

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
			return number + " " + sizeStrType;
		}
		return number + "." + decimal + " " + sizeStrType;
	}
}

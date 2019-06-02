package QuantumStorage.client;

import QuantumStorage.inventory.AdvancedContainer;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class AdvancedGui extends GuiContainer
{
    public AdvancedTileEntity machine;
    public EntityPlayer player;
    
    public AdvancedGui(EntityPlayer player, AdvancedTileEntity machine)
    {
        super(new AdvancedContainer(player, machine));
        this.machine = machine;
        this.player = player;
        this.xSize = machine.getXSize();
        this.ySize = machine.getYsize();
        buttonList.clear();
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        machine.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY, guiLeft, guiTop, xSize, ySize, this);
    }
    
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        machine.drawGuiContainerForegroundLayer(mouseX, mouseY, this, guiLeft, guiTop);
    }
    
    public void drawCentredString(String string, int y, int colour)
    {
        drawString(string, (xSize / 2 - mc.fontRenderer.getStringWidth(string) / 2), y, colour);
    }
    
    public void drawString(String string, int x, int y, int colour)
    {
        int factorX = 0;
        int factorY = 0;
        mc.fontRenderer.drawString(string, x + factorX, y + factorY, colour);
        GlStateManager.color(1, 1, 1, 1);
    }
}

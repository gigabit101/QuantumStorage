package QuantumStorage.rewrite;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class AdvancedGui extends GuiContainer
{
    public int xSize = 176;
    public int ySize = 176;
    public AdvancedTileEntity machine;
    public EntityPlayer player;

    public AdvancedGui(EntityPlayer player, AdvancedTileEntity machine)
    {
        super(new AdvancedContainer(player, machine));
        this.machine = machine;
        this.player = player;
        buttonList.clear();
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
        drawString(string, (xSize / 2 - mc.fontRendererObj.getStringWidth(string) / 2), y, colour);
    }

    public void drawString(String string, int x, int y, int colour)
    {
        int factorX = 0;
        int factorY = 0;
        mc.fontRendererObj.drawString(string, x + factorX, y + factorY, colour);
        GlStateManager.color(1, 1, 1, 1);
    }
}

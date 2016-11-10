package QuantumStorage.client.gui;

import QuantumStorage.client.container.ContainerQChest;
import QuantumStorage.tile.prefab.TileQChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import reborncore.client.guibuilder.GuiBuilder;

/**
 * Created by Gigabit101 on 01/11/2016.
 */
public class GuiQChest extends GuiContainer
{
    GuiBuilder builder = new GuiBuilder(GuiBuilder.defaultTextureSheet);
    int size;

    public GuiQChest(EntityPlayer player, TileQChest inventorySlotsIn)
    {
        super(new ContainerQChest(inventorySlotsIn, player));
        this.size = inventorySlotsIn.getInvSize();
        this.xSize = 250;
        this.ySize = 240;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 140, true);
        drawSlots(13, 6, size);
        for (int x = 0; x < 4; x++)
        {
            builder.drawSlot(this, guiLeft + 8, guiTop + 140 + x * 18);
        }
    }

    public void drawSlots(int col, int rows, int max)
    {
        int i = 0;
        for (int y = 0; y < rows; y++)
        {
            for (int x = 0; x < col; x++)
            {
                i++;
                if(i <= max)
                    builder.drawSlot(this, guiLeft + 8 + x * 18, guiTop + 20 + y * 18);
            }
        }
    }
}

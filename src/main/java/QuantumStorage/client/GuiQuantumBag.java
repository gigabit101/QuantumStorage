package QuantumStorage.client;

import QuantumStorage.inventory.ContainerQuantumBag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandlerModifiable;

public class GuiQuantumBag extends GuiContainer
{
    public static GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();

    public GuiQuantumBag(EntityPlayer player, IItemHandlerModifiable invBag)
    {
        super(new ContainerQuantumBag(player, invBag));
        this.xSize = 250;
        this.ySize = 240;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);

        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 150, true);

        int i = 0;
        for (int y = 0; y < 7; y++)
        {
            for (int x = 0; x < 13; x++)
            {
                i++;
                builder.drawSlot(this, guiLeft + 7 + x * 18, guiTop + 10 + y * 18);
            }
        }
    }
}

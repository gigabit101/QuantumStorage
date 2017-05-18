package QuantumStorage.multiblock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import reborncore.client.guibuilder.GuiBuilder;

import java.io.IOException;

/**
 * Created by Gigabit101 on 12/05/2017.
 */
public class GuiMultiBlockCrate extends GuiContainer
{
    GuiBuilder builder = new GuiBuilder(GuiBuilder.defaultTextureSheet);
    MultiBlockCrate multiBlockCrate;

    BlockPos pos;
    public static int maxSlotsPerPage = 78;

    public GuiMultiBlockCrate(EntityPlayer player, MultiBlockCrate multiBlockCrate, BlockPos pos)
    {
        super(new ContainerMultiBlockCrate(player, multiBlockCrate));
        this.xSize = 250;
        this.ySize = 240;
        this.multiBlockCrate = multiBlockCrate;
        this.pos = pos;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 140, true);
        if (multiBlockCrate.getInv().getSlots() != 0)
        {
            drawSlots(13, 6, maxSlotsPerPage);
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
                if (i <= max && i <= multiBlockCrate.getInv().getSlots())
                    builder.drawSlot(this, guiLeft + 8 + x * 18, guiTop + 20 + y * 18);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if (multiBlockCrate.getInv().getSlots() != 0)
        {
            this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Amount of Slots " + multiBlockCrate.getInv().getSlots(), 125, 130, 4210752);
        }
    }

    @Override
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, false);
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
    }
}

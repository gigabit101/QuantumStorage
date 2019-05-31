package QuantumStorage.multiblock;

import QuantumStorage.network.PacketGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;
import reborncore.client.guibuilder.GuiBuilder;
import reborncore.common.network.NetworkManager;

import java.awt.*;
import java.io.IOException;

public class GuiMultiStorage extends GuiContainer
{
    GuiBuilder builder = new GuiBuilder(GuiBuilder.defaultTextureSheet);
    MultiBlockStorage storage;
    
    int page = 0;
    BlockPos pos;
    public static int maxSlotsPerPage = 78;
    
    public GuiMultiStorage(EntityPlayer player, MultiBlockStorage storage, int page, BlockPos pos)
    {
        super(new ContainerMultiBlockStorage(player, storage, page));
        this.xSize = 250;
        this.ySize = 240;
        this.storage = storage;
        this.page = page;
        this.pos = pos;
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 140, true);
        if (storage.invs.size() != 0)
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
                if (i <= max)
                    builder.drawSlot(this, guiLeft + 8 + x * 18, guiTop + 20 + y * 18);
            }
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if (storage.invs.size() == 0)
        {
            drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Multiblock must contain at least 1 storage block", xSize / 2, 75, Color.RED.getRGB());
        } else
        {
            this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Page " + page + " of " + storage.invs.size(), 125, 130, 4210752);
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
        this.buttonList.clear();
        if (storage.invs.size() != 0)
        {
            if (page > 1)
            {
                this.buttonList.add(new GuiButton(this.page - 2, this.guiLeft + 13, this.guiTop + 172, 20, 20, "<"));
            }
            if (storage.invs.size() > page)
            {
                this.buttonList.add(new GuiButton(this.page, this.guiLeft + 209, this.guiTop + 172, 20, 20, ">"));
            }
        }
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
            if (page > 1)
            {
                NetworkManager.sendToServer(new PacketGui(button.id - 10, pos));
            } else if (storage.invs.size() > page)
            {
                NetworkManager.sendToServer(new PacketGui(button.id + 10, pos));
            }
        } else
        {
            NetworkManager.sendToServer(new PacketGui(button.id, pos));
        }
    }
}

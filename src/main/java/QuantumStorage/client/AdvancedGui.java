package QuantumStorage.client;

import QuantumStorage.inventory.AdvancedContainer;
import QuantumStorage.tiles.AdvancedTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class AdvancedGui extends ContainerScreen
{
    public AdvancedTileEntity machine;
    public PlayerEntity player;
    
    public AdvancedGui(PlayerEntity player, AdvancedTileEntity machine)
    {
        super(new AdvancedContainer(player, machine), player.inventory, new TextComponent() {
            @Override
            public String getUnformattedComponentText() {
                return null;
            }

            @Override
            public ITextComponent shallowCopy() {
                return null;
            }
        });
        this.machine = machine;
        this.player = player;
        this.xSize = machine.getXSize();
        this.ySize = machine.getYsize();
//        buttonList.clear();
    }

    @Override
    protected void init()
    {
        super.init();
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
        drawString(string, (xSize / 2 - font.getStringWidth(string) / 2), y, colour);
    }
    
    public void drawString(String string, int x, int y, int colour)
    {
        int factorX = 0;
        int factorY = 0;
        font.drawString(string, x + factorX, y + factorY, colour);
        GlStateManager.color4f(1, 1, 1, 1);
    }
}

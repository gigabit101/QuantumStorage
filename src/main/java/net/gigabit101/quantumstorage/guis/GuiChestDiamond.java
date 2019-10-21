package net.gigabit101.quantumstorage.guis;

import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerChestDiamond;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiChestDiamond extends ContainerScreen<ContainerChestDiamond>
{
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();

    public GuiChestDiamond(ContainerChestDiamond container, PlayerInventory playerinv, ITextComponent title)
    {
        super(container, playerinv, title);
        this.xSize = 240;
        this.ySize = 240;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 131, true);
    
        for (int l = 0; l < 6; ++l)
        {
            for (int j1 = 0; j1 < 12; ++j1)
            {
                builder.drawSlot(this, guiLeft + 14 + j1 * 18 - 1, guiTop + 8 + l * 18 - 1);
            }
        }
    }
}

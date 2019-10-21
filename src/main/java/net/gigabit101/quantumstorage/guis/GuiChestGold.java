package net.gigabit101.quantumstorage.guis;

import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerChestGold;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiChestGold extends ContainerScreen<ContainerChestGold>
{
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();

    public GuiChestGold(ContainerChestGold container, PlayerInventory playerinv, ITextComponent title)
    {
        super(container, playerinv, title);
        this.xSize = 190;
        this.ySize = 220;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 131, true);
    
        for (int l = 0; l < 6; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                builder.drawSlot(this, guiLeft + 14 + j1 * 18 - 1, guiTop + 8 + l * 18 - 1);
            }
        }
    }
}

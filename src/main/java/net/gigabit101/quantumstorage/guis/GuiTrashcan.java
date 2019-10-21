package net.gigabit101.quantumstorage.guis;

import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerTrashcan;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiTrashcan extends ContainerScreen<ContainerTrashcan>
{
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();

    public GuiTrashcan(ContainerTrashcan container, PlayerInventory playerinv, ITextComponent title)
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

        builder.drawSlot(this, guiLeft + xSize / 2 - 9, guiTop + 60);
    }
}

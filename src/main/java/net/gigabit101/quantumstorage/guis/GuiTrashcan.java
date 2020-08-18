package net.gigabit101.quantumstorage.guis;

import com.mojang.blaze3d.matrix.MatrixStack;
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

    //Render
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_)
    {
        builder.drawDefaultBackground(this, matrixStack, guiLeft, guiTop, xSize, ySize, 256, 256);
        builder.drawPlayerSlots(this, matrixStack, guiLeft + xSize / 2, guiTop + 131, true, 256, 256);

        builder.drawSlot(this, matrixStack, guiLeft + xSize / 2 - 9, guiTop + 60, 256, 256);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.font.func_238422_b_(matrixStack, this.title, 65.0F, 6.0F, 4210752);
        this.font.func_238422_b_(matrixStack, this.playerInventory.getDisplayName(), 14.0F, (float) (this.ySize - 100), 4210752);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrixStack, mouseX, mouseY);
    }
}

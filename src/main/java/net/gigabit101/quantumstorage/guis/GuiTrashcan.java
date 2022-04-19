package net.gigabit101.quantumstorage.guis;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerTrashcan;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiTrashcan extends AbstractContainerScreen<ContainerTrashcan>
{
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();

    public GuiTrashcan(ContainerTrashcan container, Inventory playerinv, Component title)
    {
        super(container, playerinv, title);
        this.imageWidth = 190;
        this.imageHeight = 220;
    }

    //Render
    @Override
    protected void renderBg(PoseStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_)
    {
        builder.drawDefaultBackground(this, matrixStack, leftPos, topPos, imageWidth, imageHeight, 256, 256);
        builder.drawPlayerSlots(this, matrixStack, leftPos + imageWidth / 2, topPos + 131, true, 256, 256);

        builder.drawSlot(this, matrixStack, leftPos + imageWidth / 2 - 9, topPos + 60, 256, 256);
    }

//    @Override
//    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
//    {
//        this.font.func_238422_b_(matrixStack, this.title.func_241878_f(), 65.0F, 6.0F, 4210752);
//        this.font.func_238422_b_(matrixStack, this.playerInventory.getDisplayName().func_241878_f(), 14.0F, (float) (this.ySize - 100), 4210752);
//    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }
}

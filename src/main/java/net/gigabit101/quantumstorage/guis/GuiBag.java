package net.gigabit101.quantumstorage.guis;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerBag;
import net.gigabit101.quantumstorage.containers.ContainerChestDiamond;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;

public class GuiBag extends ContainerScreen<ContainerBag>
{
    private final PlayerEntity player;
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();

    public GuiBag(ContainerBag container, PlayerInventory inv, ITextComponent title)
    {
        super(container, inv, title);
        this.xSize = 256;
        this.ySize = 231;
        this.player = inv.player;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(stack, mouseX, mouseY);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float v, int i, int i1)
    {
        builder.drawDefaultBackground(this, matrixStack, guiLeft, guiTop, xSize, ySize, 256, 256);
        drawPlayerInventorySlots(matrixStack);
        drawInventorySlots(matrixStack);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_)
    {
//        super.drawGuiContainerForegroundLayer(p_230451_1_, p_230451_2_, p_230451_3_);
    }

    private void drawInventorySlots(MatrixStack matrixStack)
    {
        for(Slot slot : container.inventorySlots)
        {
            if(slot.inventory instanceof PlayerInventory)
                continue;
            builder.drawSlot(this, matrixStack, getGuiLeft() + slot.xPos - 1, getGuiTop() + slot.yPos - 1, 256, 256);
        }
    }

    private void drawPlayerInventorySlots(MatrixStack matrixStack)
    {
        for(Slot slot : container.inventorySlots)
        {
            if(!(slot.inventory instanceof PlayerInventory))
                continue;
            builder.drawSlot(this, matrixStack, getGuiLeft() + slot.xPos - 1, getGuiTop() + slot.yPos - 1, 256, 256);
        }
    }

    @Override
    public void tick()
    {
        if (!this.container.canInteractWith(player)) player.closeScreen();
        super.tick();
    }
}

package net.gigabit101.quantumstorage.guis;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerBag;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class GuiBag extends AbstractContainerScreen<ContainerBag>
{
    private final Player player;
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();
    private ContainerBag container;

    public GuiBag(ContainerBag container, Inventory inv, Component title)
    {
        super(container, inv, title);
        this.container = container;
        this.imageWidth = 256;
        this.imageHeight = 231;
        this.player = inv.player;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float p_97788_, int p_97789_, int p_97790_)
    {
        builder.drawDefaultBackground(this, matrixStack, leftPos, topPos, width, height, 256, 256);
        drawPlayerInventorySlots(matrixStack);
        drawInventorySlots(matrixStack);
    }


    //TODO WHY WAS THIS EVEN A THING...
    private void drawInventorySlots(PoseStack matrixStack)
    {
        for(Slot slot : container.slots)
        {
            if(slot.container instanceof Inventory)
                continue;
            builder.drawSlot(this, matrixStack, getGuiLeft() + slot.x - 1, getGuiTop() + slot.y - 1, 256, 256);
        }
    }

    private void drawPlayerInventorySlots(PoseStack matrixStack)
    {
        for(Slot slot : container.slots)
        {
            if(!(slot.container instanceof Inventory))
                continue;
            builder.drawSlot(this, matrixStack, getGuiLeft() + slot.x - 1, getGuiTop() + slot.y - 1, 256, 256);
        }
    }
}

package net.gigabit101.quantumstorage.guis;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerQSU;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.IItemHandler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class GuiQSU extends AbstractContainerScreen<ContainerQSU>
{
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();
    ContainerQSU container;

    int STORAGE = 1;
    int OUTPUT = 2;

    public GuiQSU(ContainerQSU container, Inventory playerinv, Component title)
    {
        super(container, playerinv, title);
        this.container = container;
        this.imageWidth = 190;
        this.imageHeight = 220;
        this.inventoryLabelY = 120;
    }

    @Override
    protected void init()
    {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack poseStack, float p_97788_, int p_97789_, int p_97790_)
    {
        builder.drawDefaultBackground(this, poseStack, leftPos, topPos, getXSize(), getYSize(), 256, 256);
        builder.drawPlayerSlots(this, poseStack, leftPos + getXSize() / 2, topPos + 131, true, 256, 256);

        builder.drawSlot(this, poseStack, leftPos + imageWidth / 2 - 9, topPos + 30, 256, 256);
        builder.drawSlot(this, poseStack, leftPos + imageWidth / 2 - 9, topPos + 80, 256, 256);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);

        if(!this.container.tileQsu.lockedStack.isEmpty())
        {
            builder.drawLock(this, matrixStack, 2, 2, mouseX - leftPos, mouseY - topPos, 256, 256, "Locked to " + this.container.tileQsu.lockedStack.getDisplayName().getString());
        }

        String line2 = "";
        if(!container.getInv().getStackInSlot(OUTPUT).isEmpty()) line2 = container.getInv().getStackInSlot(OUTPUT).getDisplayName().getString();

        builder.drawBigBlueBar( this, matrixStack, leftPos + 36, topPos + 56, container.getInv().getStackInSlot(STORAGE).getCount() + container.getInv().getStackInSlot(OUTPUT).getCount(), Integer.MAX_VALUE, mouseX - leftPos, mouseY - topPos, "Stored",
                line2, formatQuantity(container.getInv().getStackInSlot(STORAGE).getCount() + container.getInv().getStackInSlot(OUTPUT).getCount()), 256, 256);
    }

    public static final DecimalFormat QUANTITY_FORMATTER = new DecimalFormat("####0.#", DecimalFormatSymbols.getInstance(Locale.US));

    public static String formatQuantity(int qty)
    {
        if (qty >= 1000000)
        {
            return QUANTITY_FORMATTER.format((float) qty / 1000000F) + "M";
        }
        else if (qty >= 1000)
        {
            return QUANTITY_FORMATTER.format((float) qty / 1000F) + "K";
        }
        return String.valueOf(qty);
    }
}

package net.gigabit101.quantumstorage.guis;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerTank;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.IFluidTank;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class GuiTank extends AbstractContainerScreen<ContainerTank>
{
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();
    
    public GuiTank(ContainerTank container, Inventory playerinv, Component title)
    {
        super(container, playerinv, title);
        this.imageWidth = 190;
        this.imageHeight = 220;
    }

    //Render
    @Override
    protected void renderBg(PoseStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_)
    {
        builder.drawDefaultBackground(this, matrixStack, leftPos, topPos, getXSize(), getYSize(), 256, 256);
//        builder.drawPlayerSlots(this, matrixStack, leftPos + imageWidth / 2, topPos + 131, true, 256, 256);

//        builder.drawSlot(this, matrixStack, leftPos + imageWidth / 2 - 9, topPos + 30, 256, 256);
//        builder.drawSlot(this, matrixStack, leftPos + imageWidth / 2 - 9, topPos + 80, 256, 256);
    }

//    @Override
//    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
//    {
//        this.font.func_238422_b_(matrixStack, this.title.func_241878_f(), 60.0F, 6.0F, 4210752);
//        this.font.func_238422_b_(matrixStack, this.playerInventory.getDisplayName().func_241878_f(), 14.0F, (float) (this.ySize - 100), 4210752);
//
//        int amount = 0;
//        String name = "Empty";
//
//        if (getTank().getFluid() != null)
//        {
//            amount = getTank().getFluidAmount();
//            name = getTank().getFluid().getDisplayName().getString();
//        }
//
//        builder.drawBigBlueBar(this, matrixStack, 36, 56, amount, getTank().getCapacity(), mouseX - guiLeft, mouseY - guiTop, "", "Fluid Type: " + name, amount + " mb " + name, 256, 256);
//    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
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

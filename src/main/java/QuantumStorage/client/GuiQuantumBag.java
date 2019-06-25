//package QuantumStorage.client;
//
//import QuantumStorage.inventory.ContainerQuantumBag;
//import net.minecraft.client.gui.screen.inventory.ContainerScreen;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TextComponent;
//import net.minecraftforge.items.IItemHandlerModifiable;
//
//public class GuiQuantumBag extends ContainerScreen
//{
//    public static GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();
//
//    public GuiQuantumBag(PlayerEntity player, IItemHandlerModifiable invBag)
//    {
//        super(new ContainerQuantumBag(player, invBag), player.inventory, new TextComponent() {
//            @Override
//            public String getUnformattedComponentText() {
//                return null;
//            }
//
//            @Override
//            public ITextComponent shallowCopy() {
//                return null;
//            }
//        });
//        this.xSize = 250;
//        this.ySize = 240;
//    }
//
//    @Override
//    protected void init()
//    {
//        super.init();
//    }
//
//    @Override
//    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
//    {
////        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
////        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 150, true);
////
////        int i = 0;
////        for (int y = 0; y < 7; y++)
////        {
////            for (int x = 0; x < 13; x++)
////            {
////                i++;
////                builder.drawSlot(this, guiLeft + 7 + x * 18, guiTop + 10 + y * 18);
////            }
////        }
//    }
//
////    @Override
////    public void drawScreen(int mouseX, int mouseY, float partialTicks)
////    {
////        this.drawDefaultBackground();
////
////        super.drawScreen(mouseX, mouseY, partialTicks);
////
////        this.renderHoveredToolTip(mouseX, mouseY);
////    }
////
//}

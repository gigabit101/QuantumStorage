package net.gigabit101.quantumstorage.client;

import net.creeperhost.polylib.client.modulargui.ModularGui;
import net.creeperhost.polylib.client.modulargui.elements.GuiElement;
import net.creeperhost.polylib.client.modulargui.elements.GuiRectangle;
import net.creeperhost.polylib.client.modulargui.elements.GuiSlots;
import net.creeperhost.polylib.client.modulargui.elements.GuiText;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerGuiProvider;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerScreenAccess;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Align;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint;
import net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam;
import net.gigabit101.quantumstorage.menu.QuantumStorageUnitMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class QuantumStorageUnitGui extends ContainerGuiProvider<QuantumStorageUnitMenu> {
    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;

    @Override
    public void buildGui(ModularGui gui, ContainerScreenAccess<QuantumStorageUnitMenu> screenAccess) {
        QuantumStorageUnitMenu menu = screenAccess.getMenu();
        gui.setGuiTitle(Component.translatable("gui.quantumstorage.quantum_storage_unit"));
        gui.initStandardGui(WIDTH, HEIGHT);
        gui.renderScreenBackground(true);

        GuiElement<?> root = gui.getRoot();
        fill(root, 0, 0, WIDTH, HEIGHT, 0xE0C6C6C6, 0xFF373737);
        fill(root, 6, 6, WIDTH - 12, 56, 0xFFE2E2E2, 0xFF555555);
        fill(root, 6, 72, WIDTH - 12, 88, 0xFFD6D6D6, 0xFF555555);

        text(root, 10, 8, 156, 10, Component.translatable("gui.quantumstorage.quantum_storage_unit"), 0xFF303030);
        text(root, 10, 24, 156, 10, () -> storedName(menu), 0xFF303030);
        text(root, 10, 38, 156, 10, () -> Component.translatable("gui.quantumstorage.stored_count", formatCount(menu.storedCount.get())), 0xFF303030);
        text(root, 8, 62, 160, 10, Component.translatable("container.inventory"), 0xFF303030);

        GuiSlots.singleSlot(root, screenAccess, menu.storageSlots, 0)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), 116))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 25));
        GuiSlots.singleSlot(root, screenAccess, menu.storageSlots, 1)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), 142))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 25));

        new GuiSlots(root, screenAccess, menu.playerSlots, 9)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), 7))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 83));
        new GuiSlots(root, screenAccess, menu.hotbarSlots, 9)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), 7))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 141));
    }

    private static void fill(GuiElement<?> root, int left, int top, int width, int height, int fill, int border) {
        new GuiRectangle(root)
                .rectangle(fill, border)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), left))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), top))
                .constrain(GeoParam.WIDTH, Constraint.literal(width))
                .constrain(GeoParam.HEIGHT, Constraint.literal(height));
    }

    private static void text(GuiElement<?> root, int left, int top, int width, int height, Component text, int color) {
        text(root, left, top, width, height, () -> text, color);
    }

    private static void text(GuiElement<?> root, int left, int top, int width, int height, java.util.function.Supplier<Component> text, int color) {
        new GuiText(root, text)
                .setAlignment(Align.MIN)
                .setShadow(false)
                .setTextColour(color)
                .setTrim(true)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), left))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), top))
                .constrain(GeoParam.WIDTH, Constraint.literal(width))
                .constrain(GeoParam.HEIGHT, Constraint.literal(height));
    }

    private static Component storedName(QuantumStorageUnitMenu menu) {
        ItemStack stack = menu.storedStack.get();
        return stack.isEmpty() ? Component.translatable("gui.quantumstorage.empty") : stack.getHoverName();
    }

    private static String formatCount(long count) {
        return String.format("%,d", count);
    }
}

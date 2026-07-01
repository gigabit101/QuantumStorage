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
import net.gigabit101.quantumstorage.menu.StorageCrateMenu;
import net.minecraft.network.chat.Component;

public class StorageCrateGui extends ContainerGuiProvider<StorageCrateMenu> {
    @Override
    public void buildGui(ModularGui gui, ContainerScreenAccess<StorageCrateMenu> screenAccess) {
        StorageCrateMenu menu = screenAccess.getMenu();
        int width = Math.max(176, menu.columns * 18 + 14);
        int crateHeight = menu.rows * 18;
        int playerTop = 24 + crateHeight + 13;
        int height = playerTop + 83;

        gui.setGuiTitle(Component.translatable("gui.quantumstorage.storage_crate"));
        gui.initStandardGui(width, height);
        gui.renderScreenBackground(true);

        GuiElement<?> root = gui.getRoot();
        fill(root, 0, 0, width, height, 0xE0C6C6C6, 0xFF373737);
        fill(root, 6, 6, width - 12, crateHeight + 18, 0xFFE2E2E2, 0xFF555555);
        fill(root, 6, playerTop - 11, width - 12, 88, 0xFFD6D6D6, 0xFF555555);

        text(root, 8, 8, width - 16, 10, Component.translatable("gui.quantumstorage.storage_crate"), 0xFF303030);
        text(root, 8, playerTop - 21, width - 16, 10, Component.translatable("container.inventory"), 0xFF303030);

        int crateLeft = (width - menu.columns * 18) / 2;
        new GuiSlots(root, screenAccess, menu.crateSlots, menu.columns)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), crateLeft))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 24));

        int playerLeft = (width - 162) / 2;
        new GuiSlots(root, screenAccess, menu.playerSlots, 9)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), playerLeft))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), playerTop));
        new GuiSlots(root, screenAccess, menu.hotbarSlots, 9)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), playerLeft))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), playerTop + 58));
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
}

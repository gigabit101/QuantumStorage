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
import net.gigabit101.quantumstorage.block.entity.MultiblockStorageBlockEntity;
import net.gigabit101.quantumstorage.menu.MultiblockStorageMenu;
import net.minecraft.network.chat.Component;

public class MultiblockStorageGui extends ContainerGuiProvider<MultiblockStorageMenu> {
    private static final int WIDTH = 250;
    private static final int HEIGHT = 240;

    @Override
    public void buildGui(ModularGui gui, ContainerScreenAccess<MultiblockStorageMenu> screenAccess) {
        gui.setGuiTitle(Component.translatable("gui.quantumstorage.multistorage"));
        gui.initStandardGui(WIDTH, HEIGHT);
        gui.renderScreenBackground(true);

        GuiElement<?> root = gui.getRoot();
        fill(root, 0, 0, WIDTH, HEIGHT, 0xE0C6C6C6, 0xFF373737);
        fill(root, 6, 6, WIDTH - 12, 124, 0xFFE2E2E2, 0xFF555555);
        fill(root, 6, 140, WIDTH - 12, 94, 0xFFD6D6D6, 0xFF555555);

        text(root, 10, 8, WIDTH - 20, 10, Component.translatable("gui.quantumstorage.multistorage"), 0xFF303030);
        text(root, 10, 130, WIDTH - 20, 10, Component.translatable("container.inventory"), 0xFF303030);

        new GuiSlots(root, screenAccess, screenAccess.getMenu().storageSlots, MultiblockStorageBlockEntity.COLUMNS)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), 8))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 20));

        new GuiSlots(root, screenAccess, screenAccess.getMenu().playerSlots, 9)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), 44))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 151));
        new GuiSlots(root, screenAccess, screenAccess.getMenu().hotbarSlots, 9)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), 44))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 209));
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

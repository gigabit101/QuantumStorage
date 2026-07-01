package net.gigabit101.quantumstorage.client;

import net.creeperhost.polylib.client.modulargui.ModularGui;
import net.creeperhost.polylib.client.modulargui.elements.GuiElement;
import net.creeperhost.polylib.client.modulargui.elements.GuiFluidTank;
import net.creeperhost.polylib.client.modulargui.elements.GuiRectangle;
import net.creeperhost.polylib.client.modulargui.elements.GuiSlots;
import net.creeperhost.polylib.client.modulargui.elements.GuiText;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerGuiProvider;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerScreenAccess;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Align;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint;
import net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam;
import net.creeperhost.polylib.inventory.fluid.FluidManager;
import net.creeperhost.polylib.inventory.fluid.PolyFluidStack;
import net.gigabit101.quantumstorage.block.entity.QuantumTankBlockEntity;
import net.gigabit101.quantumstorage.menu.QuantumTankMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

public class QuantumTankGui extends ContainerGuiProvider<QuantumTankMenu> {
    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;

    @Override
    public void buildGui(ModularGui gui, ContainerScreenAccess<QuantumTankMenu> screenAccess) {
        QuantumTankMenu menu = screenAccess.getMenu();
        gui.setGuiTitle(Component.translatable("gui.quantumstorage.quantum_tank"));
        gui.initStandardGui(WIDTH, HEIGHT);
        gui.renderScreenBackground(true);

        GuiElement<?> root = gui.getRoot();
        fill(root, 0, 0, WIDTH, HEIGHT, 0xE0C6C6C6, 0xFF373737);
        fill(root, 6, 6, WIDTH - 12, 56, 0xFFE2E2E2, 0xFF555555);
        fill(root, 6, 72, WIDTH - 12, 88, 0xFFD6D6D6, 0xFF555555);

        text(root, 34, 8, 98, 10, Component.translatable("gui.quantumstorage.quantum_tank"), 0xFF303030);
        text(root, 34, 24, 98, 10, () -> fluidName(menu), 0xFF303030);
        text(root, 34, 38, 98, 10, () -> fluidAmount(menu), 0xFF303030);
        text(root, 8, 62, 160, 10, Component.translatable("container.inventory"), 0xFF303030);

        new GuiFluidTank(root)
                .setCapacity(QuantumTankBlockEntity.CAPACITY)
                .setFluidStack(menu.storedFluid::get)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), 10))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 12))
                .constrain(GeoParam.WIDTH, Constraint.literal(18))
                .constrain(GeoParam.HEIGHT, Constraint.literal(44));

        GuiSlots.singleSlot(root, screenAccess, menu.tankSlots, 0)
                .constrain(GeoParam.LEFT, Constraint.relative(root.get(GeoParam.LEFT), 116))
                .constrain(GeoParam.TOP, Constraint.relative(root.get(GeoParam.TOP), 25));
        GuiSlots.singleSlot(root, screenAccess, menu.tankSlots, 1)
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

    private static Component fluidName(QuantumTankMenu menu) {
        PolyFluidStack stack = menu.storedFluid.get();
        return stack.isEmpty()
                ? Component.translatable("gui.quantumstorage.empty")
                : Component.literal(BuiltInRegistries.FLUID.getKey(stack.getFluid()).toString());
    }

    private static Component fluidAmount(QuantumTankMenu menu) {
        long amount = menu.storedFluid.get().getAmount() / FluidManager.MILLIBUCKET;
        return Component.translatable("gui.quantumstorage.fluid_stored", String.format("%,d", amount));
    }
}

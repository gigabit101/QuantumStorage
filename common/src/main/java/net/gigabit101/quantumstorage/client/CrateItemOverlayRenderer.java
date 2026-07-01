package net.gigabit101.quantumstorage.client;

import net.gigabit101.quantumstorage.item.CrateItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public final class CrateItemOverlayRenderer {
    private static final float OVERLAY_SCALE = 0.75F;
    private static final float OVERLAY_OFFSET = (16F - (16F * OVERLAY_SCALE)) / 2F;

    private CrateItemOverlayRenderer() {
    }

    public static boolean render(GuiGraphicsExtractor graphics, Font font, ItemStack stack, int x, int y) {
        Optional<HolderLookup.Provider> registries = registryAccess();
        if (registries.isEmpty()) {
            return false;
        }

        Optional<ItemStack> storedStack = CrateItem.getStoredStack(stack, registries.get());
        if (storedStack.isEmpty()) {
            return false;
        }

        ItemStack overlay = storedStack.get().copy();
        overlay.setCount(1);
        if (overlay.isEmpty() || ItemStack.isSameItemSameComponents(overlay, stack)) {
            return false;
        }

        graphics.pose().pushMatrix();
        graphics.pose().translate(x + OVERLAY_OFFSET, y + OVERLAY_OFFSET);
        graphics.pose().scale(OVERLAY_SCALE, OVERLAY_SCALE);
        graphics.fakeItem(overlay, 0, 0);
        graphics.pose().popMatrix();
        return true;
    }

    private static Optional<HolderLookup.Provider> registryAccess() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            return Optional.of(minecraft.level.registryAccess());
        }
        if (minecraft.player != null) {
            return Optional.of(minecraft.player.registryAccess());
        }
        if (minecraft.getConnection() != null) {
            return Optional.of(minecraft.getConnection().registryAccess());
        }
        return Optional.empty();
    }
}

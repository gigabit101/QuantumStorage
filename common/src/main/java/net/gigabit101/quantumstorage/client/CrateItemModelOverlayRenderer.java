package net.gigabit101.quantumstorage.client;

import net.gigabit101.quantumstorage.item.CrateItem;
import net.gigabit101.quantumstorage.mixin.client.ItemStackRenderStateAccessor;
import net.gigabit101.quantumstorage.mixin.client.LayerRenderStateAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public final class CrateItemModelOverlayRenderer {
    private static final float OVERLAY_SCALE = 0.72F;
    private static final float OVERLAY_FACE_OFFSET = 0.035F;
    private static boolean appendingOverlay;

    private CrateItemModelOverlayRenderer() {
    }

    public static void appendStoredItemLayers(ItemStackRenderState output, ItemStack crateStack, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable Level level, @Nullable ItemOwner owner, int seed) {
        if (appendingOverlay) {
            return;
        }
        if (displayContext == ItemDisplayContext.GUI) {
            return;
        }

        Optional<HolderLookup.Provider> registries = registryAccess(level, owner);
        if (registries.isEmpty()) {
            return;
        }

        Optional<ItemStack> storedStack = CrateItem.getStoredStack(crateStack, registries.get());
        if (storedStack.isEmpty()) {
            return;
        }

        ItemStack overlay = storedStack.get().copy();
        overlay.setCount(1);
        if (overlay.isEmpty() || ItemStack.isSameItemSameComponents(overlay, crateStack)) {
            return;
        }

        ItemStackRenderStateAccessor outputAccessor = (ItemStackRenderStateAccessor) output;
        int firstOverlayLayer = outputAccessor.quantumstorage$getActiveLayerCount();

        appendingOverlay = true;
        try {
            resolver.appendItemLayers(output, overlay, displayContext, level, owner, seed + 991);
        } finally {
            appendingOverlay = false;
        }

        int lastOverlayLayer = outputAccessor.quantumstorage$getActiveLayerCount();
        if (lastOverlayLayer <= firstOverlayLayer) {
            return;
        }

        Matrix4f overlayTransform = overlayTransform();
        ItemStackRenderState.LayerRenderState[] layers = outputAccessor.quantumstorage$getLayers();
        for (int i = firstOverlayLayer; i < lastOverlayLayer; i++) {
            Matrix4f currentTransform = ((LayerRenderStateAccessor) layers[i]).quantumstorage$getLocalTransform();
            Matrix4f originalTransform = new Matrix4f(currentTransform);
            currentTransform.set(overlayTransform).mul(originalTransform);
        }
    }

    private static Matrix4f overlayTransform() {
        float centerOffset = (1F - OVERLAY_SCALE) / 2F;
        return new Matrix4f()
                .translate(centerOffset, centerOffset, OVERLAY_FACE_OFFSET)
                .scale(OVERLAY_SCALE);
    }

    private static Optional<HolderLookup.Provider> registryAccess(@Nullable Level level, @Nullable ItemOwner owner) {
        if (level != null) {
            return Optional.of(level.registryAccess());
        }
        if (owner != null) {
            return Optional.of(owner.level().registryAccess());
        }

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

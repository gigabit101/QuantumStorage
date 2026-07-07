package net.gigabit101.quantumstorage.client;

import net.gigabit101.quantumstorage.item.CrateItem;
import net.gigabit101.quantumstorage.mixin.client.ItemStackRenderStateAccessor;
import net.gigabit101.quantumstorage.mixin.client.LayerRenderStateAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.cuboid.ItemTransform;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CrateItemModelOverlayRenderer {
    private static final float OVERLAY_SCALE = 0.50F;
    private static final float OVERLAY_FACE_OFFSET = 0.035F;
    private static final float MIN_FACE_AREA = 0.25F;
    private static boolean appendingOverlay;

    private CrateItemModelOverlayRenderer() {
    }

    public static void appendStoredItemLayers(ItemStackRenderState output, ItemStack crateStack, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable Level level, @Nullable ItemOwner owner, int seed) {
        if (appendingOverlay) {
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
        if (outputAccessor.quantumstorage$getActiveLayerCount() <= 0) {
            return;
        }

        ItemStackRenderState.LayerRenderState[] layers = outputAccessor.quantumstorage$getLayers();
        LayerRenderStateAccessor crateLayer = (LayerRenderStateAccessor) layers[0];
        Optional<BakedQuad.MaterialInfo> overlayMaterial = overlayMaterial(overlay, resolver, level, owner, seed);
        if (overlayMaterial.isEmpty()) {
            return;
        }
        output.appendModelIdentityElement(ItemStack.hashItemAndComponents(overlay));
        output.appendModelIdentityElement(overlayMaterial.get().sprite().contents().name());

        List<BakedQuad> overlayQuads = overlayQuads(crateLayer.quantumstorage$getQuads(), overlayMaterial.get());
        if (overlayQuads.isEmpty()) {
            return;
        }

        ItemTransform crateItemTransform = crateLayer.quantumstorage$getItemTransform();
        Matrix4f crateLocalTransform = new Matrix4f(crateLayer.quantumstorage$getLocalTransform());
        ItemStackRenderState.LayerRenderState overlayLayer = output.newLayer();
        overlayLayer.setItemTransform(crateItemTransform);
        overlayLayer.setLocalTransform(crateLocalTransform);
        overlayLayer.prepareQuadList().addAll(overlayQuads);
    }

    private static Optional<BakedQuad.MaterialInfo> overlayMaterial(ItemStack overlay, ItemModelResolver resolver, @Nullable Level level, @Nullable ItemOwner owner, int seed) {
        ItemStackRenderState sampleState = new ItemStackRenderState();
        appendingOverlay = true;
        try {
            resolver.appendItemLayers(sampleState, overlay, ItemDisplayContext.GUI, level, owner, seed + 991);
        } finally {
            appendingOverlay = false;
        }

        ItemStackRenderStateAccessor sampleAccessor = (ItemStackRenderStateAccessor) sampleState;
        ItemStackRenderState.LayerRenderState[] sampleLayers = sampleAccessor.quantumstorage$getLayers();
        for (int i = 0; i < sampleAccessor.quantumstorage$getActiveLayerCount(); i++) {
            for (BakedQuad quad : ((LayerRenderStateAccessor) sampleLayers[i]).quantumstorage$getQuads()) {
                return Optional.of(quad.materialInfo());
            }
        }
        return Optional.empty();
    }

    private static List<BakedQuad> overlayQuads(List<BakedQuad> crateQuads, BakedQuad.MaterialInfo materialInfo) {
        List<BakedQuad> result = new ArrayList<>();
        for (BakedQuad crateQuad : crateQuads) {
            if (faceArea(crateQuad) >= MIN_FACE_AREA) {
                result.add(insetQuad(crateQuad, materialInfo));
            }
        }
        return result;
    }

    private static BakedQuad insetQuad(BakedQuad source, BakedQuad.MaterialInfo materialInfo) {
        Vector3f center = new Vector3f(source.position0())
                .add(source.position1())
                .add(source.position2())
                .add(source.position3())
                .mul(0.25F);
        Vector3f normalOffset = source.direction().getUnitVec3f().mul(OVERLAY_FACE_OFFSET, new Vector3f());
        Vector3f p0 = inset(source.position0(), center, normalOffset);
        Vector3f p1 = inset(source.position1(), center, normalOffset);
        Vector3f p2 = inset(source.position2(), center, normalOffset);
        Vector3f p3 = inset(source.position3(), center, normalOffset);
        float u0 = materialInfo.sprite().getU(0F);
        float u1 = materialInfo.sprite().getU(1F);
        float v0 = materialInfo.sprite().getV(0F);
        float v1 = materialInfo.sprite().getV(1F);
        return new BakedQuad(
                p0,
                p1,
                p2,
                p3,
                UVPair.pack(u0, v0),
                UVPair.pack(u0, v1),
                UVPair.pack(u1, v1),
                UVPair.pack(u1, v0),
                source.direction(),
                materialInfo
        );
    }

    private static Vector3f inset(Vector3fc position, Vector3f center, Vector3f normalOffset) {
        return new Vector3f(center)
                .add(new Vector3f(position).sub(center).mul(OVERLAY_SCALE))
                .add(normalOffset);
    }

    private static float faceArea(BakedQuad quad) {
        Vector3f edgeA = new Vector3f(quad.position1()).sub(quad.position0());
        Vector3f edgeB = new Vector3f(quad.position2()).sub(quad.position0());
        return edgeA.cross(edgeB).length();
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

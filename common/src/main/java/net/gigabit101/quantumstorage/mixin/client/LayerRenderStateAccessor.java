package net.gigabit101.quantumstorage.mixin.client;

import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.cuboid.ItemTransform;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public interface LayerRenderStateAccessor {
    @Accessor("quads")
    List<BakedQuad> quantumstorage$getQuads();

    @Accessor("localTransform")
    Matrix4f quantumstorage$getLocalTransform();

    @Accessor("itemTransform")
    ItemTransform quantumstorage$getItemTransform();
}

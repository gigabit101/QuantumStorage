package net.gigabit101.quantumstorage.mixin.client;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStackRenderState.class)
public interface ItemStackRenderStateAccessor {
    @Accessor("activeLayerCount")
    int quantumstorage$getActiveLayerCount();

    @Accessor("layers")
    ItemStackRenderState.LayerRenderState[] quantumstorage$getLayers();
}

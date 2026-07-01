package net.gigabit101.quantumstorage.mixin.client;

import net.gigabit101.quantumstorage.client.CrateItemModelOverlayRenderer;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
    @Inject(method = "appendItemLayers", at = @At("TAIL"))
    private void quantumstorage$appendCrateStoredItemLayer(ItemStackRenderState output, ItemStack item, ItemDisplayContext displayContext, @Nullable Level level, @Nullable ItemOwner owner, int seed, CallbackInfo ci) {
        if (item.is(QuantumStorageContent.CRATE_ITEM.get())) {
            CrateItemModelOverlayRenderer.appendStoredItemLayers(output, item, (ItemModelResolver) (Object) this, displayContext, level, owner, seed);
        }
    }
}

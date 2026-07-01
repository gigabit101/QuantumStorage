package net.gigabit101.quantumstorage.item;

import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;

import java.util.Optional;
import java.util.function.Consumer;

public class CrateItem extends Item {
    private static final String STORED_STACK_KEY = "stored_stack";

    public CrateItem(Properties properties) {
        super(properties);
    }

    public static boolean isEmptyCrate(ItemStack stack) {
        return stack.is(QuantumStorageContent.CRATE_ITEM.get()) && getCustomData(stack).isEmpty();
    }

    public static boolean isFilledCrate(ItemStack stack) {
        return stack.is(QuantumStorageContent.CRATE_ITEM.get()) && getCustomData(stack).isPresent();
    }

    public static ItemStack createFilledCrate(ItemStack storedStack, HolderLookup.Provider registries) {
        if (storedStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack crate = new ItemStack(QuantumStorageContent.CRATE_ITEM.get());
        TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, registries);
        output.store(STORED_STACK_KEY, ItemStack.CODEC, storedStack.copy());
        crate.set(DataComponents.CUSTOM_DATA, CustomData.of(output.buildResult()));
        return crate;
    }

    public static Optional<ItemStack> getStoredStack(ItemStack crateStack, HolderLookup.Provider registries) {
        return getCustomData(crateStack)
                .flatMap(customData -> {
                    ValueInput input = TagValueInput.create(ProblemReporter.DISCARDING, registries, customData.copyTag());
                    return input.read(STORED_STACK_KEY, ItemStack.CODEC);
                })
                .filter(stack -> !stack.isEmpty());
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack crate = player.getItemInHand(usedHand);
        Optional<ItemStack> storedStack = getStoredStack(crate, player.registryAccess());
        if (storedStack.isEmpty()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            ItemStack unpacked = storedStack.get().copy();
            if (!player.addItem(unpacked)) {
                player.drop(unpacked, false);
            }
            crate.shrink(1);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        getStoredStack(stack, context.registries()).ifPresent(storedStack -> tooltip.accept(Component.translatable(
                "tooltip.quantumstorage.crate_item",
                storedStack.getCount(),
                storedStack.getHoverName()
        ).withStyle(ChatFormatting.GRAY)));
    }

    private static Optional<CustomData> getCustomData(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData == null || customData.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(customData);
    }
}

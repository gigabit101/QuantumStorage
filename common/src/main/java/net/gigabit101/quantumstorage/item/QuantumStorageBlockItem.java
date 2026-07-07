package net.gigabit101.quantumstorage.item;

import net.creeperhost.polylib.init.DataComps;
import net.creeperhost.polylib.inventory.fluid.FluidManager;
import net.creeperhost.polylib.inventory.fluid.PolyFluidStack;
import net.gigabit101.quantumstorage.block.entity.StorageCrateBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;

import java.util.Optional;
import java.util.function.Consumer;

public class QuantumStorageBlockItem extends BlockItem {
    private static final int MAX_CRATE_TOOLTIP_STACKS = 5;

    private final TooltipType tooltipType;
    private final int crateSlots;

    public QuantumStorageBlockItem(Block block, Properties properties, TooltipType tooltipType) {
        this(block, properties, tooltipType, 0);
    }

    public QuantumStorageBlockItem(Block block, Properties properties, StorageCrateBlockEntity.CrateTier tier) {
        this(block, properties, TooltipType.STORAGE_CRATE, tier.slots());
    }

    private QuantumStorageBlockItem(Block block, Properties properties, TooltipType tooltipType, int crateSlots) {
        super(block, properties.stacksTo(1));
        this.tooltipType = tooltipType;
        this.crateSlots = crateSlots;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);

        CustomData customData = stack.get(DataComps.getItemTileData());
        if (customData == null || customData.isEmpty()) {
            return;
        }

        ValueInput input = TagValueInput.create(ProblemReporter.DISCARDING, context.registries(), customData.copyTag());
        switch (tooltipType) {
            case QUANTUM_STORAGE_UNIT -> appendQuantumStorageUnitTooltip(input, tooltip);
            case QUANTUM_TANK -> appendQuantumTankTooltip(input, tooltip);
            case STORAGE_CRATE -> appendStorageCrateTooltip(input, tooltip);
            case NONE -> {
            }
        }
    }

    private void appendQuantumStorageUnitTooltip(ValueInput input, Consumer<Component> tooltip) {
        if (input.getBooleanOr("render_upgrade", false)) {
            tooltip.accept(Component.translatable("tooltip.quantumstorage.upgrade.render_installed").withStyle(ChatFormatting.DARK_PURPLE));
        }
        if (input.getBooleanOr("void_upgrade", false)) {
            tooltip.accept(Component.translatable("tooltip.quantumstorage.upgrade.void_installed").withStyle(ChatFormatting.DARK_PURPLE));
        }

        long storedCount = input.getLongOr("stored_count", 0L);
        Optional<ItemStack> storedStack = input.read("stored_stack", ItemStack.OPTIONAL_CODEC);
        if (storedCount <= 0 || storedStack.isEmpty() || storedStack.get().isEmpty()) {
            return;
        }

        tooltip.accept(Component.translatable(
                "tooltip.quantumstorage.stored_item",
                format(storedCount),
                storedStack.get().getHoverName()
        ).withStyle(ChatFormatting.GRAY));
    }

    private void appendQuantumTankTooltip(ValueInput input, Consumer<Component> tooltip) {
        if (input.getBooleanOr("infinite_water", false)) {
            tooltip.accept(Component.translatable("tooltip.quantumstorage.upgrade.water_installed").withStyle(ChatFormatting.DARK_PURPLE));
        }

        Optional<ValueInput> tankInput = input.child("tank");
        if (tankInput.isEmpty()) {
            return;
        }

        PolyFluidStack storedFluid = tankInput.get().read("fluid", PolyFluidStack.CODEC).orElse(PolyFluidStack.EMPTY);
        if (storedFluid.isEmpty()) {
            return;
        }

        long milliBuckets = storedFluid.getAmount() / FluidManager.MILLIBUCKET;
        ItemStack bucket = new ItemStack(storedFluid.getFluid().getBucket());
        Component fluidName = bucket.isEmpty() ? Component.literal(storedFluid.getFluid().toString()) : bucket.getHoverName();
        tooltip.accept(Component.translatable(
                "tooltip.quantumstorage.stored_fluid",
                format(milliBuckets),
                fluidName
        ).withStyle(ChatFormatting.GRAY));
    }

    private void appendStorageCrateTooltip(ValueInput input, Consumer<Component> tooltip) {
        Optional<ValueInput> inventoryInput = input.child("inventory");
        if (inventoryInput.isEmpty() || crateSlots <= 0) {
            return;
        }

        NonNullList<ItemStack> items = NonNullList.withSize(crateSlots, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(inventoryInput.get(), items);

        int stacks = 0;
        long itemCount = 0L;
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                stacks++;
                itemCount += item.getCount();
            }
        }
        if (stacks == 0) {
            return;
        }

        tooltip.accept(Component.translatable(
                "tooltip.quantumstorage.crate_summary",
                format(stacks),
                format(itemCount)
        ).withStyle(ChatFormatting.GRAY));

        int shown = 0;
        for (ItemStack item : items) {
            if (item.isEmpty()) {
                continue;
            }
            if (shown >= MAX_CRATE_TOOLTIP_STACKS) {
                break;
            }
            tooltip.accept(Component.translatable(
                    "tooltip.quantumstorage.crate_stack",
                    item.getCount(),
                    item.getHoverName()
            ).withStyle(ChatFormatting.DARK_GRAY));
            shown++;
        }

        if (stacks > shown) {
            tooltip.accept(Component.translatable(
                    "tooltip.quantumstorage.crate_more",
                    format(stacks - shown)
            ).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    private static String format(long value) {
        return String.format("%,d", value);
    }

    public enum TooltipType {
        NONE,
        QUANTUM_STORAGE_UNIT,
        QUANTUM_TANK,
        STORAGE_CRATE
    }
}

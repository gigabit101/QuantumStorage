package net.gigabit101.quantumstorage.item;

import net.gigabit101.quantumstorage.block.entity.QuantumStorageUnitBlockEntity;
import net.gigabit101.quantumstorage.block.entity.QuantumTankBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Consumer;

public class UpgradeItem extends Item {
    private final Type type;

    public UpgradeItem(Type type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockEntity blockEntity = level.getBlockEntity(context.getClickedPos());
        if (blockEntity == null) {
            return InteractionResult.PASS;
        }

        boolean applied = switch (type) {
            case RENDER -> blockEntity instanceof QuantumStorageUnitBlockEntity storageUnit && storageUnit.applyRenderUpgrade();
            case VOID -> blockEntity instanceof QuantumStorageUnitBlockEntity storageUnit && storageUnit.applyVoidUpgrade();
            case CREATIVE -> blockEntity instanceof QuantumStorageUnitBlockEntity storageUnit && storageUnit.applyCreativeUpgrade();
            case WATER -> blockEntity instanceof QuantumTankBlockEntity tank && tank.applyInfiniteWaterUpgrade();
        };

        if (!applied) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            BlockEntity updated = level.getBlockEntity(context.getClickedPos());
            if (updated != null) {
                updated.setChanged();
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        if (Minecraft.getInstance().hasShiftDown()) {
            tooltip.accept(Component.translatable(type.tooltipKey()));
        } else {
            tooltip.accept(Component.translatable("tooltip.quantumstorage.hold_shift").withStyle(ChatFormatting.GRAY));
        }
    }

    public enum Type {
        RENDER("render"),
        VOID("void"),
        CREATIVE("creative"),
        WATER("water");

        private final String id;

        Type(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }

        public String itemId() {
            return "upgrade_" + id;
        }

        public String tooltipKey() {
            return "tooltipitem.quantumstorage.upgrade." + id;
        }
    }
}

package net.gigabit101.quantumstorage.block.entity;

import net.creeperhost.polylib.blocks.InteractableBlock;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.init.DataComps;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.inventory.items.ContainerAccessControl;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.creeperhost.polylib.platform.Services;
import net.gigabit101.quantumstorage.menu.StorageCrateMenu;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StorageCrateBlockEntity extends PolyBlockEntity implements InteractableBlock, WorldlyContainer, PolyInventoryBlock {
    private final BlockInventory inventory;
    private final ContainerAccessControl accessControl;
    private final int[] slots;
    private final CrateTier tier;
    private ItemStack retainedHarvestStack = ItemStack.EMPTY;

    public StorageCrateBlockEntity(BlockPos pos, BlockState state) {
        super(QuantumStorageContent.STORAGE_CRATE_BLOCK_ENTITY.get(), pos, state);
        this.tier = CrateTier.byState(state);
        this.inventory = new BlockInventory(this, tier.slots());
        this.accessControl = new ContainerAccessControl(inventory, 0, tier.slots());
        this.slots = new int[tier.slots()];
        for (int slot = 0; slot < slots.length; slot++) {
            slots[slot] = slot;
        }
    }

    public BlockInventory inventory() {
        return inventory;
    }

    public CrateTier tier() {
        return tier;
    }

    @Override
    public @Nullable Container getContainer(@Nullable Direction side) {
        return accessControl;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return slots;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        return inventory.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return inventory.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return inventory.getItem(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return inventory.removeItem(slot, amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return inventory.removeItemNoUpdate(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.setItem(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return inventory.stillValid(player);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return inventory.canPlaceItem(slot, stack);
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
    }

    @Override
    public InteractionResult onBlockUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            Services.REGISTER_HELPER.openMenu(
                    serverPlayer,
                    new SimpleMenuProvider((syncId, inventory, menuPlayer) -> new StorageCrateMenu(syncId, inventory, this), getDisplayName()),
                    buffer -> buffer.writeBlockPos(pos)
            );
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void writeExtraData(ValueOutput output) {
        inventory.serialize(output.child("inventory"));
    }

    @Override
    public CompoundTag writeToItemStack(HolderLookup.Provider provider, boolean willHarvest) {
        return super.writeToItemStack(provider, willHarvest);
    }

    public void prepareForHarvest(HolderLookup.Provider provider) {
        retainedHarvestStack = ItemStack.EMPTY;

        CompoundTag tag = writeToItemStack(provider, true);
        if (!tag.isEmpty()) {
            retainedHarvestStack = new ItemStack(getBlockState().getBlock(), 1);
            retainedHarvestStack.set(DataComps.getItemTileData(), CustomData.of(tag));
        }

        if (hasCustomName()) {
            if (retainedHarvestStack.isEmpty()) {
                retainedHarvestStack = new ItemStack(getBlockState().getBlock(), 1);
            }
            retainedHarvestStack.set(DataComponents.CUSTOM_NAME, getCustomName());
        }

        inventory.clearContent();
        setChanged();
    }

    public ItemStack consumeRetainedHarvestStack() {
        ItemStack stack = retainedHarvestStack;
        retainedHarvestStack = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public void readExtraData(ValueInput input) {
        inventory.deserialize(input.childOrEmpty("inventory"));
    }

    public enum CrateTier {
        IRON("chest_iron", 9, 4),
        GOLD("chest_gold", 9, 6),
        DIAMOND("chest_diamond", 13, 7),
        QUANTUM("chest_quantum", 13, 14);

        private final String id;
        private final int columns;
        private final int rows;

        CrateTier(String id, int columns, int rows) {
            this.id = id;
            this.columns = columns;
            this.rows = rows;
        }

        public int columns() {
            return columns;
        }

        public int rows() {
            return rows;
        }

        public int slots() {
            return columns * rows;
        }

        public static CrateTier byState(BlockState state) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            String path = id == null ? "" : id.getPath();
            for (CrateTier tier : values()) {
                if (tier.id.equals(path)) {
                    return tier;
                }
            }
            return IRON;
        }
    }
}

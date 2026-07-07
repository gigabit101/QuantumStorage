package net.gigabit101.quantumstorage.multiblock;

import net.creeperhost.polylib.mulitblock.IMultiblockPart;
import net.creeperhost.polylib.mulitblock.MultiblockControllerBase;
import net.creeperhost.polylib.mulitblock.MultiblockValidationException;
import net.creeperhost.polylib.mulitblock.rectangular.RectangularMultiblockControllerBase;
import net.gigabit101.quantumstorage.block.entity.MultiblockStorageBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;

public class MultiblockStorageController extends RectangularMultiblockControllerBase {
    private static final int MIN_SIZE = 3;
    private static final int MAX_SIZE = 16;

    private List<MultiblockStorageBlockEntity> storageBlocks = List.of();

    public MultiblockStorageController(Level world) {
        super(world);
    }

    public List<MultiblockStorageBlockEntity> storageBlocks() {
        return storageBlocks;
    }

    public int storageSlots() {
        return storageBlocks.size() * MultiblockStorageBlockEntity.SLOTS_PER_STORAGE;
    }

    public void refreshStorageBlocks() {
        storageBlocks = connectedParts.stream()
                .filter(MultiblockStorageBlockEntity.class::isInstance)
                .map(MultiblockStorageBlockEntity.class::cast)
                .filter(MultiblockStorageBlockEntity::isStorageBlock)
                .sorted(Comparator
                        .comparingInt((MultiblockStorageBlockEntity blockEntity) -> blockEntity.getBlockPos().getY())
                        .thenComparingInt(blockEntity -> blockEntity.getBlockPos().getZ())
                        .thenComparingInt(blockEntity -> blockEntity.getBlockPos().getX()))
                .toList();
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, CompoundTag data) {
    }

    @Override
    protected void onBlockAdded(IMultiblockPart newPart) {
        refreshStorageBlocks();
    }

    @Override
    protected void onBlockRemoved(IMultiblockPart oldPart) {
        refreshStorageBlocks();
    }

    @Override
    protected void onMachineAssembled() {
        refreshStorageBlocks();
    }

    @Override
    protected void onMachineRestored() {
        refreshStorageBlocks();
    }

    @Override
    protected void onMachinePaused() {
    }

    @Override
    protected void onMachineDisassembled() {
        refreshStorageBlocks();
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return MIN_SIZE * MIN_SIZE * MIN_SIZE;
    }

    @Override
    protected int getMaximumXSize() {
        return MAX_SIZE;
    }

    @Override
    protected int getMaximumZSize() {
        return MAX_SIZE;
    }

    @Override
    protected int getMaximumYSize() {
        return MAX_SIZE;
    }

    @Override
    protected int getMinimumXSize() {
        return MIN_SIZE;
    }

    @Override
    protected int getMinimumYSize() {
        return MIN_SIZE;
    }

    @Override
    protected int getMinimumZSize() {
        return MIN_SIZE;
    }

    @Override
    protected void isMachineWhole() throws MultiblockValidationException {
        super.isMachineWhole();
        refreshStorageBlocks();
        if (storageBlocks.isEmpty()) {
            throw new MultiblockValidationException("Multiblock must contain at least one storage block.");
        }
    }

    @Override
    protected void onAssimilate(MultiblockControllerBase assimilated) {
        refreshStorageBlocks();
    }

    @Override
    protected void onAssimilated(MultiblockControllerBase assimilator) {
        storageBlocks = List.of();
    }

    @Override
    protected boolean updateServer() {
        return false;
    }

    @Override
    protected void updateClient() {
    }

    @Override
    public void writeToNBT(CompoundTag data) {
    }

    @Override
    public void readFromNBT(CompoundTag data) {
    }

    @Override
    public void formatDescriptionPacket(CompoundTag data) {
        writeToNBT(data);
    }

    @Override
    public void decodeDescriptionPacket(CompoundTag data) {
        readFromNBT(data);
    }
}

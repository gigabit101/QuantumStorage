package QuantumStorage.multiblock;

import net.gigabit101.quantumstorage.init.ModBlocks;
import net.minecraft.block.Block;
import reborncore.common.itemblock.ItemBlockBase;

public class ItemMultiBlockStorage extends ItemBlockBase
{
    public ItemMultiBlockStorage(Block block)
    {
        super(ModBlocks.MULTIBLOCK_STORAGE, ModBlocks.MULTIBLOCK_STORAGE, BlockMultiStorage.types);
    }
}

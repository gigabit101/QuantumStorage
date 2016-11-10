package QuantumStorage.items.itemblocks;

import QuantumStorage.block.BlockQChest;
import QuantumStorage.init.ModBlocks;
import net.minecraft.block.Block;
import reborncore.common.itemblock.ItemBlockBase;

/**
 * Created by Gigabit101 on 01/11/2016.
 */
public class ItemBlockQChest extends ItemBlockBase
{
    public ItemBlockQChest(Block block)
    {
        super(ModBlocks.QChest, ModBlocks.QChest, BlockQChest.types);
    }
}

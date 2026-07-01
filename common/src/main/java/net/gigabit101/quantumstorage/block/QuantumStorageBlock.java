package net.gigabit101.quantumstorage.block;

import net.creeperhost.polylib.blocks.PolyBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class QuantumStorageBlock extends PolyBlock {
    public QuantumStorageBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public static BlockBehaviour.Properties properties(BlockBehaviour.Properties properties) {
        return properties
                .mapColor(MapColor.METAL)
                .requiresCorrectToolForDrops()
                .strength(2.0F, 100.0F)
                .sound(SoundType.METAL);
    }
}

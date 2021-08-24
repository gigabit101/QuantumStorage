package net.gigabit101.quantumstorage.init;

import dev.architectury.hooks.block.BlockEntityHooks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.blocks.BlockQuantumStorageUnit;
import net.gigabit101.quantumstorage.tiles.TileQuantumStorageUnit;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(QuantumStorage.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(QuantumStorage.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<Block> QUANTUM_STORAGE_UNIT = BLOCKS.register("qsu",
            BlockQuantumStorageUnit::new);

    public static final RegistrySupplier<BlockEntityType<TileQuantumStorageUnit>> QUANTUM_STORAGE_UNIT_TILE = TILES.register("qsu", () ->
            BlockEntityHooks.builder(TileQuantumStorageUnit::new, QUANTUM_STORAGE_UNIT.get()).build(null));
}

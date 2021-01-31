package net.gigabit101.quantumstorage.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.util.ResourceLocation;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GeneratorLoot extends LootTableProvider
{
    public GeneratorLoot(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(Blocks::new, LootParameterSets.BLOCK));
    }

    private static class Blocks extends BlockLootTables {
        @Override
        protected void addTables() {
            LootPool.Builder builder = LootPool.builder()
                    .name(QSBlocks.CONTROLLER.get().getRegistryName().toString())
                    .rolls(ConstantRange.of(1))
                    .acceptCondition(SurvivesExplosion.builder())
                    .addEntry(ItemLootEntry.builder(QSBlocks.CONTROLLER.get()).acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY)));

            this.registerLootTable(QSBlocks.CONTROLLER.get(), LootTable.builder().addLootPool(builder));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ImmutableList.of(QSBlocks.CONTROLLER.get());
        }
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.func_227508_a_(validationtracker, name, table));
    }
}

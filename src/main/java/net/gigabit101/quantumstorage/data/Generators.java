package net.gigabit101.quantumstorage.data;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = QuantumStorage.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Generators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        if (event.includeServer()) {
//            generator.addProvider(new GeneratorRecipes(generator));
            generator.addProvider(new GeneratorLoot(generator));
        }

        if (event.includeClient()) {
//            generator.addProvider(new GeneratorLanguage(generator));
//            generator.addProvider(new GeneratorBlockStates(generator, event.getExistingFileHelper()));
//            generator.addProvider(new GeneratorItemModels(generator, event.getExistingFileHelper()));
        }
    }
}

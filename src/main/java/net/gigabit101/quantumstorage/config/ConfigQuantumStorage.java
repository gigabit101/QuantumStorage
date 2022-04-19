package net.gigabit101.quantumstorage.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

/**
 * Created by Gigabit101 on 27/03/2017.
 */
@Mod.EventBusSubscriber
public class ConfigQuantumStorage
{
    public static final String CATEGORY_GENERAL = "general";
    
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;
    
    public static ForgeConfigSpec.ConfigValue<Boolean> SPECIAL_RENDER_QSU;
    public static ForgeConfigSpec.ConfigValue<Boolean> SPECIAL_RENDER_TANK;
    
    static
    {
        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        COMMON_BUILDER.pop();
    
        CLIENT_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        CLIENT_BUILDER.pop();
        
        SPECIAL_RENDER_QSU = CLIENT_BUILDER.comment("Set to true to disable the render of Quantum Storage Units")
                .define("disableRenderQuantumStorageUnit",  false);
        SPECIAL_RENDER_TANK = CLIENT_BUILDER.comment("Set to true to disable the render of Quantum Tank")
                .define("disableRenderQuantumTank",  false);
        
        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
    
    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        
        configData.load();
        spec.setConfig(configData);
    }
}

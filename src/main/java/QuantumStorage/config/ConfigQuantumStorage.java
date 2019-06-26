package QuantumStorage.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Created by Gigabit101 on 27/03/2017.
 */
public class ConfigQuantumStorage
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec spec = BUILDER.build();
    public static final ConfigQuantumStorage INSTANCE = new ConfigQuantumStorage(BUILDER);
    
    public ForgeConfigSpec.ConfigValue<Boolean> specialRender;
    
    ConfigQuantumStorage(ForgeConfigSpec.Builder builder)
    {
        builder.push("General");
        specialRender = builder.comment("Set to false to disable the QuantumStorage units special render").translation("config.quantumstorage.specialrender").define("specialRender", true);
        builder.build();
        builder.pop();
    }
}

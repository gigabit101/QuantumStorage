package QuantumStorage.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Gigabit101 on 27/03/2017.
 */
public class ConfigQuantumStorage
{
    private static ConfigQuantumStorage INSTANCE = null;
    public static String CATEGORY_GENERAL = "general";
    public static boolean blameWyld;


    public static Configuration config;

    private ConfigQuantumStorage(File configfile)
    {
        config = new Configuration(configfile);
        config.load();

        ConfigQuantumStorage.Configs();

        config.save();
    }

    public static ConfigQuantumStorage init(File configfile)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new ConfigQuantumStorage(configfile);
        }
        else
        {
            throw new IllegalStateException("Cannot init QuantumStorage config twice");
        }
        return INSTANCE;
    }

    public static ConfigQuantumStorage instance()
    {
        if (INSTANCE == null)
        {
            throw new IllegalStateException("Instance of QuantumStorage Config requested before initialization");
        }
        return INSTANCE;
    }

    public static void Configs()
    {
        blameWyld =  config.get(CATEGORY_GENERAL, "WYLD SAID I NEEED A CONFIG FILE", true).getBoolean();

        if(config.hasChanged())
        {
            config.save();
        }
    }
}

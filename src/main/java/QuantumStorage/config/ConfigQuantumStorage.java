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
    public static String CATEGORY_RECIPE = "recipe";

    public static boolean disableChests;
    public static boolean disableQuantumTank;
    public static boolean disableQuantumCrafter;
    public static boolean disableQuantumDisk;
    public static boolean disableQuantumStorageUnit;
    public static boolean disableCrateingMachine;
    public static boolean disableBarrel;
    public static boolean disableTrashcan;
    public static boolean disableTrashcanFluid;
    public static boolean disableQuantumBattery;

    public static int defaultDiskTime;

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
        if (INSTANCE == null)
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
        disableChests = config.get(CATEGORY_RECIPE, "disable default recipe for quantum chests", false).getBoolean();
        disableQuantumCrafter = config.get(CATEGORY_RECIPE, "disable default recipe for quantum crafter", false).getBoolean();
        disableQuantumDisk = config.get(CATEGORY_RECIPE, "disable default recipe for quantum disk", false).getBoolean();
        disableQuantumTank = config.get(CATEGORY_RECIPE, "disable default recipe for quantum tank", false).getBoolean();
        disableQuantumStorageUnit = config.get(CATEGORY_RECIPE, "disable default recipe for quantum storage unit", false).getBoolean();
        disableCrateingMachine = config.get(CATEGORY_RECIPE, "disable default recipe for crating machine", false).getBoolean();
        disableBarrel = config.get(CATEGORY_RECIPE, "disable default recipe for quantum barrel", false).getBoolean();
        disableTrashcan = config.get(CATEGORY_RECIPE, "disable default recipe for trash can", false).getBoolean();
        disableTrashcanFluid = config.get(CATEGORY_RECIPE, "disable default recipe for fluid trash can", false).getBoolean();

        disableQuantumBattery = config.get(CATEGORY_RECIPE, "disable default recipe for quantum battery", false).getBoolean();

        defaultDiskTime = config.get(CATEGORY_RECIPE, "how long in ticks it takes to craft a quantum disk", 20000).getInt();

        if (config.hasChanged())
        {
            config.save();
        }
    }
}

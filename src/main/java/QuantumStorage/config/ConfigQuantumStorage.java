package QuantumStorage.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigQuantumStorage 
{
	private static ConfigQuantumStorage instance = null;
	public static String CATEGORY_RECIPE = "RECIPE";
	public static String CATEGORY_TWEAKS = "TWEAKS";
	
	public static boolean disableqsumk1;
	public static boolean disableqsumk2;
	public static boolean disableqsumk3;
	public static boolean disableqsumk4;
	
	public static boolean disableTankMk1;
	public static boolean disableTankMk2;
	public static boolean disableTankMk3;
	public static boolean disableTankMk4;
	
	public static boolean disableMk2Upgrade;
	public static boolean disableMk3Upgrade;
	public static boolean disableMk4Upgrade;
	
	public static boolean disableCraftingUpgrade;
	public static boolean disableAutoPickupUpgrade;
	
	public static boolean disableQuantumBag;
	public static boolean disableLinkedBag;
	
	public static int mk1MaxStorage;
	public static int mk2MaxStorage;
	public static int mk3MaxStorage;
	public static int mk4MaxStorage;
	
	public static int mk1TankMaxStorage;
	public static int mk2TankMaxStorage;
	public static int mk3TankMaxStorage;
	public static int mk4TankMaxStorage;
	
	public static Configuration config;
	
    private ConfigQuantumStorage(File configFile) 
    {
        config = new Configuration(configFile);
        config.load();

        ConfigQuantumStorage.Configs();

        config.save();
    }
    
    public static ConfigQuantumStorage initialize(File configFile) 
    {
        if (instance == null)
            instance = new ConfigQuantumStorage(configFile);
        else
            throw new IllegalStateException("Cannot initialize QuantumStorage Config twice");
        return instance;
    }
    
    public static ConfigQuantumStorage instance() 
    {
        if (instance == null) 
        {
            throw new IllegalStateException("Instance of QuantumStorage Config requested before initialization");
        }
        return instance;
    }
    
    public static void Configs() 
    {
        disableqsumk1 = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Unit MK1",
                false,"set to false to remove recipe from QuantumStorage Unit MK1").getBoolean();
        disableqsumk2 = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Unit MK2",
                false,"set to false to remove recipe from QuantumStorage Unit MK2").getBoolean();
        disableqsumk3 = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Unit MK3",
                false,"set to false to remove recipe from QuantumStorage Unit MK3").getBoolean();
        disableqsumk4 = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Unit MK4",
                false,"set to false to remove recipe from QuantumStorage Unit MK4").getBoolean();
        
        disableTankMk1 = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Tank MK1",
                false,"set to false to remove recipe from QuantumStorage Tank MK1").getBoolean();
        disableTankMk2 = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Tank MK2",
                false,"set to false to remove recipe from QuantumStorage Tank MK2").getBoolean();
        disableTankMk3 = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Tank MK3",
                false,"set to false to remove recipe from QuantumStorage Tank MK3").getBoolean();
        disableTankMk4 = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Tank MK4",
                false,"set to false to remove recipe from QuantumStorage Tank MK4").getBoolean();
        
        disableMk2Upgrade= config.get(CATEGORY_RECIPE,"Disable MK2 Upgrade",
                false,"set to false to remove recipe from MK2 upgrade").getBoolean();
        disableMk3Upgrade= config.get(CATEGORY_RECIPE,"Disable MK3 Upgrade",
                false,"set to false to remove recipe from MK3 upgrade").getBoolean();
        disableMk4Upgrade= config.get(CATEGORY_RECIPE,"Disable MK4 Upgrade",
                false,"set to false to remove recipe from MK4 upgrade").getBoolean();
        
        disableCraftingUpgrade= config.get(CATEGORY_RECIPE,"Disable Crafting Card Upgrade",
                false,"set to false to remove recipe from crafting card upgrade").getBoolean();
        disableAutoPickupUpgrade= config.get(CATEGORY_RECIPE,"Disable AutoCrafting Card Upgrade",
                false,"set to false to remove recipe from autocrafting card upgrade").getBoolean();
        
        disableQuantumBag= config.get(CATEGORY_RECIPE,"Disable QuantumBag",
                false,"set to false to remove recipe from quantum bag").getBoolean();
        disableLinkedBag= config.get(CATEGORY_RECIPE,"Disable LinkedBag",
                false,"set to false to remove recipe from linked bag").getBoolean();
        
        mk1MaxStorage= config.get(CATEGORY_TWEAKS,"MK1 Max Storage",
                536870911,"set to change the max storage of the MK1 quantum storage unit").getInt();
        mk2MaxStorage= config.get(CATEGORY_TWEAKS,"MK2 Max Storage",
                1073741823,"set to change the max storage of the MK2 quantum storage unit").getInt();
        mk3MaxStorage= config.get(CATEGORY_TWEAKS,"MK3 Max Storage",
                1610612735,"set to change the max storage of the MK3 quantum storage unit").getInt();
        mk4MaxStorage= config.get(CATEGORY_TWEAKS,"MK4 Max Storage",
        		2147483647,"set to change the max storage of the MK4 quantum storage unit").getInt();
        
        mk1TankMaxStorage= config.get(CATEGORY_TWEAKS,"MK1 Tank Max Storage",
                536870911,"set to change the max storage of the MK1 quantum storage tank").getInt();
        mk2TankMaxStorage= config.get(CATEGORY_TWEAKS,"MK2 Tank Max Storage",
                1073741823,"set to change the max storage of the MK2 quantum storage tank").getInt();
        mk3TankMaxStorage= config.get(CATEGORY_TWEAKS,"MK3 Tank Max Storage",
                1610612735,"set to change the max storage of the MK3 quantum storage tank").getInt();
        mk4TankMaxStorage= config.get(CATEGORY_TWEAKS,"MK4 Tank Max Storage",
        		2147483647,"set to change the max storage of the MK4 quantum storage tank").getInt();
       
        if (config.hasChanged())
            config.save();
    }
}

package QuantumStorage.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigQuantumStorage 
{
	private static ConfigQuantumStorage instance = null;
	public static String CATEGORY_RECIPE = "RECIPE";
	public static String CATEGORY_TWEAKS = "TWEAKS";
	
	public static boolean disableDsu;
	public static int dsuMaxStorage;
	
	public static boolean disableTank;
	public static int tankMaxStorage;
	
	
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
        disableDsu = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Unit MK1",
                false,"set to false to remove recipe from QuantumStorage Unit MK1").getBoolean();
        
        disableTank = config.get(CATEGORY_RECIPE,"Disable QuantumStorage Tank MK1",
                false,"set to false to remove recipe from QuantumStorage Tank MK1").getBoolean();

        
        dsuMaxStorage = config.get(CATEGORY_TWEAKS,"DSU Max Storage",
                536870911,"set to change the max storage of the quantum storage unit").getInt();
        
        tankMaxStorage = config.get(CATEGORY_TWEAKS,"Quantum Tank Max Storage",
                536870911,"set to change the max storage of the quantum storage tank").getInt();
       
        if (config.hasChanged())
            config.save();
    }
}

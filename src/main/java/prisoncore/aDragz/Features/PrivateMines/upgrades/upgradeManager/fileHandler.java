package prisoncore.aDragz.Features.PrivateMines.upgrades.upgradeManager;

import prisoncore.aDragz.main;

import java.io.File;

public class fileHandler {
    //This just creates the files for the first-time-use. Nothing else to be fair
    //Used in the main class hence no callables as there will be no performance loss.
    
    private static final main plugin = main.getPlugin(main.class);
    
    public static void makeFiles() {
        File upgradeFile = new File(plugin.getDataFolder() + "gui/Upgrades.yml");
        File effectsFile = new File(plugin.getDataFolder() + "gui/Effects.yml");
        File enchantGUIFile = new File(plugin.getDataFolder() + "gui/enchantmentGUI.yml");
        File blocksFile = new File(plugin.getDataFolder() + "gui/blocks.yml");
        
        File enchantMainFile = new File(plugin.getDataFolder() + "Configs/enchantmentGUI.yml");
        File private_minesFile = new File(plugin.getDataFolder() + "Configs/private_mine.yml");
        //File rebootFile = new File(plugin.getDataFolder() + "Configs/reboot.yml");
        
        
        createUpgradesFile();
        
        
        createEffectsFile();
        
        createEnchantmentGUIFile();
        
        createBlocksFile();
        
        createEnchantmentsFile();
        
        createPrivateMinesFile();
        
        
        createRanksFile();
        
        //if (!(rebootFile.exists())) {
        //    createRebootFile();
        //}
    }
    
    public static void createUpgradesFile() {
        //Layout:
        //ID: 1
        //Size: 5 #Like
        
        File customConfig = new File(plugin.getDataFolder() + "gui/upgrades.yml");
        
        plugin.saveResource("gui/upgrades.yml", false);
        
    }
    
    public static void createEffectsFile() {
        
        File customConfig = new File(plugin.getDataFolder() + "gui/effects.yml");
        
        plugin.saveResource("gui/effects.yml", false);
        
    }
    
    public static void createEnchantmentGUIFile() {
        
        File customConfig = new File(plugin.getDataFolder() + "gui/enchantmentGUI.yml");
        
        plugin.saveResource("gui/enchantmentGUI.yml", false);
        
    }
    
    public static void createBlocksFile() {
        
        File customConfig = new File(plugin.getDataFolder() + "gui/blocks.yml");
        
        plugin.saveResource("gui/blocks.yml", false);
        
    }
    
    public static void createEnchantmentsFile() {
        File customConfig = new File(plugin.getDataFolder() + "Configs/enchantments.yml");
        
        plugin.saveResource("Configs/enchantments.yml", false);
        
    }
    
    public static void createPrivateMinesFile() {
        File customConfig = new File(plugin.getDataFolder() + "Configs/private_mine.yml");
        
        plugin.saveResource("Configs/private_mine.yml", false);
        
    }
    
    public static void createRanksFile() {
        File customConfig = new File(plugin.getDataFolder() + "Configs/infiniteRanks.yml");
        
        plugin.saveResource("Configs/infiniteRanks.yml", false);
        
    }
    
    public static void createRebootFile() {
        //File customConfig = new File(plugin.getDataFolder() + "Configs/reboot.yml");
        
        //plugin.saveResource("Configs/reboot.yml", false);
        
    }
    
}

package prisoncore.aDragz.Features.Enchantments.EnchantmentManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Enchantments.EnchantmentList.advanced.*;
import prisoncore.aDragz.Features.Enchantments.EnchantmentList.basic.charity;
import prisoncore.aDragz.Features.Enchantments.EnchantmentList.basic.moneyFinder;
import prisoncore.aDragz.Features.Enchantments.EnchantmentList.basic.tokenFinder;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.Random;

public class getProc {
    static private final Random rand = new Random();
    
    private static final double min = 0.0;
    private static final double  max = 100.0;
    
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    public static void procCharity (int level, Player player) {
        if (level != 0) { //So it doesn't proc if you don't have it
            
            Double procChance = rand.nextDouble(max - min + 1) + min;
            
            double procNumber = level/cfgYaml.getDouble("Charity.Proc-Calculation");
            
            if (procChance < procNumber) { // (max = 90)
                charity.procCharity(level, player);
            }
        }
    }
    
    public static void procNuke (int level, Player player, Material block, String leader) {
        if (level != 0) { //So it doesn't proc if you don't have it
            
            Double procChance = rand.nextDouble(max - min + 1) + min;
            
            double procNumber = level/cfgYaml.getDouble("Nuke.Proc-Calculation");
            
            if (procChance < procNumber) { // (max = 90)
                nuke.proc(player, leader);
            }
        }
    }
    
    public static void procTnt (int level, Player player, int y, Material block, String leader) {
        if (level != 0) { //So it doesn't proc if you don't have it
            
            Double procChance = rand.nextDouble(max - min + 1) + min;
            
            double procNumber = level/cfgYaml.getDouble("Tnt.Proc-Calculation");
            
            if (procChance < procNumber) { // (max = 90)
                tnt.procTnt(player, y, leader); //Y for block to remove layer
            }
        }
    }
    
    public static void procThunder (int level, Player player, int y, Material block, String leader, Location location) {
        if (level != 0) { //So it doesn't proc if you don't have it
            
            Double procAmount = Math.ceil(level / 200) + cfgYaml.getInt("Thunder.Min-Lightning");
            
            Double procChance = rand.nextDouble(max - min + 1) + min;
            
            double procNumber = level/cfgYaml.getDouble("Thunder.Proc-Calculation");
            
            if (procChance < procNumber) { // (max = 90)
                thunder.procThunder(player, block, location, leader, procAmount);
            }
        }
    }
    
    public static void procLaser (int level, Player player, Material block, String leader, Location location) {
        
        if (level != 0) { //So it doesn't proc if you don't have it
            
            Double procChance = rand.nextDouble(max - min + 1) + min;
            
            double procNumber = level/cfgYaml.getDouble("Laser.Proc-Calculation");
            
            if (procChance < procNumber) { // (max = 90)
                laser.procLaser(player, block, location, leader); //Y for block to remove layer
            }
        }
    }
    
    public static void procJackhammer (int level, Player player, int y, Material block, String leader) {
        
        if (level != 0) { //So it doesn't proc if you don't have it
            
            Double procChance = rand.nextDouble(max - min + 1) + min;
            
            double procNumber = level/cfgYaml.getDouble("Jackhammer.Proc-Calculation");
            
            if (procChance < procNumber) { // (max = 90)
                jackhammer.procJackhammer(player, y, block, leader);
            }
        }
    }
    
    public static void procMoneyFinder (int level, Player player) {
        if (level != 0) {
            Double procChance = rand.nextDouble(max - min + 1) + min;
            
            double procNumber = level/cfgYaml.getDouble("Money-Finder.Proc-Calculation");
            
            if (procChance < procNumber) { // (max = 90)
                moneyFinder.procMoneyFinder(level, player);
            }
        }
    }
    
    public static void procTokenFinder (int level, Player player) {
        if (level != 0) {
            Double procChance = rand.nextDouble(max - min + 1) + min;
            
            double procNumber = level/cfgYaml.getDouble("Token-Finder.Proc-Calculation");
            
            if (procChance < procNumber) { // (max = 90)
                tokenFinder.procTokenFinder(level, player);
            }
        }
    }
}

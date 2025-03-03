package prisoncore.aDragz.Features.Enchantments.EnchantmentManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.text.DecimalFormat;

public class getProcPercent {
    
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    
    //Default value was "return decimalFormat.format(100.0 - (100.0 * proc) / 100.0);"
    
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    public static String percentConverter(double enchantLevel, String enchantName) {
        //100-(100*maxNumber) / 100 - converts to a percentage
        
        if (enchantName.equals("Money Finder")) {
            //25*(level*20)*10 (level=1) = 5000
            double procNumber = enchantLevel/cfgYaml.getDouble("Money-Finder.Proc-Calculation");
            
            return decimalFormat.format(procNumber);
        } else if (enchantName.equals("Token Finder")) {
            //25*(level*20)*2 (level=1) = 1500
            
            double procNumber = enchantLevel/cfgYaml.getDouble("Token-Finder.Proc-Calculation");
            
            return decimalFormat.format(procNumber);
        } else if (enchantName.equals("Jackhammer")) {
            //25*(1*20)*10 (level=1) = 5000]
            
            double procNumber = enchantLevel/cfgYaml.getDouble("Jackhammer.Proc-Calculation");
            
            return decimalFormat.format(procNumber);
        } else if (enchantName.equals("Tnt")) {
            //25*(1*20)*20 (level=1) = 10000
            
            double procNumber = enchantLevel/cfgYaml.getDouble("Tnt.Proc-Calculation");
            
            return decimalFormat.format(procNumber);
        } else if (enchantName.equals("Thunder")) {
            //25*(1*20)*10 (level=1) = 2000
            
            double procNumber = enchantLevel/cfgYaml.getDouble("Thunder.Proc-Calculation");
            
            return decimalFormat.format(procNumber);
        } else if (enchantName.equals("Laser")) {
            //25*(1*20)*20 (level=1) = 10000
            
            double procNumber = enchantLevel/cfgYaml.getDouble("Laser.Proc-Calculation");
            
            return decimalFormat.format(procNumber);
        } else if (enchantName.equals("Charity")) {
            //25*(1*20)*20 (level=1) = 10000
            
            double procNumber = enchantLevel/cfgYaml.getDouble("Charity.Proc-Calculation");
            
            return decimalFormat.format(procNumber);
        } else if (enchantName.equals("Nuke")) {
            //25*(1*20)*20 (level=1) = 10000
            
            double procNumber = enchantLevel/cfgYaml.getDouble("Nuke.Proc-Calculation");
            
            return decimalFormat.format(procNumber);
        }
        
        
        return decimalFormat.format(100.0);
    }
}

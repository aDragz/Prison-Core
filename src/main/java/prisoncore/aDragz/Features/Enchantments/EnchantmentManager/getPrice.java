package prisoncore.aDragz.Features.Enchantments.EnchantmentManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;

public class getPrice {
    
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    public static Long grabPrice(Long enchantLevel, String enchantName) {

        if (enchantName.equals("Efficiency")) {
            //(10*(Long.valueOf(enchantLevel)*1)+5000)
            
            Long value = cfgYaml.getLong("Efficiency.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Fortune")) {
            //(25 * (Long.valueOf(enchantLevel) * 2) + 1000)
            Long value = cfgYaml.getLong("Fortune.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Exp")) {
            //(25*(Long.valueOf(enchantLevel)*20)*2)+1500
            Long value = cfgYaml.getLong("Exp.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Money Finder")) {
            //(25*(Long.valueOf(enchantLevel)*20)*1)+500
            Long value = cfgYaml.getLong("Money-Finder.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Token Finder")) {
            //(25*(Long.valueOf(enchantLevel)*20)*2)+1500
            Long value = cfgYaml.getLong("Token-Finder.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Jackhammer")) {
            //(100*(Long.valueOf(enchantLevel)*20))+5000
            Long value = cfgYaml.getLong("Jackhammer.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Tnt")) {
            //(100*(Long.valueOf(enchantLevel)*20)*2)+5000
            Long value = cfgYaml.getLong("Tnt.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Thunder")) {
            //(50 * (Long.valueOf(enchantLevel) * 2) * 2) + 2000
            Long value = cfgYaml.getLong("Thunder.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Laser")) {
            //(100 * (Long.valueOf(enchantLevel) * 30)) + 6000
            Long value = cfgYaml.getLong("Laser.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Charity")) {
            //(100 * (Long.valueOf(enchantLevel) * 30)) + 6000
            Long value = cfgYaml.getLong("Charity.Price-Increase");
            
            return (value * enchantLevel) + value;
        } else if (enchantName.equals("Nuke")) {
            //(100 * (Long.valueOf(enchantLevel) * 30)) + 6000
            Long value = cfgYaml.getLong("Nuke.Price-Increase");
            
            return (value * enchantLevel) + value;
        }
        return 0L;
    }
}

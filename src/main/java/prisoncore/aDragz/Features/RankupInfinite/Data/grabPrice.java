package prisoncore.aDragz.Features.RankupInfinite.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;

public class grabPrice {
    private static final main plugin = main.getPlugin(main.class);
    
    static File ranksFile = new File(plugin.getDataFolder(), "Configs/infiniteRanks.yml");
    static FileConfiguration ranksCfg = YamlConfiguration.loadConfiguration(ranksFile);
    
    static Long basePrice = ranksCfg.getLong("Price.Base-Price");
    static Double multiplier = ranksCfg.getDouble("Price.Multiplier");
    
    public static Long getRankPrice(Long rankNumber) {
        return (long) ((basePrice * rankNumber) * multiplier);
    }
}
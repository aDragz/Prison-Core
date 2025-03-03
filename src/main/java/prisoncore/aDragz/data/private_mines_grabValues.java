package prisoncore.aDragz.data;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.main;

import java.io.File;

public class private_mines_grabValues {
    private static final main plugin = main.getPlugin(main.class);
    
    public static int currentTier(Player player) {
        String gangName = gangCheckInfo.grabGangName(player);
        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangName));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);
        
        return gangCfg.getInt("Mine.Tier");
    }
    
    public static Integer grabGangID(OfflinePlayer player) {
        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);
        
        String gangName = playerCfg.getString("Info.Gang.Name");
        
        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangName));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);
        
        return gangCfg.getInt("Info.ID");
    }
    
    public static Material currentBlock(OfflinePlayer Player, FileConfiguration gangCfg) {
        try {
            return Material.valueOf(gangCfg.getString("Mine.Block"));
        } catch (Exception e) {
            //Null:
            return Material.COBBLESTONE;
        }
    }
}

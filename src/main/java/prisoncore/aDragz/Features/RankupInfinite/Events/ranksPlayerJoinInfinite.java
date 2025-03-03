package prisoncore.aDragz.Features.RankupInfinite.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import prisoncore.aDragz.Features.RankupInfinite.Data.Storage.storeRanksInfinite;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.EventListener;
import java.util.Objects;

public class ranksPlayerJoinInfinite implements EventListener, Listener {
    
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "ranksinfinite.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    @EventHandler
    public static void playerJoinEvent(PlayerJoinEvent event) {
        
        if (Objects.isNull(storeRanksInfinite.ranks.get(event.getPlayer().getUniqueId().toString()))) {
            storeRanksInfinite.ranks.put(event.getPlayer().getUniqueId().toString(), 1L);
        }
    }
}

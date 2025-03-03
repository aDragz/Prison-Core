package prisoncore.aDragz.Features.Pouches.Events;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PouchJoinEvent implements Listener {
    
    private static final main plugin = main.getPlugin(main.class);
        
        @EventHandler
        public void onJoin(PlayerJoinEvent event) {
            UUID playerID = event.getPlayer().getUniqueId();
            
            File playersfile = new File(plugin.getDataFolder(), "PlayerData/" + playerID + ".yml");
            YamlConfiguration playerscfg = YamlConfiguration.loadConfiguration(playersfile);
            
            if (!(playersfile.exists())) {
                playerscfg.set("Pouches.Money.Amount", 0);
                playerscfg.set("Pouches.Exp.Amount", 0);
                playerscfg.set("Pouches.Tokens.Amount", 0);
                playerscfg.set("Pouches.Crates.Amount", 0);
                
                try {
                    playerscfg.save(playersfile);
                } catch (IOException e) {
                    return;
                }
            }
        }
}

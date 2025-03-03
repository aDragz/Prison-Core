package prisoncore.aDragz.Features.Sell.Multipliers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import prisoncore.aDragz.Features.Sell.Multipliers.cmds.giveMultiplier;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.EventListener;
import java.util.Objects;
import java.util.UUID;

public class onPlayerJoinEvent implements EventListener, Listener {
    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "multipliers.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    @EventHandler
    public static void playerJoinEvent(PlayerJoinEvent event) {
        //Grab the player ID. Then try to find in the multipliers.yml file.
        UUID ID = event.getPlayer().getUniqueId();

        if (Objects.isNull(cfgYaml.get(ID.toString()))) {
            giveMultiplier.setPlayerMultiplier(ID, 1);
        }
    }

}

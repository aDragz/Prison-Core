package prisoncore.aDragz.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.main;

import java.io.File;

public class gang_grabValues {
    private static final main plugin = main.getPlugin(main.class);

    public static String gangLeader(Player player) {
        String gangName = gangCheckInfo.grabGangName(player);
        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangName));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

        return gangCfg.getString("Info.Leader");
    }
}

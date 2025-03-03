package prisoncore.aDragz.Features.Admin.Features.Mine.Commands.Management;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class setMineWorld {
    private final static main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "config.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    public static void setWorld(CommandSender player, String worldName) throws IOException {
        if (Objects.isNull(worldName)) {
            player.sendMessage(ChatColor.DARK_RED + "Invalid world");
        }
        
        try {
            cfgYaml.set("Type.Private_Mine.World", worldName.toString());
            cfgYaml.save(cfgFile);
            
            player.sendMessage(ChatColor.GREEN + "Set World to '" + worldName + "'");
            player.sendMessage("");
            player.sendMessage(ChatColor.GRAY + "Please use " + ChatColor.DARK_GRAY + "'/reboot'" + ChatColor.GRAY + " to apply these changes!");
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            throw e;
        }
    }
}

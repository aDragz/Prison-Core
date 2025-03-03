package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.nonAuto.V215;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class updateMessages215 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "Messages.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateMessages(CommandSender player) throws IOException {
        cfg.set("mine.tp_decline", "&c&lPrison&8&l-&c&lCore &8&l» &cYou cannot tp to your mine currently!");

        cfg.save(file);
        
        player.sendMessage(ChatColor.GREEN + "Updated Messages.yml:");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "mine.tp_decline");
        player.sendMessage("");
    }
}

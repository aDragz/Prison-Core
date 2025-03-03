package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.nonAuto.V208;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class updateMessages208 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "Messages.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateMessages(CommandSender player) throws IOException {
        cfg.set("reboot.stop", "&c&lPrison&8&l-&c&lCore &8&l» &cYou have stopped the reboot!");
        cfg.set("admin.schem_no_args", "&c&lPrison&8&l-&c&lCore &8&l» &4Please enter Schematic Name!");
        cfg.set("admin.updated", "&c&lPrison&8&l-&c&lCore &8&l» &aYou have Updated the configs!");
        
        cfg.save(file);
        
        player.sendMessage(ChatColor.GREEN + "Updated Messages.yml:");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "reboot.stop");
        player.sendMessage(ChatColor.GRAY + "admin.schem_no_args");
        player.sendMessage(ChatColor.GRAY + "admin.updated");
        player.sendMessage("");
    }
}

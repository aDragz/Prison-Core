package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.nonAuto.V211;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class updateEnchantments211 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateMessages(CommandSender player) throws IOException {
        cfg.set("Thunder.Min-Lightning", 1);
        cfg.set("Thunder.Max-Lightning", 10);
        
        cfg.save(file);
        
        player.sendMessage(ChatColor.GREEN + "Updated Configs/enchantments.yml:");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "Thunder.Min-Lightning");
        player.sendMessage(ChatColor.GRAY + "Thunder.Max-Lightning");
        player.sendMessage("");
    }
}

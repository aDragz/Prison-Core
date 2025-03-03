package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.nonAuto.V211;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class updateEnchantmentGUI211 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "gui/enchantmentGUI.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateConfig(CommandSender player) throws IOException {
        
        cfg.set("GUI.Title", "&aEnchantments");
        
        cfg.save(file);
        
        player.sendMessage(ChatColor.GREEN + "Updated gui/enchantmentGUI.yml");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "GUI.Title");
        player.sendMessage("");
    }
}
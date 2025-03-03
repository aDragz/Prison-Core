package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.nonAuto.V210;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class updateEnchantments210 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateMessages(CommandSender player) throws IOException {
        cfg.set("Charity.Display-Name", "&6Charity: &d");
        cfg.set("Charity.Price-Increase", 250);
        cfg.set("Charity.Max-Value", 10000);
        cfg.set("Charity.Proc-Calculations", 5.0);
        cfg.set("Charity.Value", 1500);
        cfg.set("Charity.Divide", 10);
        
        cfg.set("Nuke.Display-Name", "&4Nuke: &d");
        cfg.set("Nuke.Price-Increase", 3000);
        cfg.set("Nuke.Max-Value", 10000);
        cfg.set("Nuke.Proc-Calculation", 50.0);
        
        cfg.set("Tnt.Max-Layers", 10);
        
        cfg.save(file);
        
        player.sendMessage(ChatColor.GREEN + "Updated Configs/enchantments.yml:");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "enchants.charity");
        player.sendMessage(ChatColor.GRAY + "enchants.nuke");
        player.sendMessage(ChatColor.GRAY + "tnt.max-layers");
        player.sendMessage("");
    }
}

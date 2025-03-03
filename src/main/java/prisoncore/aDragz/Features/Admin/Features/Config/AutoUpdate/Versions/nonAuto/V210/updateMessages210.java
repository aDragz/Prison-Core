package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.nonAuto.V210;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class updateMessages210 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "Messages.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateMessages(CommandSender player) throws IOException {
        cfg.set("enchants.charity_give", "&c&lPrison&8&l-&c&lCore &8&l» &6You were given &fTOKENS &eTokens &6And &f$&aMONEY &6with Charity!");
        
        ArrayList<String> totalSold = new ArrayList<>();
        
        totalSold.add("&eTotal Sold:");
        totalSold.add("");
        totalSold.add("&a$&fTOTAL");
        totalSold.add("&fMultiplier: &dMULTIx");
        
        cfg.set("mine.total_sold", totalSold);
        
        cfg.save(file);
        
        player.sendMessage(ChatColor.GREEN + "Updated Messages.yml:");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "enchants.charity_give");
        player.sendMessage("");
    }
}

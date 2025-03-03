package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.nonAuto.V209;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class updateBlocks209 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "gui/blocks.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateBlocks(CommandSender player) throws IOException {
        
        ArrayList<String> blocks = new ArrayList<>();
        
        blocks.add("Bedrock");
        blocks.add("Glowstone");
        
        cfg.set("Border", blocks);
        
        cfg.save(file);
        
        player.sendMessage(ChatColor.GREEN + "Updated gui/blocks.yml:");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "Border");
        player.sendMessage("");
        player.sendMessage(ChatColor.RED + "Comments are not available inside the config, unless you add them manually!");
        player.sendMessage(ChatColor.DARK_RED + "Comments explain how the border works, check update post for more info!");
        player.sendMessage("");
    }
}
package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.nonAuto.V209;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class updateInfiniteRanks209 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "Configs/infiniteRanks.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateRanks(CommandSender player) throws IOException {
        
        ArrayList<String> commands = new ArrayList<>();
        commands.add("eco give PLAYER 1000");
        commands.add("token give PLAYER 5000");
        cfg.set("Rewards.1.Commands", commands);
        
        ArrayList<String> commands2 = new ArrayList<>();
        commands2.add("token give PLAYER 7500");
        cfg.set("Rewards.2.Commands", commands2);
        
        ArrayList<String> commands5 = new ArrayList<>();
        commands5.add("eco give PLAYER 15000");
        cfg.set("Rewards.5.Commands", commands5);
        
        cfg.save(file);
        
        //To stop memory leaks (hopefully)
        
        commands5.clear();
        commands2.clear();
        commands.clear();
        
        player.sendMessage(ChatColor.GREEN + "Updated Configs/infiniteRanks.yml:");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "Rewards.1.Commands");
        player.sendMessage(ChatColor.GRAY + "Rewards.2.Commands");
        player.sendMessage(ChatColor.GRAY + "Rewards.5.Commands");
        player.sendMessage("");
        player.sendMessage(ChatColor.RED + "Comments are not available inside the config, unless you add them manually!");
        player.sendMessage(ChatColor.DARK_RED + "Comments explain how the ranking system works, check update post for more info!");
        player.sendMessage("");
    }
}

package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.nonAuto.V212;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class updateMessages212 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "Messages.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateMessages(CommandSender player) throws IOException {
        cfg.set("gang.chat_no_args_disabled", "&c&lPrison&8&l-&c&lCore &8&l» &cGang Chat is Disabled! Use &a/gang chat toggle &cto enable it!");
        cfg.set("global.unknown_player", "&c&lPrison&8&l-&c&lCore &8&l» &4Player &cPLAYER &4is Offline!");
        cfg.set("global.no_player_name", "&c&lPrison&8&l-&c&lCore &8&l» &4Please Enter Player Name!");
        cfg.set("global.never_joined_player", "&c&lPrison&8&l-&c&lCore &8&l» &4Player &cPLAYER &4has never Joined the Server!");
        cfg.set("token.paidsender", "&c&lPrison&8&l-&c&lCore &8&l» &cYou paid &fPLAYER &9AMOUNT &ctokens!");
        cfg.set("token.paidreceiver", "&c&lPrison&8&l-&c&lCore &8&l» &aYou were paid &9AMOUNT &atokens by &fPLAYER!");
        
        
        cfg.save(file);
        
        player.sendMessage(ChatColor.GREEN + "Updated Messages.yml:");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "gang.chat_no_args_disabled");
        player.sendMessage(ChatColor.GRAY + "global.unknown_player");
        player.sendMessage(ChatColor.GRAY + "global.no_player_name");
        player.sendMessage(ChatColor.GRAY + "token.paidsender");
        player.sendMessage(ChatColor.GRAY + "token.paidreceiver");
        player.sendMessage("");
    }
}

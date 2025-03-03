package prisoncore.aDragz.Features.Tokens.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Logs.Features.Tokens.logsTokenGive;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;

public class tokenGive {
    
    public static boolean giveToken(CommandSender sender, OfflinePlayer player, String[] args) throws NullPointerException {
        try {
            long beforeTokens = storeTokens.tokens.get(player.getUniqueId().toString());
            long afterTokens = (beforeTokens + Long.valueOf(args[2]));
            
            storeTokens.tokens.put(player.getUniqueId().toString(), afterTokens);
            logsTokenGive.transactionTokenGive(sender.getName().toString(), player, beforeTokens, afterTokens);
            
            sender.sendMessage(grabMessagesValue.type("token", "give").replaceAll("&", "§").replaceAll("PLAYER", args[1]).replaceAll("AMOUNT", args[2]));
            if (player.isOnline())
                Bukkit.getPlayer(player.getName()).sendMessage((grabMessagesValue.type("token", "given").replaceAll("&", "§").replaceAll("AMOUNT", args[2])));
        } catch (Exception e) {
            sender.sendMessage(ChatColor.DARK_RED + "An error has occurred! Please message an Administrator!");
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
        return false;
    }
}

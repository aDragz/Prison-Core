package prisoncore.aDragz.Features.Tokens.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;

import java.text.DecimalFormat;

public class tokenCheckValue {
    
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###,###,###,###");
    
    static void tokenCheck(Player player){
        player.sendMessage(grabMessagesValue.type("token", "balance").replaceAll("&", "§").replaceAll("AMOUNT", decimalFormat.format(storeTokens.tokens.get(player.getUniqueId().toString()))));
    }
    
    static void tokenCheckAdmin(Player player, String[] args) {
        if (player.hasPermission("PrisonCore.Tokens.balance")) {
            if (args[0].equalsIgnoreCase("check") || (args[0].equalsIgnoreCase("bal") || (args[0].equalsIgnoreCase("balance")))) {
                if (args.length == 1) {
                    player.sendMessage(grabMessagesValue.type("global", "no_player_name").replaceAll("&", "§"));
                    return;
                }
                try {
                    OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);
                    player.sendMessage(grabMessagesValue.type("token", "balance_admin")
                            .replaceAll("&", "§")
                            .replaceAll("PLAYER", args[1])
                            .replaceAll("AMOUNT",
                                    decimalFormat.format(storeTokens.tokens.get
                                            (targetPlayer.getUniqueId().toString()))));
                } catch (Exception e) {
                    player.sendMessage(grabMessagesValue.type("global", "never_joined_player").replaceAll("&", "§").replaceAll("PLAYER", args[1]));
                    
                }
            }
        }
    }
    
}

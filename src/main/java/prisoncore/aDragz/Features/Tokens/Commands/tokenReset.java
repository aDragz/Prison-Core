package prisoncore.aDragz.Features.Tokens.Commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Logs.Features.Tokens.logsTokenReset;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;

public class tokenReset {
    
    static boolean resetToken(Player sender, OfflinePlayer player) {
        storeTokens.tokens.put(player.getUniqueId().toString(), 0L);

        logsTokenReset.transactionTokenReset(sender, player);

        sender.sendMessage(grabMessagesValue.type("token", "reset").replaceAll("&", "§").replaceAll("PLAYER", player.getName().toString()));
    return false;
    }
}

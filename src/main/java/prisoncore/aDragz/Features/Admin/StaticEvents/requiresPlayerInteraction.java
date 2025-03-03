package prisoncore.aDragz.Features.Admin.StaticEvents;

import org.bukkit.command.CommandSender;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;

public class requiresPlayerInteraction {
    public static void noPlayerName(CommandSender player) {
        player.sendMessage(grabMessagesValue.type("global", "no_player_name").replaceAll("&", "§"));
    }
    
    public static void unknownPlayerName(CommandSender player, String target) {
        player.sendMessage(grabMessagesValue.type("global", "unknown_player").replaceAll("&", "§").replaceAll("PLAYER", target));
    }
}

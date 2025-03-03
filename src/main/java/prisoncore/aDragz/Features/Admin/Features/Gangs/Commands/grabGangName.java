package prisoncore.aDragz.Features.Admin.Features.Gangs.Commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;

public class grabGangName {

    public static void adminGangName(CommandSender admin, OfflinePlayer target) {
        try {
            admin.sendMessage(grabMessagesValue.type("admin", "info_gangs_name").replaceAll("&", "§").replaceAll("PLAYER", target.getName().toString()).replaceAll("GANGNAME", gangCheckInfo.grabGangName(target)));
        } catch (NullPointerException e) {
            admin.sendMessage(grabMessagesValue.type("admin", "info_gangs_name").replaceAll("&", "§").replaceAll("PLAYER", target.getName().toString()).replaceAll("GANGNAME", "§4Unknown gang name - " + e.getMessage()));
        }
    }
}

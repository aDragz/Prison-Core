package prisoncore.aDragz.Features.Admin.Features.PVE;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Admin.Features.PVE.Commands.mobsReset;
import prisoncore.aDragz.Features.Admin.Features.PVE.Commands.tpPve;

public class adminPveCommandManager {

    public static void adminPveCommands(CommandSender player, String[] args) {

        /*switch (args[1].toLowerCase()) {
            case "reset": //Resets all gangs
                if (player.hasPermission("Prisoncore.Admin.Pve.Reset")) {
                    mobsReset.resetMobs(0);
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Pickaxe.Give"));
                    return;
                }
                break;
            case "forcetp":
                if (player.hasPermission("PrisonCore.Admin.Pve.ForceTp")) {
                    tpPve.tpPlayer(Bukkit.getPlayer(args[2]));
                }
                break;
            default:
                player.sendMessage(grabMessagesValue.type("admin", "unknown_feature").replaceAll("&", "§"));
        }
         */
    }
}

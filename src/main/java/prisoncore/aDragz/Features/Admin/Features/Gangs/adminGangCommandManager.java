package prisoncore.aDragz.Features.Admin.Features.Gangs;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Admin.Features.Gangs.Commands.checkGangDetails;
import prisoncore.aDragz.Features.Admin.Features.Gangs.Commands.deleteGangs;
import prisoncore.aDragz.Features.Admin.Features.Gangs.Commands.grabGangName;
import prisoncore.aDragz.Features.Admin.Features.Gangs.Commands.listGangFiles;

import java.io.IOException;

public class adminGangCommandManager {
    
    public static void adminGangCommands(CommandSender player, String[] args) throws IOException {
        
        switch (args[1].toLowerCase()) {
            case "reset": //Resets all gangs
                if (player.hasPermission("Prisoncore.Admin.Gang.Reset")) {
                    if (args.length == 3) {
                        if (args[2].equalsIgnoreCase("confirm")) {
                            player.sendMessage(grabMessagesValue.type("admin", "delete_gangs").replaceAll("&", "§"));
                            if (deleteGangs.deleteAllGangs())
                                player.sendMessage(grabMessagesValue.type("admin", "delete_gangs_complete").replaceAll("&", "§"));
                            else
                                player.sendMessage(grabMessagesValue.type("admin", "delete_gangs_incomplete").replaceAll("&", "§"));
                            break;
                        } else {
                            player.sendMessage("");
                            player.sendMessage("§4§lThis will delete all gangs!");
                            player.sendMessage("§cType '/admin gang reset §econfirm§c' to confirm this choice.");
                            player.sendMessage("");
                            break;
                        }
                    } else {
                        player.sendMessage("");
                        player.sendMessage("§4§lThis will delete all gangs!");
                        player.sendMessage("§cType '/admin gang reset §econfirm§c' to confirm this choice.");
                        player.sendMessage("");
                        break;
                    }
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Gang.Reset"));
                    return;
                }
            case "list": //Lists all gang files
                if (player.hasPermission("Prisoncore.Admin.Gang.List")) {
                    if (!listGangFiles.listFiles(player))
                        player.sendMessage(grabMessagesValue.type("admin", "list_gangs_incomplete").replaceAll("&", "§"));
                    break;
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Gang.List"));
                    return;
                }
            case "info": //Lists all gang files
                if (player.hasPermission("Prisoncore.Admin.Gang.Info")) {
                    if (args.length != 3) {
                        player.sendMessage(grabMessagesValue.type("admin", "no_args").replaceAll("&", "§").replaceAll("INFO", "Please enter Gang Name"));
                        return;
                    }
                    
                    if (!checkGangDetails.checkDetails(player, args[2])) //gang0 info1 name2
                        player.sendMessage(grabMessagesValue.type("admin", "info_gangs_incomplete").replaceAll("&", "§"));
                    break;
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Gang.Info"));
                    return;
                }
            case "name":
                if (player.hasPermission("Prisoncore.Admin.Gang.Name")) {
                    grabGangName.adminGangName(player, Bukkit.getOfflinePlayer(args[2]));
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Gang.Info"));
                    return;
                }
                
                break;
            default:
                player.sendMessage(grabMessagesValue.type("admin", "unknown_feature").replaceAll("&", "§"));
        }
    }
}

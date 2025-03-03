package prisoncore.aDragz.Features.Admin.Features.Pickaxe;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Admin.Features.Pickaxe.Commands.pickaxeGive;
import prisoncore.aDragz.Features.Admin.StaticEvents.requiresPlayerInteraction;

import java.io.IOException;
import java.util.Objects;

public class adminPickaxeCommandManager {

    public static void adminPickaxeCommands(CommandSender player, String[] args) throws IOException {

        switch (args[1].toLowerCase()) {
            case "give": //Resets all gangs
                if (player.hasPermission("Prisoncore.Admin.Pickaxe.Give")) {
                    if (Objects.equals(args.length, 2)) {
                        requiresPlayerInteraction.noPlayerName(player);
                        return;
                    }
                    if (Bukkit.getOfflinePlayer(args[2]).isOnline()) {
                        pickaxeGive.givePickaxe((Player) Bukkit.getOfflinePlayer(args[2]), args);
                        player.sendMessage(grabMessagesValue.type("admin", "give_pickaxe").replaceAll("&", "§").replaceAll("PLAYER", args[2]));
                        break;
                    } else {
                        requiresPlayerInteraction.unknownPlayerName(player, args[2]);
                        return;
                    }
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Pickaxe.Give"));
                    return;
                }
            default:
                player.sendMessage(grabMessagesValue.type("admin", "unknown_feature").replaceAll("&", "§"));
        }
    }
}

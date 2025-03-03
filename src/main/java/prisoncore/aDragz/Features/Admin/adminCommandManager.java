package prisoncore.aDragz.Features.Admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Admin.Features.Config.adminUpdateCommandManager;
import prisoncore.aDragz.Features.Admin.Features.Gangs.adminGangCommandManager;
import prisoncore.aDragz.Features.Admin.Features.InfiniteRankup.adminRankupCommandManager;
import prisoncore.aDragz.Features.Admin.Features.Mine.adminMineCommandManager;
import prisoncore.aDragz.Features.Admin.Features.Pickaxe.adminPickaxeCommandManager;

import java.io.IOException;

public class adminCommandManager implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender player, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("admin")) {
            if (!player.hasPermission("PrisonCore.Admin")) {
                player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin"));
                return false;
            }
            if (args.length < 1) {
                player.sendMessage(grabMessagesValue.type("admin", "no_feature").replaceAll("&", "§"));
                return false;
            }

            //Does not need it for "Update", as it's just '/admin update'
            if (args.length == 1 && !args[0].equalsIgnoreCase("update")) {
                player.sendMessage(grabMessagesValue.type("admin", "no_args").replaceAll("&", "§").replaceAll("INFO", ""));
                return false;
            }


            //Grab feature

            switch (args[0].toLowerCase()) {
                case "gang":
                    if (player.hasPermission("PrisonCore.Admin.Gang")) {
                        try {

                            adminGangCommandManager.adminGangCommands(player, args);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Gang"));
                    }
                    return true;
                case "pickaxe":
                    if (player.hasPermission("PrisonCore.Admin.Pickaxe")) {
                        try {
                            adminPickaxeCommandManager.adminPickaxeCommands(player, args);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Pickaxe"));
                    }
                    return true;
                case "mine":
                    if (player.hasPermission("PrisonCore.Admin.Mine")) {
                        try {
                            adminMineCommandManager.adminPickaxeCommands(player, args);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Mine"));
                    }
                    return true;
                case "rankup":
                    if (player.hasPermission("PrisonCore.Admin.Rankup")) {
                            try {
                                adminRankupCommandManager.adminRankupCommands(player, args);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                        player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Rankup"));
                    }
                    
                    return true;
                case "update":
                    if (player.hasPermission("PrisonCore.Admin.Update")) {
                        try {
                            adminUpdateCommandManager.adminUpdateCommands(player);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Update"));
                    }
                    
                    return true;
                /*case "pve":
                    if (player.hasPermission("PrisonCore.Admin.Pve")) {
                        adminPveCommandManager.adminPveCommands(player, args);
                    }
                    return true;
                 */
                default:
                    player.sendMessage(grabMessagesValue.type("admin", "unknown_feature").replaceAll("&", "§"));
                    return false;
            }
        }

        return false;
    }
}

package prisoncore.aDragz.Features.PrisonCoreAdmin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.PrisonCoreAdmin.Schematics.createMineSchematic;

import java.util.ArrayList;

public class adminCommandHandler implements Listener, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("prisoncore")) {
            if (player.hasPermission("PrisonCore.Admin")) {
                if (args.length == 0) {
                    helpMenu(player);
                    return false;
                }
                switch (args[0].toLowerCase()) {
                    case "schem":
                        if (args.length == 1) {
                            player.sendMessage(ChatColor.RED + "Please enter File Name");
                            return false;
                        }
                        createMineSchematic.moveSchematic(player, args[1]); //Prisoncore[-1] Schematic[0] name[1]
                        return true;
                    case "schematic":
                        if (args.length == 1) {
                            player.sendMessage(ChatColor.RED + "Please enter File Name");
                            return false;
                        }
                        createMineSchematic.moveSchematic(player, args[1]); //Prisoncore[-1] Schematic[0] name[1]
                        return true;
                    default:
                        helpMenu(player);
                }
            } else {
                player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin"));
            }
        }
        return false;
    }

    static ArrayList<String> Commands = new ArrayList<>(); //Already create on server startup to not create lag if someone is spamming it. Like 20 admins.

    static void helpMenu(Player player) {
        player.sendMessage("§cPrison-Core §f- §cADMIN HELP");
        player.sendMessage("§7List of Commands: §bhttps://prisoncore.dev/commands");
        player.sendMessage("§7List of Permissions: §bhttps://prisoncore.dev/permissions");
        player.sendMessage("");
        player.sendMessage("§7Need Help? Join the discord and ask the community / developer: §bhttps://prisoncore.dev/discord");

    }
}

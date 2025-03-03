package prisoncore.aDragz.Features.Gangs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class gangHelp {
    public static void helpMenu(Player player) {
        player.sendMessage("");
        player.sendMessage(ChatColor.RED + "Gangs - Help Menu");
        player.sendMessage("");

        player.sendMessage(ChatColor.YELLOW + "/gang " + ChatColor.GREEN + "create"
                + ChatColor.WHITE + " <name> - Creates a gang");

        player.sendMessage(ChatColor.YELLOW + "/gang " + ChatColor.GREEN + "disband"
                + ChatColor.WHITE + " - Disbands your gang");

        player.sendMessage(ChatColor.YELLOW + "/gang " + ChatColor.GREEN + "invite"
                + ChatColor.WHITE + " <name> - Invites <name> to your gang");

        player.sendMessage(ChatColor.YELLOW + "/gang " + ChatColor.GREEN + "join"
                + ChatColor.WHITE + " <gang_name> - Joins <gang_name>");

        player.sendMessage(ChatColor.YELLOW + "/gang " + ChatColor.GREEN + "kick"
                + ChatColor.WHITE + " <name> - Kicks <name> from your gang");

        player.sendMessage(ChatColor.YELLOW + "/gang " + ChatColor.GREEN + "invites"
                + ChatColor.WHITE + " - Shows your invites");
    }
}

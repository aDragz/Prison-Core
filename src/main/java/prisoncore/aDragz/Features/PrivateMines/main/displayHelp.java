package prisoncore.aDragz.Features.PrivateMines.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class displayHelp {
    public static void showHelpMenu(Player player) {
        //Display the help menu to the player so they know what to do.

        player.sendMessage("");
        player.sendMessage(ChatColor.RED + "Private Mines - Help Menu");
        player.sendMessage("");

        player.sendMessage(ChatColor.YELLOW + "/mine"
                + ChatColor.WHITE + " - Shows Mines menu");

        player.sendMessage(ChatColor.YELLOW + "/mine " + ChatColor.GREEN + "tp"
                + ChatColor.WHITE + " - Teleports you to your Mine");

        player.sendMessage(ChatColor.YELLOW + "/mine " + ChatColor.GREEN + "reset"
                + ChatColor.WHITE + " - Resets your Mine");

        player.sendMessage(ChatColor.YELLOW + "/mine " + ChatColor.GREEN + "upgrade"
                + ChatColor.WHITE + " - Upgrades your mine if you reach the requirements");
    }
}

package prisoncore.aDragz.Features.Player.Help;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class displayHelp {
    public static void displayHelpPlayer(Player player, String command) {
        String commandName = Bukkit.getPluginCommand(command).getName();
        String commandDescription = Bukkit.getPluginCommand(command).getDescription();

        player.sendMessage(ChatColor.WHITE + "Command: §e/" + commandName);
        player.sendMessage(ChatColor.WHITE + "Description: " + commandDescription);
        player.sendMessage(ChatColor.WHITE + String.format("Usage: %s", returnUsage(command)));
    }

    public static void displayHelpConsole(ConsoleCommandSender console, String command) {

        String commandName = Bukkit.getPluginCommand(command).getName();
        String commandDescription = Bukkit.getPluginCommand("prison-core:" + command).getDescription();

        console.sendMessage("[Console-Help]");
        console.sendMessage(ChatColor.WHITE + "Command: §e/" + commandName);
        console.sendMessage(ChatColor.WHITE + "Description: " + commandDescription);
        console.sendMessage(ChatColor.WHITE + String.format("Usage: %s", returnUsage(command)));
    }

    private static String returnUsage(String type) {
        if (type.equals("checkgang")) return "§e/checkgang <§fPlayer§e>";
        if (type.equals("gm")) return "§e/gamemode <§fs,a,sp,c§e> <§fPlayer§e>";

        return "§4Unknown, Please message the Developer through https://prisoncore.dev/discord";
    }
}

package prisoncore.aDragz.Features.Player.Commands.Admin.moderator_commands.minor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.Features.Player.Help.displayHelp;

public class checkGang implements CommandExecutor {

    @Override
    @Deprecated
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("PrisonCore.CheckGangInformation")) {
            sender.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.CheckGangInformation"));
            return false;
        }
        if (sender == Bukkit.getConsoleSender() && args.length == 0) {
            displayHelp.displayHelpConsole(Bukkit.getConsoleSender(), "checkgang");
            return false;
        } //Check if console & has player
        if (args.length == 0) {
            displayHelp.displayHelpPlayer((Player) sender, "checkgang");
            return false;
        } //Check if args has a player
        if (command.getName().equalsIgnoreCase("checkgang")) {
            gangCheckInfo.adminCheckStats(sender, Bukkit.getOfflinePlayer(args[0]));
        } //Args[0] should be a player
        return false;
    }

}

package prisoncore.aDragz.Features.Logs.CommandHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class logsTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (command.getName().equals("transactions")) {
            if (sender.hasPermission("PrisonCore.Transactions")) {
                if (args.length == 1) {
                    return Arrays.asList("tokens", "enchantments");
                }
            }
        }
        return null;
    }
}

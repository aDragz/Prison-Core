package prisoncore.aDragz.Features.PrivateMines;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class minesTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equals("mine")) {
            if (args.length == 1) {
                return Arrays.asList("tp", "reset", "upgrade", "blocks");
            }
        }
        return null;
    }
}

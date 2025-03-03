package prisoncore.aDragz.Features.Gangs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class gangsTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equals("gang")) {
            if (args.length == 1) {
                return Arrays.asList("create", "disband", "invite", "join", "kick", "invites", "chat");
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("chat")) {
                    return Arrays.asList("toggle");
                }
            }
        }
        return null;
    }
}

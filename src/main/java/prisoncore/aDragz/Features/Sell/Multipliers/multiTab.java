package prisoncore.aDragz.Features.Sell.Multipliers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class multiTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equals("multiplier")) {
            if (args.length == 1) {
                ArrayList<String> tabList = new ArrayList<>();

                if (sender.hasPermission("Prisoncore.Multiplier.Give")) {
                    tabList.add("give");
                }

                if (sender.hasPermission("Prisoncore.Multiplier.Set")) {
                    tabList.add("set");
                }

                if (sender.hasPermission("Prisoncore.Multiplier.Save")) {
                    tabList.add("save");
                }

                return tabList;
            }
        }
        return null;
    }
}

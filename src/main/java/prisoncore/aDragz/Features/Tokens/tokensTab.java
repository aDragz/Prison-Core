package prisoncore.aDragz.Features.Tokens;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class tokensTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equals("token")) {
            if (args.length == 1) {
                ArrayList<String> tabList = new ArrayList<>();

                if (sender.hasPermission("PrisonCore.Tokens.reset")) {
                    tabList.add("reset");
                }

                if (sender.hasPermission("PrisonCore.Tokens.give")) {
                    tabList.add("give");
                }

                if (sender.hasPermission("PrisonCore.Tokens.save")) {
                    tabList.add("save");
                }

                if (sender.hasPermission("PrisonCore.Tokens.balance")) {
                    tabList.add("balance");
                }
                
                tabList.add("pay"); //Global Permission

                return tabList;
            }
        }
        return null;
    }
}

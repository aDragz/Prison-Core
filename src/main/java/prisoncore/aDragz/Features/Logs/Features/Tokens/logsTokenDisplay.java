package prisoncore.aDragz.Features.Logs.Features.Tokens;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.Features.Logs.LogManagement.Tokens.storeTokenTransactions;
import prisoncore.aDragz.main;

import java.util.ArrayList;
import java.util.Objects;

public class logsTokenDisplay {

    private final static main plugin = main.getPlugin(main.class);


    public static void checkLogs(CommandSender sender, OfflinePlayer player) {

        ArrayList<String> playerIDLogs = new ArrayList<>();
        if (Objects.isNull(storeTokenTransactions.transactions.get(player.getUniqueId().toString()))) {
            sender.sendMessage(ChatColor.DARK_RED + String.format("Player %s does not exist!", player));
            return;
        }

        playerIDLogs.addAll(storeTokenTransactions.transactions.get(player.getUniqueId().toString()));

        for (int i = 0; i < playerIDLogs.size(); ) {
            sender.sendMessage(playerIDLogs.get(i));
            i++;
        }
    }
}

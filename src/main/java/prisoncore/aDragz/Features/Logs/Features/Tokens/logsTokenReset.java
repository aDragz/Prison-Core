package prisoncore.aDragz.Features.Logs.Features.Tokens;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Logs.LogManagement.Tokens.storeTokenTransactions;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class logsTokenReset {

    public static void transactionTokenReset(Player sender, OfflinePlayer player) {
        //Transactions:

        ArrayList<String> transactions = new ArrayList<>();

        transactions.addAll(storeTokenTransactions.transactions.get(player.getUniqueId().toString()));

        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime timeNow = LocalDateTime.now();

        long beforeTokens = storeTokens.tokens.get(player.getUniqueId().toString());

        transactions.add(String.format("§8(§9§lToken§8§l-§9§lLogs§8) §f%d §7--> §f0 §7- §cReset by §c%s §8(§f%s§8)", beforeTokens, sender.getName().toString(), date.format(timeNow)));

        storeTokenTransactions.transactions.put(player.getUniqueId().toString(), transactions);
    }
}

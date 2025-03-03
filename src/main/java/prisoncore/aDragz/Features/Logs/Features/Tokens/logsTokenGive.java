package prisoncore.aDragz.Features.Logs.Features.Tokens;

import org.bukkit.OfflinePlayer;
import prisoncore.aDragz.Features.Logs.LogManagement.Tokens.storeTokenTransactions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class logsTokenGive {

    public static void transactionTokenGive(String sender, OfflinePlayer player, Long beforeTokens, Long afterTokens) {
        //Transactions:

        ArrayList<String> transactions = new ArrayList<>();

        transactions.addAll(storeTokenTransactions.transactions.get(player.getUniqueId().toString()));

        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime timeNow = LocalDateTime.now();

        long amountGiven = afterTokens - beforeTokens;

        transactions.add(String.format("§8(§9§lToken§8§l-§9§lLogs§8) §f%d §7--> §f%d §7- §aAdded by §c%s §8(§f%s§8) §8{§a+§e%s§8}", beforeTokens, afterTokens, sender, date.format(timeNow), amountGiven));

        storeTokenTransactions.transactions.put(player.getUniqueId().toString(), transactions);
    }
}

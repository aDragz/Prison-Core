package prisoncore.aDragz.Features.RankupInfinite.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.RankupInfinite.Data.grabPrice;
import prisoncore.aDragz.Features.RankupInfinite.Data.Storage.storeRanksInfinite;
import prisoncore.aDragz.Features.RankupInfinite.Events.giveRewards;
import prisoncore.aDragz.main;

import java.text.DecimalFormat;

import static prisoncore.aDragz.main.econ;

public class rankupInfinite implements CommandExecutor {
    private static final main plugin = main.getPlugin(main.class);

    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###,###,###,###");

    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        
        if (command.getName().equalsIgnoreCase("rankup")) {
            //grab money
            double playerBalance = econ.getBalance(player);
            
            //grab next tier
            Long nextRankTier = 1L + storeRanksInfinite.ranks.get(player.getUniqueId().toString());
            
            //grab price
            Long nextTierPrice = grabPrice.getRankPrice(nextRankTier);
            
            //Check to see if the Player can afford it
            if (playerBalance >= nextTierPrice) {
                rankupConfirm(player, nextRankTier, nextTierPrice);
            } else {
                rankupDecline(player, nextTierPrice, playerBalance);
            }
        }
        return false;
    }
    
    //If the player can afford rankup
    public void rankupConfirm(Player player, Long nextRankTier, Long nextTierPrice) {
        storeRanksInfinite.ranks.put(player.getUniqueId().toString(), nextRankTier);
        econ.withdrawPlayer(player, nextTierPrice);
        
        player.sendMessage(grabMessagesValue.type("rankup", "rankup").replaceAll("&", "§").replaceAll("LEVEL", String.valueOf(nextRankTier)));
        
        giveRewards.playerGiveRewards(player, nextRankTier);
    }
    
    //If the player cannot afford the rankup
    public void rankupDecline(Player player, Long nextPrice, double playerBalance) {
        player.sendMessage(grabMessagesValue.type("rankup", "norankup").replaceAll("&", "§").replaceAll("MONEY", decimalFormat.format((nextPrice - playerBalance))));
    }
}

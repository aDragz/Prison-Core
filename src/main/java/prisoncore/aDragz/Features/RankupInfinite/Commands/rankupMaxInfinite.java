package prisoncore.aDragz.Features.RankupInfinite.Commands;

import org.bukkit.Bukkit;
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

public class rankupMaxInfinite implements CommandExecutor {
    private static final main plugin = main.getPlugin(main.class);

    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###,###,###,###");


    int maxRankup = 1000;
    
    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        
        if (command.getName().equalsIgnoreCase("rankupmax")) {
            
            //grab money
            double playerBalance = econ.getBalance(player);
            
            Long currentPlayerTier = storeRanksInfinite.ranks.get(player.getUniqueId().toString());
            Long nextTier = currentPlayerTier;
            
            Long totalBalance = 0L;
            Long rankupTimes = 0L;

            try {
                while (totalBalance <= playerBalance) {
                    nextTier++;
                    Long nextPrice = grabPrice.getRankPrice(nextTier);
                    if ((totalBalance + nextPrice) <= playerBalance) {
                        if (rankupTimes == maxRankup) {
                            if (rankupTimes != 0) {
                                nextTier--;
                                rankupConfirm(player, nextTier, totalBalance);
                            } else {
                                double totalAmount = (totalBalance + nextPrice);
                                player.sendMessage(grabMessagesValue.type("rankup", "norankup").replaceAll("&", "§").replaceAll("MONEY", decimalFormat.format(totalAmount - playerBalance)));
                            }
                            break;
                        }
                        totalBalance = totalBalance + nextPrice;
                        rankupTimes++;
                        giveRewards.playerGiveRewards(player, nextTier);
                    } else {
                        if (rankupTimes != 0) {
                            nextTier--;
                            rankupConfirm(player, nextTier, totalBalance);
                        } else {
                            double totalAmount = (totalBalance + nextPrice);
                            player.sendMessage(grabMessagesValue.type("rankup", "norankup").replaceAll("&", "§").replaceAll("MONEY", decimalFormat.format(totalAmount - playerBalance)));
                        }
                        break;
                    }
                }
            } catch (NumberFormatException e) { //Catches out of range method
                player.sendMessage(grabMessagesValue.type("rankup", "norankup").replaceAll("&", "§").replaceAll("MONEY", "0"));
                Bukkit.getConsoleSender().sendMessage(e.getMessage());
            } catch (Exception exception) {
                Bukkit.getConsoleSender().sendMessage(exception.getMessage());
            }
        }
        
        return false;
    }
    
    public void rankupConfirm(Player player, Long nextRankTier, Long nextTierPrice) {
        storeRanksInfinite.ranks.put(player.getUniqueId().toString(), nextRankTier);
        econ.withdrawPlayer(player, nextTierPrice);
        
        player.sendMessage(grabMessagesValue.type("rankup", "rankup").replaceAll("&", "§").replaceAll("LEVEL", String.valueOf(nextRankTier)));
        return;
    }
}

package prisoncore.aDragz.Features.RankupStandard.Commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import prisoncore.aDragz.ConfigFiles.cache.ranksCache;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Sell.Multipliers.cmds.giveMultiplier;
import prisoncore.aDragz.Features.RankupStandard.Data.storeRanks;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class rankup implements CommandExecutor, Listener {
    
    /*
    Bascially, optimizedRanks is a copy of ranks, just coded more efficienctly
    For now, due to time. I am still using most of the same code. But gathering current rank
    is in a HashMap<> rather than grabbing in a file every operation. As I need to display
    current and next rank in PAPI.
     */
    
    private static final main plugin = main.getPlugin(main.class);
    
    
    static File cfgFile = new File(plugin.getDataFolder(), "config.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    static String rankupServerName = cfgYaml.getString("Type.Ranks.Server_Name");

    static String rankupTitleMessage = cfgYaml.getString("Type.Ranks.Rankup_Title.Message").replaceAll("&", "§");
    static String rankupTitleSubMessage = cfgYaml.getString("Type.Ranks.Rankup_Title.Sub_Message").replaceAll("&", "§");
    
    static int rankupTitleFadeIn = cfgYaml.getInt("Type.Ranks.Rankup_Title.fadeIn");
    static int rankupTitleFadeOut = cfgYaml.getInt("Type.Ranks.Rankup_Title.fadeOut");
    static int rankupTitleStay = cfgYaml.getInt("Type.Ranks.Rankup_Title.Stay");
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        
        if (command.getName().equalsIgnoreCase("rankup")) {
            //Temp ready for the loop
            
            ArrayList<String> rank = new ArrayList<>();
            
            //For final in loop
            String playerRank = storeRanks.ranks.get(player.getUniqueId().toString());
            
            
            int totalAmount = ranksCache.ranksFinalMessage.size();
            
            //
            //Check if player rank is equal to rank, or just skip to next rank.
            //After it is found, get 1  above, with cost and see if they can afford it.
            //
            for (int i = 0; totalAmount != i; ) {
                String[] split = ranksCache.ranksFinalMessage.get(i).split(" - ");
                
                if (Objects.requireNonNull(playerRank.equals(split[0].toUpperCase()))) {
                    String[] rankAboveArr = ranksCache.ranksFinalMessage.get(i += 1).split(" - "); //Rank above Split
                    
                    String rankAbove = rankAboveArr[0]; //Rank above the player
                    String priceAbove = rankAboveArr[1].replaceAll("\\D+", ""); //Gets rank above cost, goes into long later
                    
                    Long playerRankPrice = Long.valueOf(priceAbove);
                    double playerBalance = main.econ.getBalance(player);
                    
                    if (checkLong(priceAbove)) {
                        playerRankPrice = Long.valueOf(priceAbove);
                    }
                    
                    if ((playerRankPrice - playerBalance) <= 0) { //rank cost - player balance less or equal to 0
                        rankupAccept(player, playerRankPrice, main.econ, rankAbove);
                    } else {
                        rankupDecline(player, playerBalance, playerRankPrice, rankAbove);
                    }
                    return false;
                }
                i += 1;
            }
            
            return false;
        }
        return false;
    }
    
    //
    // Checks if string is just numbers. So it can convert into long.
    //
    public boolean checkLong(String string) {
        return string != null && string.matches("[0-9.]+");
    }
    
    public void rankupAccept(Player player, long rankPrice, Economy econ, String nextRankName) { //Balance = player balance. Econ = vault api
        econ.withdrawPlayer(player, rankPrice); //Take money away from player
        
        storeRanks.ranks.put(player.getUniqueId().toString(), nextRankName);
        
        //Run lp command to give the user the rank:
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        
        if (rankupServerName.equals("null")) {
            String consoleCommand = String.format("lp user %s permission set group.%s", player.getName(), nextRankName);
            Bukkit.dispatchCommand(console, consoleCommand);
        } else {
            String consoleCommand = String.format("lp user %s permission set group.%s", player.getName(), nextRankName + " " + rankupServerName);
            Bukkit.dispatchCommand(console, consoleCommand);
        }
        
        rankupTitle(player, nextRankName);
        
        //Give the player a 0.15 Multiplier.
        giveMultiplier.givePlayerMultiplier(player.getUniqueId(), 0.15F);
        
        //Requires: LuckPerms
        player.sendMessage(grabMessagesValue.type("rankup", "accept").replaceAll("&", "§")
                .replaceAll("NEXTRANKNAME", nextRankName) //Enter so it's easier to read instead of 1 massive line
                .replaceAll("RANKPRICE", String.valueOf(rankPrice))); //Makes into readable message from the user
    }
    
    public void rankupDecline(Player player, double balance, long rankPrice, String nextRankName) { //Balance = player balance. No need for vaultapi as we're not changing balance
        double money = (rankPrice - balance);
        //Requires: LuckPerms
        player.sendMessage(grabMessagesValue.type("rankup", "decline").replaceAll("&", "§")
                .replaceAll("NEXTRANKNAME", nextRankName) //Enter so it's easier to read instead of 1 massive line
                .replaceAll("PLAYERMINUSRANK", String.format("%,.2f", money))); //Makes into readable message from the user
    }
    
    //public void rankupTitle(Audience playerAudience) {
    //        final Component main = Component.text(cfg.getString("Type.Ranks.Rankup_Title.Text".replaceAll("&", "§")));
    //        final Component sub = Component.text(cfg.getString("Type.Ranks.Rankup_Title.SubText".replaceAll("&", "§")));
    //        final Title title = Title.title(main, sub);
    //
    //        playerAudience.showTitle(title);
//
    //
    //}
    
    public void rankupTitle(Player player, String nextRank) {
        player.sendTitle(rankupTitleMessage, rankupTitleSubMessage.replaceAll("NEXTRANK", nextRank), rankupTitleFadeIn, rankupTitleStay, rankupTitleFadeOut);
    }
}

package prisoncore.aDragz.Features.RankupInfinite.Events;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.RankupInfinite.Data.grabRankTier;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.List;

public class giveRewards {
    
    private static final main plugin = main.getPlugin(main.class);
    static File ranksFile = new File(plugin.getDataFolder(), "Configs/infiniteRanks.yml");
    static FileConfiguration ranksCfg = YamlConfiguration.loadConfiguration(ranksFile);
    
    public static void playerGiveRewards(Player player, Long rankNumber) {
        //Check if number is in infiniteRanks:
        
        try {
            int rankInt = grabRankTier.grabPlayerRank(rankNumber);
            
            if (ranksCfg.contains("Rewards." + rankInt)) {
                //Grab Commands
                List<String> commandList = ranksCfg.getStringList("Rewards." + rankInt + ".Commands");
                
                commandList.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("PLAYER", player.getName().toString())));
             }
        } catch (Exception e) {}
    }
}

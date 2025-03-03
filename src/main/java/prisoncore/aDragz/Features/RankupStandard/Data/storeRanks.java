package prisoncore.aDragz.Features.RankupStandard.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class storeRanks {

    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "ranks.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    static File Cachefile = new File(plugin.getDataFolder(), "Cache/RanksCache.yml");
    static FileConfiguration Cachecfg = YamlConfiguration.loadConfiguration(Cachefile);

    /*This will store all ranks on startup
     * this will be ran in main.java for quick access
     * on bootup. Can be changed by removing and adding
     * the player ID
     */
    public static HashMap<String, String> ranks = new HashMap<>();
    static ArrayList<String> ranksList = new ArrayList<>();

    public static void generateRanks() throws IOException {
        if (!cfgFile.exists()) { //check if the config doesn't exist
            /*
            If it doesn't, make hashMap nothing and create
            file with no data
            */

            ranks.clear();

            cfgYaml.save(cfgFile);
            return;
        }
        
        ranksList.addAll(Cachecfg.getStringList("Ranks.rankName"));

        //Make a for loop generating all perm multipliers.

        float totalPerm = cfgYaml.getStringList("Ranks").size();
        List<String> IDs = new ArrayList<>();

        IDs = cfgYaml.getStringList("Ranks");

        for (int i = 0; i != totalPerm; ) {
            String playerID = IDs.get(i);
            String rankName = cfgYaml.getString(String.format("%s", playerID));
            ranks.put(playerID, rankName);

            i += 1;
        }
    }
}

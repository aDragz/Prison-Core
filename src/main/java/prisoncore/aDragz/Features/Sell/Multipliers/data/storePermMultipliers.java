package prisoncore.aDragz.Features.Sell.Multipliers.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class storePermMultipliers {

    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "multipliers.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    /*This will store all multipliers on startup
     * this will be ran in main.java for quick access
     * on bootup. Can be changed by removing and adding
     * the player ID
     */
    public static HashMap<String, Float> permMultipliers = new HashMap<>();

    public static void generateMapPermMultiplier() throws IOException {
        if (!cfgFile.exists()) { //check if the config doesn't exist
            /*
            If it doesn't, make hashMap nothing and create 
            file with no data
            */

            permMultipliers.clear();

            cfgYaml.save(cfgFile);
            return;
        }

        //Make a for loop generating all perm multipliers.

        float totalPerm = cfgYaml.getStringList("Multipliers").size();
        List<String> IDs = new ArrayList<>();

        IDs = cfgYaml.getStringList("Multipliers");

        for (int i = 0; i != totalPerm; ) {
            String playerID = IDs.get(i);
            Double amount = cfgYaml.getDouble(String.format("%s", playerID));
            permMultipliers.put(playerID, Float.valueOf(String.valueOf(amount)));

            i += 1;
        }
    }

}

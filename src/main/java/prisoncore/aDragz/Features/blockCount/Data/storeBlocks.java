package prisoncore.aDragz.Features.blockCount.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class storeBlocks {

    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "blocksPlayer.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    /*This will store all multipliers on startup
     * this will be ran in main.java for quick access
     * on bootup. Can be changed by removing and adding
     * the player ID
     */
    public static HashMap<String, Long> blocks = new HashMap<>();

    public static void generateMapPermMultiplier() throws IOException {
        if (!cfgFile.exists()) { //check if the config doesn't exist
            /*
            If it doesn't, make hashMap nothing and create
            file with no data
            */

            blocks.clear();

            cfgYaml.save(cfgFile);
            return;
        }

        //Make a for loop generating all perm multipliers.

        float totalPerm = cfgYaml.getStringList("Blocks").size();
        List<String> IDs = new ArrayList<>();

        IDs = cfgYaml.getStringList("Blocks");

        for (int i = 0; i != totalPerm; ) {
            String playerID = IDs.get(i);
            Long amount = cfgYaml.getLong(String.format("%s", playerID));
            blocks.put(playerID, amount);

            i += 1;
        }
    }
}

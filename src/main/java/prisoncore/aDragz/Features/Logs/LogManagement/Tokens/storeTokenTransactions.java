package prisoncore.aDragz.Features.Logs.LogManagement.Tokens;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class storeTokenTransactions {

    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "token_transactions.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    /*This will store all multipliers on startup
     * this will be ran in main.java for quick access
     * on bootup. Can be changed by removing and adding
     * the player ID
     */
    public static HashMap<String, List<String>> transactions = new HashMap<>();

    public static void generateMapPermTransactions() throws IOException {
        if (!cfgFile.exists()) { //check if the config doesn't exist
            /*
            If it doesn't, make hashMap nothing and create
            file with no data
            */

            transactions.clear();

            cfgYaml.save(cfgFile);
            return;
        }

        //Make a for loop generating all perm multipliers.

        float totalPerm = cfgYaml.getStringList("IDs").size();
        ArrayList<String> IDs = new ArrayList<>();

        if (Objects.isNull(cfgYaml.getStringList("IDs"))) {
            return;
        }

        IDs.addAll(cfgYaml.getStringList("IDs"));

        for (int i = 0; i != totalPerm; ) {
            String playerID = IDs.get(i);

            List<String> playerIDArrayList = new ArrayList<>(Arrays.asList(cfgYaml.get(playerID.toString()).toString().replace("[", "").replace("]", "").split(", ")));

            transactions.put(playerID, playerIDArrayList);
            i += 1;
        }
    }
}

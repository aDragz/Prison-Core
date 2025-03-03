package prisoncore.aDragz.Features.Logs.LogManagement.Tokens;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class saveTokenTransactions {
    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "token_transactions.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    public static void autoSaveTransactions() throws IOException, NullPointerException {

        int totalPerm = storeTokenTransactions.transactions.size();
        List<String> permNames = new ArrayList<>(storeTokenTransactions.transactions.keySet());
        List<String> permValues = new ArrayList<>();

        permNames.forEach((l) -> permValues.add(storeTokenTransactions.transactions.get(l).toString()));

        for (int i = 0; i != totalPerm; ) {
            String permIGN = permNames.get(i);
            List<String> value = storeTokenTransactions.transactions.get(permIGN);

            if (Objects.nonNull(value)) {
                permValues.add(value.toString());
            }

            i += 1;
        }

        if (totalPerm != 0) { //Check if it has any data. If not, cancel.
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saving Transactions");

            cfgYaml.set("IDs", storeTokenTransactions.transactions.keySet().toArray());

            for (int i = 0; i != totalPerm; ) {
                cfgYaml.set(String.valueOf(permNames.get(i)), permValues.get(i));
                i += 1;
            }


            cfgYaml.save(cfgFile);

            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved Transactions");

            return;
        }
    }
}

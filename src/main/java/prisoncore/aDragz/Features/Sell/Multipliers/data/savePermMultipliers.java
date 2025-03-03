package prisoncore.aDragz.Features.Sell.Multipliers.data;

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

public class savePermMultipliers {
    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "multipliers.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    public static boolean autoSavePermMultiplier() throws IOException {

        int totalPerm = storePermMultipliers.permMultipliers.size();
        List<String> permNames = new ArrayList<String>(storePermMultipliers.permMultipliers.keySet());
        List<Float> permValues = new ArrayList<>();

        permNames.forEach((l) -> permValues.add(storePermMultipliers.permMultipliers.get(l)));

        for (int i = 0; i != totalPerm; ) {
            String permIGN = permNames.get(i);
            float value = storePermMultipliers.permMultipliers.get(permIGN);

            if (Objects.isNull(value)) {
                value = 1F;
            }

            permValues.add(value);

            i += 1;
        }

        if (totalPerm != 0) { //Check if it has any data. If not, cancel.
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saving permanent Multipliers");

            cfgYaml.set("Multipliers", storePermMultipliers.permMultipliers.keySet().toArray());

            for (int i = 0; i != totalPerm; ) {
                cfgYaml.set(permNames.get(i), permValues.get(i));
                i += 1;
            }


            cfgYaml.save(cfgFile);

            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved permanent Multipliers");

            return true;
        }

        return false;
    }
}

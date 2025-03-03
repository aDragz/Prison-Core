package prisoncore.aDragz.Features.RankupInfinite.Data.Storage;

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

public class saveRanksInfinite {
    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "infiniteranks.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    public static boolean autoSaveRanks() throws IOException, NullPointerException {

        int totalRanks = storeRanksInfinite.ranks.size();
        List<String> permNames = new ArrayList<>(storeRanksInfinite.ranks.keySet());
        List<Long> permRank = new ArrayList<>();

        permNames.forEach((s) -> permRank.add(storeRanksInfinite.ranks.get(s)));

        for (int i = 0; i != totalRanks; ) {
            String permIGN = permNames.get(i);
            Long rankName = storeRanksInfinite.ranks.get(permIGN);

            if (Objects.isNull(rankName)) {
                rankName = 1L;
            }

            permRank.add(rankName);

            i += 1;
        }

        if (totalRanks != 0) { //Check if it has any data. If not, cancel.
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saving ∞ Ranks");

            cfgYaml.set("Ranks", storeRanksInfinite.ranks.keySet().toArray());

            for (int i = 0; i != totalRanks; ) {
                cfgYaml.set(String.valueOf(permNames.get(i)), permRank.get(i));
                i += 1;
            }


            cfgYaml.save(cfgFile);

            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved ∞ Ranks");

            return true;
        }
        return false;
    }
}

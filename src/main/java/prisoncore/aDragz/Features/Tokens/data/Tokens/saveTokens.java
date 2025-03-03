package prisoncore.aDragz.Features.Tokens.data.Tokens;

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

public class saveTokens {
    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "tokens.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    public static void autoSaveTokens() throws IOException, NullPointerException {

        int totalPerm = storeTokens.tokens.size();
        List<String> permNames = new ArrayList<>(storeTokens.tokens.keySet());
        List<Long> permValues = new ArrayList<>();

        permNames.forEach((l) -> permValues.add(storeTokens.tokens.get(l)));

        for (int i = 0; i != totalPerm; ) {
            String permIGN = permNames.get(i);
            Long value = storeTokens.tokens.get(permIGN);

            if (Objects.isNull(value)) {
                value = 0L;
            }

            permValues.add(value);

            i += 1;
        }

        if (totalPerm != 0) { //Check if it has any data. If not, cancel.
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saving Tokens");

            cfgYaml.set("Tokens", storeTokens.tokens.keySet().toArray());

            for (int i = 0; i != totalPerm; ) {
                cfgYaml.set(String.valueOf(permNames.get(i)), permValues.get(i));
                i += 1;
            }


            cfgYaml.save(cfgFile);

            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved Tokens");

            return;
        }
    }
}

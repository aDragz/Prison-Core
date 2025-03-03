package prisoncore.aDragz.Features.Gangs.management.gangChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class activeGangChat {
    private static final main plugin = main.getPlugin(main.class);

    private static final File cfgFile = new File(plugin.getDataFolder(), "data/activeGangsChat.yml");
    private static final FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    /*This will store all multipliers on startup
     * this will be ran in main.java for quick access
     * on bootup. Can be changed by removing and adding
     * the player ID
     */
    public static List<Long> IDsList = new ArrayList<>();

    public static void generateList() {

        //Make a for loop generating all block prices.

        IDsList.addAll(cfgYaml.getLongList("IDs"));
        Bukkit.getConsoleSender().sendMessage("[Prison-Core] " + ChatColor.GREEN + "Gang Chat List Generated!");
    }

    public static boolean saveList() throws IOException {
        if (cfgFile.exists()) {
            Bukkit.getConsoleSender().sendMessage("[Prison-Core] " + ChatColor.RED + "Gang Chat Deleted!");

            cfgFile.delete();
        }
        cfgYaml.set("IDs", IDsList);
        cfgYaml.save(cfgFile);

        Bukkit.getConsoleSender().sendMessage("[Prison-Core] " + ChatColor.GREEN + "Gang Chat Saved!");

        return true;
    }
}

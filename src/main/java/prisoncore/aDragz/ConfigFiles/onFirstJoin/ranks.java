package prisoncore.aDragz.ConfigFiles.onFirstJoin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;


public class ranks implements EventListener, Listener {
    //
    //Ranks for creating file on server start
    //

    private final static main plugin = main.getPlugin(main.class);
    private final static FileConfiguration config = plugin.getConfig();

    public static File file;
    public static FileConfiguration cfg;

    public static boolean checkConfig() throws IOException {
        //In /Configs as there may be 500+ features,
        //and easy to add more in the future
        file = new File(plugin.getDataFolder(), "Configs/ranks.yml");
        cfg = YamlConfiguration.loadConfiguration(file);


        if (config.getBoolean("Features.Ranks.Enabled")) { //Check if ranks are enabled from the config file
            if (!(file.exists())) {
                createFile(file, cfg);
                return true;
            }
            return true;
        }
        return false;
    }

    public static boolean createFile(File file, FileConfiguration config) throws IOException {
        //Names of Default ranks
        //Can be changed, removed or added in /configs/ranks.yml
        List<String> ranksName = Arrays.asList("Default", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

        cfg.set("Ranks.Name", ranksName);
        cfg.save(file);

        int i = 0;

        String name;  //Rank name
        String nName; //Next name
        String cName; //Custom name [Default only (May change at later update)]

        long price;   //Price of the rank

        //This will set the price of each rank.
        price = 0;

        ArrayList<String> Permissions = new ArrayList<>();
        ArrayList<String> aRankPermissions = new ArrayList<>();
        ArrayList<String> bRankPermissions = new ArrayList<>();

        Permissions.add(0, "Warps.A");
        Permissions.add(1, "PrisonCore.fly");
        Permissions.add(2, "Warps.B");
        Permissions.add(3, "Warps.pvp");

        aRankPermissions.add(Permissions.get(0));
        aRankPermissions.add(Permissions.get(1));

        bRankPermissions.add(Permissions.get(2));
        bRankPermissions.add(Permissions.get(3));

        name = ranksName.get(i);      //Rank name
        nName = ranksName.get(i + 1); //Next name

        cName = "A";

        //Sets rank name and price in config
        cfg.set("Ranks.Name." + name.toUpperCase() + ".Info.Custom_Name", cName);
        cfg.set("Ranks.Name." + name.toUpperCase() + ".Info.Price", price);

        cfg.set("Ranks.Name." + name.toUpperCase() + ".Info.Permissions", aRankPermissions);

        //Sets current and previous rank
        cfg.set("Ranks.Name." + name.toUpperCase() + ".Next_Rank", nName);

        cfg.save(file); //throws IOException hence no try event.

        //Displays message to console to show what this script is doing.
        //And so they know if they like the prices or not.
        //Can easily change in /configs/ranks.yml
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + cName + "§f - §a$§f" + price + "§f - DEFAULT RANK");

        for (i = 1; i <= 25; ) {
            //This will set the price of each rank.
            price = (long) (1000000 * (i * 1.75));

            name = ranksName.get(i);      //Rank name
            if (i == 25) {
                nName = "null";
            } else {
                nName = ranksName.get(i + 1); //Next name
            }

            //Sets rank name and price in config
            cfg.set("Ranks.Name." + name.toUpperCase() + ".Info.Price", price);

            //Sets current and previous rank
            cfg.set("Ranks.Name." + name.toUpperCase() + ".Next_Rank", nName);

            if (i == 1) {
                cfg.set("Ranks.Name." + name.toUpperCase() + ".Info.Permissions", bRankPermissions);
            }

            cfg.save(file); //throws IOException hence no try event.


            //Displays message to console to show what this script is doing.
            //And so they know if they like the prices or not.
            //Can easily change in /configs/ranks.yml
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + name.toUpperCase() + "§f - §a$§f" + price + "§f");

            i += 1;
        }


        return true;
    }
}

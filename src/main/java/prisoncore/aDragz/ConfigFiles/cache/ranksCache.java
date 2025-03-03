package prisoncore.aDragz.ConfigFiles.cache;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import prisoncore.aDragz.ConfigFiles.onFirstJoin.ranks;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ranksCache implements Listener {
    private static final main plugin = main.getPlugin(main.class);
    public static ArrayList<String> ranksFinalMessage = new ArrayList<>(); //ranks final message

    public static void Cache() throws IOException {
        File Ranksfile = new File(plugin.getDataFolder(), "Configs/ranks.yml");
        FileConfiguration Rankscfg = YamlConfiguration.loadConfiguration(Ranksfile);

        File Cachefile = new File(plugin.getDataFolder(), "Cache/RanksCache.yml");
        FileConfiguration Cachecfg = YamlConfiguration.loadConfiguration(Cachefile);

        //Check if Ranks file != exists.
        if (!(Ranksfile.exists())) {
            ranks.checkConfig();
        }

        //Check if Cache file exists.
        if (Cachefile.exists()) {
            Cachefile.delete();
            Bukkit.getConsoleSender().sendMessage("Deleted " + Cachefile.getAbsolutePath().toUpperCase());
        }

        ArrayList<String> ranks = new ArrayList<>(); //Ranks Name
        //Rank Price - Long due to high range, int = few mill.
        // Whereas long is way  longer, hence the name.
        ArrayList<Long> price = new ArrayList<>();

        ranks.add(Rankscfg.getString("Ranks.Name.DEFAULT.Info.Custom_Name"));

        String nName = ""; //next Name
        boolean nextRankNotNull = true; //Get next rank name

        long rPrice = 0; //rank Price [not price due to the array name], ArrayList<Long>

        nName = Rankscfg.getString("Ranks.Name.DEFAULT.Next_Rank");
        rPrice = Rankscfg.getLong("Ranks.Name.DEFAULT.Info.Price");
        price.add(rPrice); //adding here as it changes instantly in the while loop

        while (nextRankNotNull) {
            //up here as it's current, not next rank.
            rPrice = Rankscfg.getLong("Ranks.Name." + nName + ".Info.Price");

            ranks.add(nName.toUpperCase()); //adds to array
            price.add(rPrice); //adds to array

            nName = Rankscfg.getString("Ranks.Name." + nName + ".Next_Rank");

            if (nName.equalsIgnoreCase("null")) {
                nextRankNotNull = false;

                //saves array
                Cachecfg.set("Ranks.rankName", ranks);
                Cachecfg.set("Ranks.rankPrice", price);
                Cachecfg.save(Cachefile);
            }
        }

        createCommand(Cachefile, Cachecfg);
    }

    public static void createCommand(File cacheFile, FileConfiguration cacheCfg) {
        HashMap<String, Long> ranksCmd = new HashMap<>(); //Rank name + Price

        List<String> ranksName = cacheCfg.getStringList("Ranks.rankName");
        List<Long> ranksPrice = cacheCfg.getLongList("Ranks.rankPrice");

        ArrayList<String> ranksNameArr = new ArrayList<>();
        ArrayList<Long> ranksPriceArr = new ArrayList<>();

        ranksNameArr.addAll(ranksName);
        ranksPriceArr.addAll(ranksPrice);

        int totalRank = ranksNameArr.size(); //int as I'm sure no-one will have 2m+ ranks
        int totalPrice = ranksPriceArr.size();

        ArrayList<String> message = new ArrayList<>(); //ranks load message

        if ((totalRank - totalPrice) == 0) { //checks it's equal.

            for (int i = 0; i != totalRank; ) { //so it goes from 0-max instead of reverse

                String curRank = ranksNameArr.get(i);
                Long curPrice = ranksPriceArr.get(i);

                ranksCmd.put(curRank, curPrice);

                message.add(i, (curRank + " - " + ChatColor.GREEN + "$" + ChatColor.WHITE + curPrice));
                if ((i + 1) == totalRank) {
                    ranksFinalMessage.addAll(message);
                }

                i += 1;
            }
        }
    }
}

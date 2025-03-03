package prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;

public class BLOCKS {
    private static final main plugin = main.getPlugin(main.class);

    public static ArrayList<String> generateBlockLore(ArrayList<String> loreList, Player player) {
        ArrayList<String> LoreNew = new ArrayList<>();
        
        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangCheckInfo.grabGangName((Player) player)));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

        Material currentBlock = private_mines_grabValues.currentBlock(player, gangCfg); //Grab current tier from player

        for (String newLoreS : loreList) {
            newLoreS = newLoreS.replaceAll("&", "§")
                    .replaceAll("BLOCK", currentBlock + "");

            LoreNew.add(newLoreS);
        }
        return LoreNew;
    }
    
    public static Material returnBlock(Player player) {
        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangCheckInfo.grabGangName((Player) player)));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);
        
        return private_mines_grabValues.currentBlock(player, gangCfg); //Grab current tier from player
    }
}

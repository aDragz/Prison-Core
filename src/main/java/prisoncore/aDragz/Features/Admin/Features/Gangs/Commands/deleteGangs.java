package prisoncore.aDragz.Features.Admin.Features.Gangs.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class deleteGangs {

    private static final main plugin = main.getPlugin(main.class);


    private static File[] gangFile = new File(plugin.getDataFolder(),"Gangs").listFiles();
    private static File[] playerFileList = new File(plugin.getDataFolder(),"PlayerData").listFiles();

    public static boolean deleteAllGangs() throws IOException {

        //Grab all files for player data:
        for (int i = 0; i != playerFileList.length; ) {

            File playerFile = new File(playerFileList[i].toString());
            FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

            //playerCfg.set("Info.Gang.Invites", null);
            playerCfg.set("Info.Gang.Name", null);
            playerCfg.set("Info.Gang.Invites", null);

            playerCfg.save(playerFile);

            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Deleted gang data for " + playerFileList[i]);

            i++;
        }

        for (int i = 0; i != gangFile.length; ) {
            File gangiFile = new File(gangFile[i].toString());
            gangiFile.delete();

            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Deleted gang file for " + gangFile[i]);

            i++;
        }

        return true;
    }
}

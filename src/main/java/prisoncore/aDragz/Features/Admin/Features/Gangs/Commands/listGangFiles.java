package prisoncore.aDragz.Features.Admin.Features.Gangs.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.main;

import java.io.File;

public class listGangFiles {
    private static final main plugin = main.getPlugin(main.class);

    public static boolean listFiles(CommandSender player) {
        try {

            File[] gangFile = new File(plugin.getDataFolder(), "Gangs").listFiles();

            if (gangFile.length == 0) {
                return false;
            }

            player.sendMessage(ChatColor.GREEN + "Location: " + gangFile[0].getAbsolutePath().replaceAll(gangFile[0].getName(), ""));

            for (int i = 0; i != gangFile.length; ) {
                player.sendMessage(gangFile[i].getName());
                i++;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

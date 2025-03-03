package prisoncore.aDragz.Features.Admin.Features.Config;

import org.bukkit.command.CommandSender;
import prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.autoUpdater;

import java.io.IOException;

public class adminUpdateCommandManager {

    public static void adminUpdateCommands(CommandSender player) throws IOException {
        autoUpdater.setConfigFiles(player);
    }
}

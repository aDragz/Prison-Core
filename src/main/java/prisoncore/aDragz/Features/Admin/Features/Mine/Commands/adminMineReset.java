package prisoncore.aDragz.Features.Admin.Features.Mine.Commands;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.PrivateMines.PMineCommandHandler;
import prisoncore.aDragz.main;

public class adminMineReset {

    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();

    public static int mineHeight = (config.getInt("Type.Private_Mine.Height")); //Grab default height to then minus the height of it.
    public static long xOffset = (config.getInt("Type.Private_Mine.tp_Offset.X"));
    public static long yOffset = (config.getInt("Type.Private_Mine.tp_Offset.Y"));
    public static long zOffset = (config.getInt("Type.Private_Mine.tp_Offset.Z"));

    public static void adminMineReset(Player admin, String player) {
        //To do this, we need for first grab gang id. From this, we can calculate the x,y,z values.

        PMineCommandHandler.resetMine(Bukkit.getOfflinePlayer(player), true, true);

        admin.sendMessage(grabMessagesValue.type("mine", "reset_admin").replaceAll("&", "§").replaceAll("PLAYER", player));
    }
}

package prisoncore.aDragz.Features.Admin.Features.Mine.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.main;

import java.util.Objects;

import static prisoncore.aDragz.data.private_mines_grabValues.grabGangID;

public class adminMineTp {

    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();

    public static int mineHeight = (config.getInt("Type.Private_Mine.Height")); //Grab default height to then minus the height of it.
    public static long xOffset = (config.getInt("Type.Private_Mine.tp_Offset.X"));
    public static long yOffset = (config.getInt("Type.Private_Mine.tp_Offset.Y"));
    public static long zOffset = (config.getInt("Type.Private_Mine.tp_Offset.Z"));

    public static void adminmineTp(Player admin, String player) {
        //To do this, we need for first grab gang id. From this, we can calculate the x,y,z values.

        int id = grabGangID(Bukkit.getOfflinePlayer(player));

        byte x = 0; //just 0, no need for anymore.
        long y = mineHeight + 3; //This is from UpgradesCfg because it needs to be in height with the mine.
        long z = ((config.getLong("Type.Private_Mine.Width") * 2) * id); //This is from the config, because it needs to location. Due to the schematic creations.

        if (z == 0) {
            admin.sendMessage(grabMessagesValue.type("admin", "info_gangs_deleted").replaceAll("&", "§"));
            return;
        }

        World world = Bukkit.getWorld(Objects.requireNonNull(config.getString("Type.Private_Mine.World")));

        //Add 3 to Y so it goes slightly above the mine
        Location tpLoc = new Location(world, x + xOffset, y + yOffset, z + zOffset);

        admin.teleport(tpLoc);

        admin.sendMessage(grabMessagesValue.type("mine", "tp_admin").replaceAll("&", "§").replaceAll("PLAYER", player));
    }
}

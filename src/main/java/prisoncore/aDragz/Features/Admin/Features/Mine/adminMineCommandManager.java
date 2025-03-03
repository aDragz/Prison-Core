package prisoncore.aDragz.Features.Admin.Features.Mine;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Admin.Features.Mine.Commands.Management.setMineWorld;
import prisoncore.aDragz.Features.Admin.Features.Mine.Commands.Management.setSchematic;
import prisoncore.aDragz.Features.Admin.Features.Mine.Commands.adminMineReset;
import prisoncore.aDragz.Features.Admin.Features.Mine.Commands.adminMineTp;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class adminMineCommandManager {

    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();
    static File upgradesFile = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    static FileConfiguration upgradesCfg = YamlConfiguration.loadConfiguration(upgradesFile);

    public static void adminPickaxeCommands(CommandSender player, String[] args) throws IOException {

        switch (args[1].toLowerCase()) {
            case "tp": //Resets all gangs
                if (player.hasPermission("PrisonCore.Admin.Mine.Tp")) {
                    adminMineTp.adminmineTp((Player) player, args[2]);
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Mine.Tp"));
                    return;
                }
                break;
            case "reset":
                if (player.hasPermission("PrisonCore.Admin.Mine.Reset")) {
                    adminMineReset.adminMineReset((Player) player, args[2]);
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Mine.Tp"));
                }

                break;
            case "setworld":
                if (player.hasPermission("PrisonCore.Admin.Mine.SetWorld")) {
                    Player playerPlayer = (Player) player;
                    try {
                        //args world?
                        setMineWorld.setWorld(player, Bukkit.getWorld(args[2]).getName());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        setMineWorld.setWorld(player, playerPlayer.getWorld().getName());
                    } catch (NullPointerException e) {
                        player.sendMessage(grabMessagesValue.type("admin", "unknown_world").replaceAll("&", "§").replaceAll("WORLD", args[2]));
                    }
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Mine.SetWorld"));
                }
                break;
            case "setschematic":
                if (player.hasPermission("PrisonCore.Admin.Mine.SetSchematic")) {
                    if (args.length != 3) {
                        player.sendMessage(grabMessagesValue.type("admin", "schem_no_args").replaceAll("&", "§"));
                        return;
                    }
                    setSchematic.setSchematic(player, args[2].toString());
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Mine.SetSchematic"));
                }
                break;
            default:
                player.sendMessage(grabMessagesValue.type("admin", "unknown_feature").replaceAll("&", "§"));
        }
    }
}

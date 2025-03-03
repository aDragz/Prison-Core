package prisoncore.aDragz.Features.Admin.Features.InfiniteRankup;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Admin.Features.InfiniteRankup.Commands.resetTierRank;
import prisoncore.aDragz.Features.Admin.Features.InfiniteRankup.Commands.saveRank;
import prisoncore.aDragz.Features.Admin.Features.InfiniteRankup.Commands.setTierRank;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class adminRankupCommandManager {

    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();
    static File upgradesFile = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    static FileConfiguration upgradesCfg = YamlConfiguration.loadConfiguration(upgradesFile);

    public static void adminRankupCommands(CommandSender player, String[] args) throws IOException {

        switch (args[1].toLowerCase()) {
            case "reset": //Resets all gangs
                if (player.hasPermission("PrisonCore.Admin.Rankup.Reset")) {
                    resetTierRank.resetRank((Player) player, Bukkit.getOfflinePlayer(args[2]));
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Rankup.Reset"));
                    return;
                }
                break;
            case "set":
                if (player.hasPermission("PrisonCore.Admin.Rankup.Set")) {
                    setTierRank.setRank((Player) player, Bukkit.getOfflinePlayer(args[2]), Long.valueOf(args[3]));
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Rankup.Set"));
                    return;
                }
                break;
            case "save":
                if (player.hasPermission("PrisonCore.Admin.Rankup.Save")) {
                    saveRank.saveRankup(player);
                } else {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Admin.Rankup.Save"));
                    return;
                }
                break;
            default:
                player.sendMessage(grabMessagesValue.type("admin", "unknown_feature").replaceAll("&", "§"));
        }
    }
}

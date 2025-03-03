package prisoncore.aDragz.Features.Admin.Features.Gangs.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class checkGangDetails {
    private static final main plugin = main.getPlugin(main.class);

    public static boolean checkDetails(CommandSender player, String gangName) {
        File gangFile = new File(plugin.getDataFolder(),"Gangs/" + gangName + ".yml");
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

        if (!gangFile.exists())
            return false;

        //Check if gang is null, if so it will display "gang deleted" message:
        if (Objects.isNull(gangCfg.getString("Info.Leader"))) {
            player.sendMessage(grabMessagesValue.type("admin", "info_gangs_deleted").replaceAll("&", "§"));
            return true;
        }

        //Grab values.
        String leader = gangCfg.getString("Info.Leader");
        int id = gangCfg.getInt("Info.ID");

        ArrayList<String> members = new ArrayList<>();
        members.addAll(gangCfg.getStringList("Members"));

        ArrayList<String> gangMembersUUID = new ArrayList<>();
        gangMembersUUID.addAll(gangCfg.getStringList("Members"));

        ArrayList<String> gangMembers = new ArrayList<>();
        gangMembers.addAll(gangCheckInfo.IDtoIGN(gangMembersUUID));

        ArrayList<String> invites = new ArrayList<>();
        invites.addAll(gangCfg.getStringList("Invites"));

        int mineTier = gangCfg.getInt("Mine.Tier");

        player.sendMessage(ChatColor.GREEN + "Leader: " + ChatColor.WHITE + leader);
        player.sendMessage(ChatColor.GREEN + "ID: " + ChatColor.WHITE + id);
        player.sendMessage(ChatColor.GREEN + "Mine Tier: " + ChatColor.WHITE + mineTier);
        player.sendMessage(ChatColor.GREEN + "Members: " + ChatColor.WHITE + gangMembers);
        player.sendMessage(ChatColor.GREEN + "Invites: " + ChatColor.WHITE + invites);
        return true;
    }
}

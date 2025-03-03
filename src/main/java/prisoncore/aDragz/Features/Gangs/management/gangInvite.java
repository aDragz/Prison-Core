package prisoncore.aDragz.Features.Gangs.management;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class gangInvite {

    private final static main plugin = main.getPlugin(main.class);

    static String message;

    public static void gangInvite(Player player, String[] args) throws IOException {

        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);


        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", playerCfg.getString("Info.Gang.Name")));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);
        String leader = gangCfg.getString("Info.Leader");

        if (!player.getName().equals(leader)) {
            player.sendMessage(grabMessagesValue.type("gang", "kick_not_leader").replaceAll("&", "§"));
            return;
        }

        //Check args to see if player is even inviting anyone

        if (args.length <= 1) {
            message = grabMessagesValue.type("gang", "invite_no_name").replaceAll("&", "§");
            player.sendMessage(message);
            return;
        }

        Player player2 = Bukkit.getPlayer(args[1]);

        if ((player2 == null) || !(player2.isOnline())) {
            message = grabMessagesValue.type("gang", "invite_offline").replaceAll("&", "§")
                    .replaceAll("IGN2", args[1]);
            player.sendMessage(message);
            return;
        }

        File player2File = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player2.getUniqueId()));
        FileConfiguration player2Cfg = YamlConfiguration.loadConfiguration(player2File);

        ArrayList<String> invites = new ArrayList<>();

        //Check if player2 is already in a gang
        try {
            if (Objects.requireNonNull(player2Cfg.getString("Info.Gang.Name")).length() != 0) {
                message = grabMessagesValue.type("gang", "invite_already_in_gang").replaceAll("&", "§")
                        .replaceAll("IGN2", player2.getName());
                player.sendMessage(message);
                return;
            }
        } catch (NullPointerException e) {
            //Not in gang

            //Unsure if this is stable, first time coding like that properly.
        }

        //Check now, could 1/2 the server load.



        if (player2Cfg.getStringList("Info.Gang.Invites").size() != 0) {
            invites.addAll(player2Cfg.getStringList("Info.Gang.Invites"));
        }

        String playerGangName = Objects.requireNonNull(playerCfg.getString("Info.Gang.Name"));

        //Check if player has already been invited
        if (invites.contains(playerGangName)) {
            message = grabMessagesValue.type("gang", "invite_already_sent").replaceAll("&", "§")
                    .replaceAll("IGN2", player2.getName());
            player.sendMessage(message);
            return;
        }

        //should not be invited, safe to add to list without errors.

        invites.add(playerGangName);

        player2Cfg.set("Info.Gang.Invites", invites);
        player2Cfg.save(player2File);

        invites.clear(); //used previously

        invites.addAll(gangCfg.getStringList("Invites"));
        invites.add(player2.getName());

        gangCfg.set("Invites", invites);
        gangCfg.save(gangFile);

        message = grabMessagesValue.type("gang", "invite_sent").replaceAll("&", "§")
                .replaceAll("IGN2", player2.getName());
        player.sendMessage(message);


        player2.sendMessage(grabMessagesValue.type("gang", "invite_receive").replaceAll("&", "§")
                .replaceAll("IGN", player.getName()));

        player2.sendMessage(grabMessagesValue.type("gang", "invite_receive_command").replaceAll("&", "§")
                .replaceAll("GANGNAME", gangCheckInfo.grabGangName(player)));
    }
}

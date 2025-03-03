package prisoncore.aDragz.Features.Gangs.management;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.Features.Sell.Multipliers.cmds.giveMultiplier;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static prisoncore.aDragz.data.gang_grabValues.gangLeader;

public class gangJoin {
    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();

    public static void gangJoin(Player player, String[] args) throws IndexOutOfBoundsException {
        //Check if args is correct
        if (args.length == 1) {
            player.sendMessage(grabMessagesValue.type("gang", "invite_no_gang_name").replaceAll("&", "§"));
            return;
        }

        String joinGangName = args[1];

        //Check if player has invite to gang

        ArrayList<String> gangInviteList = new ArrayList<>();
        gangInviteList.addAll(gangCheckInfo.grabPlayerInvites(player));

        if (!gangInviteList.contains(args[1])) {
            player.sendMessage(grabMessagesValue.type("gang", "invite_no_gang_found").replaceAll("&", "§")
                    .replaceAll("GANGNAME", args[1]));

            return;
        }

        //check if player is alr in gang

        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

        try {
            if (Objects.requireNonNull(playerCfg.getString("Info.Gang.Name")).length() != 0) {
                player.sendMessage(grabMessagesValue.type("gang", "invite_already_in_gang").replaceAll("&", "§")
                        .replaceAll("IGN2", player.getName()));
                return;
            }
        } catch (NullPointerException e) {
            //empty, good.
        }

        //gang invites - check if name is in invites in gang and player invites
        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", joinGangName));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

        if (!gangFile.exists()) { //check if it exists or not
            player.sendMessage(grabMessagesValue.type("gang", "invite_no_gang_found").replaceAll("&", "§")
                    .replaceAll("GANGNAME", args[1]));

            ArrayList<String> invites = new ArrayList<>(playerCfg.getStringList("Info.Gang.Invites"));

            gangCheckInfo.clearInviteName(player, joinGangName, invites, playerFile, playerCfg, "Info.Gang.Invites");

            return;
        }

        //Check if the gang has available spaces

        ArrayList<String> gangMembers = new ArrayList<>();
        gangMembers.addAll(gangCfg.getStringList("Members"));

        int amount = gangMembers.size();
        int total = config.getInt("Type.Gangs.Max_Members");

        if (amount == total) {
            player.sendMessage(grabMessagesValue.type("gang", "max_members").replaceAll("&", "§"));
            return;
        }

        //gang join [name]

        //Grab gang member list, and add player

        gangMembers.add(player.getUniqueId().toString());

        playerCfg.set("Info.Gang.Name", joinGangName); //Set playerID.yml gang name
        gangCfg.set("Members", gangMembers);

        try {
            playerCfg.save(playerFile);
            gangCfg.save(gangFile);

            //add player to plot
            String leader = gangLeader(player);

            gangAddPlotMember.addPlayerPlotRegion(leader, player);

            player.sendMessage(grabMessagesValue.type("gang", "invite_join").replaceAll("&", "§")
                    .replaceAll("GANGNAME", joinGangName));

            gangCheckInfo.clearInvites(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        int mineTier = gangCfg.getInt("Mine.Tier");
        giveMultiplier.givePlayerMultiplier(player.getUniqueId(), (0.30F * mineTier)-0.30F);
    }
}

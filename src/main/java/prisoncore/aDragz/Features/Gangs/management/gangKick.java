package prisoncore.aDragz.Features.Gangs.management;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Sell.Multipliers.cmds.giveMultiplier;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class gangKick {
    private static final main plugin = main.getPlugin(main.class);

    public static void kick(Player player, String[] args) throws IOException {
        try {
            if (Objects.isNull(args[1])) {
                player.sendMessage(grabMessagesValue.type("gang", "kick_no_name").replaceAll("&", "§"));
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) { //Removes in-game error
            player.sendMessage(grabMessagesValue.type("gang", "kick_no_name").replaceAll("&", "§"));
            return;
        }
        OfflinePlayer player2 = Bukkit.getOfflinePlayer((args[1])); //gang[-1] kick[0] (player)[1]
        File leaderFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
        FileConfiguration leaderCfg = YamlConfiguration.loadConfiguration(leaderFile);
        
        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", leaderCfg.getString("Info.Gang.Name")));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);
        String leader = gangCfg.getString("Info.Leader");
        
        if (!player.getName().equals(leader)) {
            player.sendMessage(grabMessagesValue.type("gang", "kick_not_leader").replaceAll("&", "§"));
            return;
        }
        
        //Check if leader
        if (player2.getName().equals(leader)) {
            player.sendMessage(grabMessagesValue.type("gang", "kick_unavailable").replaceAll("&", "§"));
            return;
        }
        
        String gangName = Objects.requireNonNull(gangFile.getName().trim());
        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player2.getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);
        try {
            if ((playerCfg.getString("Info.Gang.Name")).equals(gangName.replaceAll(".yml", "").toLowerCase())) { //Double checks just incase of anything so it doesn't randomly kick them from a gang.
                playerCfg.set("Info.Gang.Name", null);
                
                ArrayList<String> gangMembersUUID = new ArrayList<>(); //So I can make it so they are not in a gang.
                gangMembersUUID.addAll(gangCfg.getStringList("Members"));
                
                ArrayList<String> newGangMembersUUID = new ArrayList<>();
                
                for (String UUID : gangMembersUUID) {
                    if (!player2.getUniqueId().toString().equals(UUID)) {
                        newGangMembersUUID.add(UUID);
                    }
                }
                gangCfg.set("Members", newGangMembersUUID);
                playerCfg.save(playerFile);
                gangCfg.save(gangFile);
                player.sendMessage(grabMessagesValue.type("gang", "kick_success").replaceAll("&", "§"));
                
                //Remove multiplier
                try {
                    int mineTier = gangCfg.getInt("Mine.Tier");
                    giveMultiplier.givePlayerMultiplier(player2.getUniqueId(), (-0.30F * mineTier) + 0.30F);
                } catch (Exception Ignored) {
                }
            } else {
                player.sendMessage(grabMessagesValue.type("gang", "kick_unavailable").replaceAll("&", "§"));
            }
        } catch (Exception e){
            player.sendMessage(grabMessagesValue.type("gang", "kick_unavailable").replaceAll("&", "§").replaceAll("MESSAGE", e.getMessage()));
        }
    }
}
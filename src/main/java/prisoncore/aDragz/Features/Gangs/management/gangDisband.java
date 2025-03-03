package prisoncore.aDragz.Features.Gangs.management;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.Features.PrivateMines.regions.mine_blocks.deleteRegion;
import prisoncore.aDragz.Features.PrivateMines.regions.mine_plots.deletePlotsRegion;
import prisoncore.aDragz.Features.Sell.Multipliers.cmds.giveMultiplier;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class gangDisband {
    private static final main plugin = main.getPlugin(main.class);
    private static final FileConfiguration config = plugin.getConfig();
    
    
    public static void disband(Player player) throws IOException {
        try {
            File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
            FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);
            
            File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", playerCfg.getString("Info.Gang.Name")));
            FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);
            String leader = gangCfg.getString("Info.Leader");
            
            
            if (!player.getName().equals(leader)) {
                player.sendMessage(grabMessagesValue.type("gang", "disband_not_leader").replaceAll("&", "§"));
                return;
            }
            
            ArrayList<String> gangMembersUUID = new ArrayList<>(); //So I can make it so they are not in a gang.
            gangMembersUUID.addAll(gangCfg.getStringList("Members"));
            
            ArrayList<String> gangMembers = new ArrayList<>();
            gangMembers.addAll(gangCheckInfo.IDtoIGN(gangMembersUUID));
            
            String gangName = Objects.requireNonNull(gangFile.getName().trim());
            int total = gangMembers.size(); //grabs gang members total amount
            //Removes the player Multiplier.
            int mineTier = gangCfg.getInt("Mine.Tier");
            
            //Remove multiplier
            try {
                giveMultiplier.givePlayerMultiplier(player.getUniqueId(), (-0.30F * mineTier) +0.30F);
            } catch (Exception Ignored) {}
            
            if (total != 0) { //checks gang members total amount if it has any with notequals [!=]
                for (int i = 0; i < total; ) {
                    UUID id = UUID.fromString(gangMembersUUID.get(i)); //gets their UUID
                    File file = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", id)); //Changes local player file
                    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    
                    if (Objects.requireNonNull(cfg.getString("Info.Gang.Name")).equals(gangName.replaceAll(".yml", ""))) { //Double checks just incase of anything so it doesn't randomly kick them from a gang.
                        cfg.set("Info.Gang.Name", null);
                        cfg.save(file);
                    }
                    
                    //Remove multiplier
                    try {
                        giveMultiplier.givePlayerMultiplier(id, (-0.30F * mineTier) +0.30F);
                    } catch (Exception Ignored) {}
                    
                    OfflinePlayer player2 = Bukkit.getOfflinePlayer(UUID.fromString(gangMembersUUID.get(i)));
                    
                    //player2.getPlayer().sendMessage(grabMessagesValue.type("gang", "disband_player").replaceAll("&", "§"));
                    i++;
                }
            }

                    /*
                    we want to delete the region. We're doing it early because we need to grab the leader name.
                    Without it, we cannot delete it. I accidentally added to end, and it has never shown errors, but
                    leader was null and stopped code, without any error codes.
                     */
            try {
                deleteRegion.deleteRegion(player);
                deletePlotsRegion.deletePlotRegion(player);
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("[Prison-Core] " + ChatColor.DARK_RED + "Could not delete region(s) for " + player.getName());
            }
            
            //Player now
            File file = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId())); //Changes local player file
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            
            //No need for checks if they created the request
            
            cfg.set("Info.Gang.Name", null);
            cfg.save(file);
            
            gangCfg.set("Info", null);
            gangCfg.set("Members", null);
            gangCfg.set("Invites", null);
            gangCfg.set("Mine", null);
            gangCfg.save(gangFile);
            
            //gangFile.delete(); - replaced with deleteRegion()
            
            player.sendMessage(grabMessagesValue.type("gang", "disband_deleted").replaceAll("&", "§"));
        } catch (Exception e) {
            throw e;
        }
    }
}

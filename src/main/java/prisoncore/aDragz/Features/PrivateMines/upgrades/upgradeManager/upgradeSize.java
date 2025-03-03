package prisoncore.aDragz.Features.PrivateMines.upgrades.upgradeManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.Features.PrivateMines.PMineCommandHandler;
import prisoncore.aDragz.Features.Sell.Multipliers.cmds.giveMultiplier;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class upgradeSize {
    private static final main plugin = main.getPlugin(main.class);
    static File upgradesFile = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    static FileConfiguration upgradesCfg = YamlConfiguration.loadConfiguration(upgradesFile);

    public static boolean upgradeMineSize(Player player) {
        try {
        /*
        First, I will load all files and ints. Like Current tier, next tier etc.

        Before I save the File, I will check that they have all requirements ready for
        leveling up the mine. Including Money, Exp, Blocks broken etc etc.
        ^ They are not requirements implemented, just a baseline on my idea. (Only money will be added for now until I can code more)

        Then I will save 'Mine.Tier' in the gang file to nextTier and save the file.

        After this, I will reset the mine. This will tp the user back to the top.
        This should put the next tier in effect.



        It also checks if the upgrade is already there before taking any values from the user.
         */
            
            File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
            FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);
            
            File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", playerCfg.getString("Info.Gang.Name")));
            FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);
            
            String leader = gangCfg.getString("Info.Leader");
            
            
            if (!player.getName().equals(leader)) {
                player.sendMessage(grabMessagesValue.type("gang", "disband_not_leader").replaceAll("&", "§"));
            }
            
            int currentTier = private_mines_grabValues.currentTier(player); //Grab current tier from player
            int nextTier = currentTier + 1; //Add 1, for the next tier.
            
            String location = String.format("%d", nextTier); //Location of the next upgrade tier
            
            if (Objects.isNull(upgradesCfg.getString(location))) {
                player.sendMessage(grabMessagesValue.type("mine", "upgrade_top_rank").replaceAll("&", "§"));
                return false;
            }
            
            //Check requirements for next rank, and the user to see if they reached the goal for the next tier/
            if (!checkRequirements.checkPlayerRequirements(player, nextTier, upgradesCfg)) {
                player.sendMessage(grabMessagesValue.type("mine", "upgrade_fail").replaceAll("&", "§"));
                return false;
            }
            
            String gangName = playerCfg.getString("Info.Gang.Name");
            
            gangCfg.set("Mine.Tier", nextTier);
            try {
                gangCfg.save(gangFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            
            try {
                PMineCommandHandler.createRegionRequirements(player);
                PMineCommandHandler.resetMine(player, true, false);
            } catch (Exception e) {
                return false;
            }
            
            try {
                ArrayList<String> gangMembersUUID = new ArrayList<>(); //So I can make it so they are not in a gang.
                gangMembersUUID.addAll(gangCfg.getStringList("Members"));
                
                ArrayList<String> gangMembers = new ArrayList<>();
                gangMembers.addAll(gangCheckInfo.IDtoIGN(gangMembersUUID));
                
                int total = gangMembers.size(); //grabs gang members total amount
                
                if (total != 0) { //checks gang members total amount if it has any with notequals [!=]
                    for (int i = 0; i < total; i++) {
                        UUID id = UUID.fromString(gangMembersUUID.get(i)); //gets their UUID
                        giveMultiplier.givePlayerMultiplier(id, 0.30F);
                    }
                }
            } catch (Exception Ignored) {
            }
            player.sendMessage(grabMessagesValue.type("mine", "upgrade_successful").replaceAll("&", "§"));
            giveMultiplier.givePlayerMultiplier(player.getUniqueId(), 0.30F);
            
            return true;
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            return false;
        }
    }
}

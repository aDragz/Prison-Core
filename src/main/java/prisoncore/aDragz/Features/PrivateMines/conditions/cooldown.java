package prisoncore.aDragz.Features.PrivateMines.conditions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class cooldown {
    public static HashMap<String, Long> cooldownMap = new HashMap<>(); //Cooldown list, do not remove as I want it to be static.
    public static int cooldownLength = 15; //may be configurable later. (60 seconds)

    public static boolean isOnCooldown(Player player) {
        
        if (cooldownMap.containsKey(player.getName())) {
            long secondsLeft = ((cooldownMap.get(player.getName()) / 1000) + cooldownLength) - (System.currentTimeMillis() / 1000);

            if (secondsLeft >= 1) {
                //Has a cooldown
                player.sendMessage(ChatColor.RED + String.format("You cannot use that command for another %d seconds", secondsLeft));
                return true;
            }
        }
        //No cooldown

        cooldownMap.put(player.getName(), System.currentTimeMillis()); //adds player to the list, so they can get a cooldown for the command they are using.
        return false;
    }
    
    public static HashMap<String, Long> upgradecooldownMap = new HashMap<>(); //Cooldown list, do not remove as I want it to be static.
    public static int upgradecooldownLength = 300; //may be configurable later. (60 seconds)
    
    public static boolean upgradeCooldown(Player player) {
        
        if (upgradecooldownMap.containsKey(player.getName())) {
            long secondsLeft = ((upgradecooldownMap.get(player.getName()) / 1000) + upgradecooldownLength) - (System.currentTimeMillis() / 1000);
            
            if (secondsLeft >= 1) {
                //Has a cooldown
                player.sendMessage(ChatColor.RED + String.format("You cannot use that command for another %d seconds", secondsLeft));
                return true;
            }
        }
        //No cooldown
        
        upgradecooldownMap.put(player.getName(), System.currentTimeMillis()); //adds player to the list, so they can get a cooldown for the command they are using.
        return false;
    }
}

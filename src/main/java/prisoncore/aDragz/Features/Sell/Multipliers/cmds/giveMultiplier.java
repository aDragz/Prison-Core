package prisoncore.aDragz.Features.Sell.Multipliers.cmds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Sell.Multipliers.data.storePermMultipliers;
import prisoncore.aDragz.Features.Sell.Multipliers.grabMultiplier;

import java.io.IOException;
import java.util.UUID;

@SuppressWarnings("deprecation")
//Not needed as I am grabbing ID, not other way. It doesn't want me to store as IGN. Rather ID. Which I am grabbing ID.
public class giveMultiplier {
    public static boolean playerGiveMultiplier(Player player, String[] args, Boolean set) throws IOException {
        /*Args:
        [0] = "give" [Give Multiplier in commandExecutor.java]
        [1] = name
        [2] = amount
        
        3 in total. Starting from 0*/

        if (args.length <= 2) { //Check if it's 2 or less
            return false; //Return as I don't want to corrupt data by saving no values.
        }

        UUID playerID = Bukkit.getOfflinePlayer(args[1]).getUniqueId(); //Args[1] = Player Name
        float value = Float.valueOf(args[2]);

        //Check if it contains playerID already.

        //Remove because I need to update it
        storePermMultipliers.permMultipliers.remove(playerID);

        if (set.equals(true)) {
            setPlayerMultiplier(playerID, value);
        } else {
            givePlayerMultiplier(playerID, value);
        }
        return true;
    }

    public static void setPlayerMultiplier(UUID playerID, float value) {
        storePermMultipliers.permMultipliers.put(playerID.toString(), value); //Sets players Multiplier with the value it has.

        //Send Player message

        if (Bukkit.getOfflinePlayer(playerID).isOnline()) { //Check if player is online. Gives error otherwise.
            Bukkit.getPlayer(playerID).sendMessage(grabMessagesValue.type("multiplier", "set").replaceAll("&", "§")
                    .replaceAll("MULTIPLIER", String.valueOf(value)));
        }

    }

    public static void givePlayerMultiplier(UUID playerID, float value) {

        //To calculate new value (new = giving + current)

        float currentMultiplier;
        float newValue;

        //If it cannot find one, it will throw. Else, it will add.

        try {
            currentMultiplier = grabMultiplier.grabUUIDMultiplier(playerID);
            newValue = value + currentMultiplier;
        } catch (NullPointerException E) { //Current multiplier throws null
            newValue = value; //Cannot find currentMultiplier
        }
        
        float roundedNumber = (float) Math.round(newValue * 100) / 100;
        
        //Save Multiplier

        storePermMultipliers.permMultipliers.put(playerID.toString(), roundedNumber);

        //Send Player message

        if (Bukkit.getOfflinePlayer(playerID).isOnline()) { //Check if player is online. Gives error otherwise.
            Bukkit.getPlayer(playerID).sendMessage(grabMessagesValue.type("multiplier", "give").replaceAll("&", "§")
                    .replaceAll("MULTIPLIER", String.valueOf(value)));
        }
    }
}

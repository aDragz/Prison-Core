package prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems;

import org.bukkit.entity.Player;
import prisoncore.aDragz.data.private_mines_grabValues;

import java.util.ArrayList;

public class UPGRADE_MINE {

    public static ArrayList<String> generateUpgradeLore(ArrayList<String> loreList, Player player) {
        ArrayList<String> LoreNew = new ArrayList<>();

        int currentTier = private_mines_grabValues.currentTier(player); //Grab current tier from player

        for (String newLoreS : loreList) {
            newLoreS = newLoreS.replaceAll("&", "§")
                    .replaceAll("CURRENTTIER", currentTier + "")
                    .replaceAll("NEXTTIER", currentTier + 1 + "");

            LoreNew.add(newLoreS);
        }
        return LoreNew;
    }
}

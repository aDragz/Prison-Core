package prisoncore.aDragz.Features.Enchantments.Pickaxe.Effects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.blockCount.Data.storeBlocks;

public class grabPickaxeLevel {

    public static Double grabLevelDecimal(Player player) {

        try {
            //Grab Blocks Broken
            Long Blocks = storeBlocks.blocks.get(player.getUniqueId().toString());

            return (Math.sqrt(Blocks))/5;
        } catch (Exception e) {
            return 0D;
        }
    }

    public static int grabLevel(Player player) {

        try {
            //Grab Blocks Broken
            Long Blocks = storeBlocks.blocks.get(player.getUniqueId().toString());

            return returnWholeNumber((Math.sqrt(Blocks))/5);
        } catch (Exception e) {
            return 0;
        }
    }

    private static int returnWholeNumber(double number) {

        String stringNumber = String.valueOf(number);

        String[] decimalSplit = stringNumber.split("\\.");

        return Integer.parseInt(decimalSplit[0]);
    }
}

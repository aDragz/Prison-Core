package prisoncore.aDragz.Features.PrivateMines.upgrades.upgradeManager;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.main;

import java.text.DecimalFormat;

public class checkRequirements {
    static Economy econ = main.econ;
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###,###,###,###.00");

    public static boolean checkPlayerRequirements(Player player, int nextTier, FileConfiguration file) {
        //player = player
        //tier = next tier
        //file = gui/upgrades.yml. Grabbing requirements from the next tier. Returns true if reach, and false if they didn't

        String type = file.getString(String.format("%d.Requirements.Type", nextTier));

        switch (type.toLowerCase()) {
            case "permission":
                String permissionName = file.getString(String.format("%d.Requirements.Value", nextTier)).toLowerCase();
                
                if (!grabPermission(player, permissionName)) {
                    player.sendMessage(grabMessagesValue.type("mine", "upgrade_fail_permission_requirement").replaceAll("&", "§").replaceAll("NAME", permissionName.toUpperCase().replaceAll("GROUP.", "")));
                    return false;
                }
                
                return grabPermission(player, permissionName);
            case "money":
                double requiredAmount = file.getDouble(String.format("%d.Requirements.Value", nextTier));
                double playerAmount = econ.getBalance(player);
                if (!grabMoneyAmount(requiredAmount, playerAmount)) {
                    double amount = requiredAmount - playerAmount;
                    player.sendMessage(grabMessagesValue.type("mine", "upgrade_fail_money_requirement").replaceAll("&", "§").replaceAll("AMOUNT", String.valueOf(decimalFormat.format(amount))));
                    return false;
                }
                
                //Has the money:
                econ.withdrawPlayer(player, requiredAmount);
                
                return grabMoneyAmount(requiredAmount, playerAmount);
            case "exp":
                int expRequiredAmount = file.getInt(String.format("%d.Requirements.Value", nextTier));
                int expPlayerAmount = player.getTotalExperience();

                if (!grabMoneyAmount(expRequiredAmount, expPlayerAmount)) {
                    int amount = expRequiredAmount - expPlayerAmount;
                    player.sendMessage(grabMessagesValue.type("mine", "upgrade_fail_exp_requirement").replaceAll("&", "§").replaceAll("AMOUNT", String.valueOf(amount)));
                    return false;
                }
                return grabExpAmount(expRequiredAmount, expPlayerAmount);
            default:
                return false;
        }
    }

    public static boolean grabPermission(Player player, String permissionName) {
        return player.hasPermission(permissionName);
    }

    public static boolean grabMoneyAmount(double playerAmount, double requiredAmount) {
        return requiredAmount >= playerAmount;
    }

    public static boolean grabExpAmount(double playerAmount, double requiredAmount) {
        return requiredAmount >= playerAmount;
    }

}

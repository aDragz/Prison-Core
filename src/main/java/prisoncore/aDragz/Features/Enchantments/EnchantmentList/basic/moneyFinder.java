package prisoncore.aDragz.Features.Enchantments.EnchantmentList.basic;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore.updateLore;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class moneyFinder {
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    private static int maxValue = cfgYaml.getInt("Money-Finder.Max-Value")+1; //+1 or it'll cancel out at 999

    private static long defaultValue = cfgYaml.getLong("Money-Finder.Value");
    private static long divide = cfgYaml.getLong("Money-Finder.Divide");
    
    public static void procMoneyFinder(int level, Player player) {
        long value = defaultValue * (level/divide);
        
        EconomyResponse r = main.econ.depositPlayer(player.getPlayer(), value);
    }

    //Pickaxe Lore:
    public static boolean addMoneyFinderEnchant(Player player, int value, int oldAmountToAdd, ItemStack pickaxe) {
        if (value < maxValue) {
            try {
                ItemMeta pickMeta = pickaxe.getItemMeta();

                /*
                Remember to change values in:
                Pickaxe.Events.GuiLore.updateLore
                Pickaxe.Events.GuiLore.updateGUI
                Pickaxe.GuiManager.clickManager
                Enchantments.EnchantManager.getLevel
                 */
                ArrayList<String> pickLore = (ArrayList<String>) Objects.requireNonNull(updateLore.updatePickaxeMeta(pickaxe.getItemMeta(), "Money Finder", value, oldAmountToAdd)); //Adds lore to pickaxe

                pickMeta.setLore(pickLore);
                pickaxe.setItemMeta(pickMeta);
                player.getInventory().setItem(0, pickaxe); //Sets pickaxe lore if everything works out
                return true; //Updates GUI
            } catch (Exception e) {
                player.sendMessage(ChatColor.DARK_RED + "ERROR: " + e.getMessage());
                return false;
            }
        }
        player.sendMessage(grabMessagesValue.type("enchants", "max_enchant").replaceAll("&", "§"));
        return false;
    }
}

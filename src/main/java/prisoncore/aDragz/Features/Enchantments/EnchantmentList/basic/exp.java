package prisoncore.aDragz.Features.Enchantments.EnchantmentList.basic;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getLevel;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore.updateLore;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class exp {
    
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    private static int maxValue = cfgYaml.getInt("Exp.Max-Value")+1; //+1 or it'll cancel out at 999

    public static float grabExpLevel(Player player) {
        return Float.valueOf(getLevel.grabEnchantLevel(player.getInventory().getItem(0), "Exp"));
    }

    //Pickaxe Lore:
    public static boolean addExpEnchant(Player player, int value, int oldAmountToAdd, ItemStack pickaxe) {
        if (value < maxValue) {
            try {
                ItemMeta pickMeta = pickaxe.getItemMeta();

                /*
                Remember to change values in:
                Pickaxe.Events.GuiLore.updateLore
                Pickaxe.Events.GuiLore.updateGUI
                Pickaxe.GuiManager.clickManager
                Enchantments.EnchantManager.getLevel
                Enchantments.EnchantManager.getProc
                 */
                ArrayList<String> pickLore = (ArrayList<String>) Objects.requireNonNull(updateLore.updatePickaxeMeta(pickaxe.getItemMeta(), "Exp", value, oldAmountToAdd)); //Adds lore to pickaxe

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

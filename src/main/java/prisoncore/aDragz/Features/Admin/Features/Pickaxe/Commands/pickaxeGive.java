package prisoncore.aDragz.Features.Admin.Features.Pickaxe.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore.setPickaxeLore;
import prisoncore.aDragz.main;

import java.io.File;

public class     pickaxeGive {
    
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    static String pickName = cfgYaml.getString("Pickaxe.Display-Name").replaceAll("&", "§");

    static String pickMaterial = cfgYaml.getString("Pickaxe.Material");

    /*
                        [0] pickaxe - e
                        [1] give - o
                        [2] aDragz - e
                        [3] Fortune - o
                        [4] 10 - e
                        [5] Exp - o
                        [6] 100 - e
    */

    public static void givePickaxe(Player target, String[] args) {
        try {
            ItemStack pickaxe = new ItemStack(Material.valueOf(pickMaterial));
            ItemMeta pickaxeMeta = pickaxe.getItemMeta();
            
            pickaxeMeta.setDisplayName(pickName
                    .replaceAll("NAME", target.getName()));

            pickaxeMeta.setUnbreakable(true); //Sets pickaxe unbreakable

            pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); //So I can use custom Lore for enchants
            pickaxeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            pickaxe.setItemMeta(pickaxeMeta);

            target.getInventory().setItem(0, setPickaxeLore.changeLore(target, pickaxe, 0, args));
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + e.getMessage());
        }
    }
}

package prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getLevel;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getPrice;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getProcPercent;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;

public class updateGUI {

    private static final main plugin = main.getPlugin(main.class);

    public static File file = new File(plugin.getDataFolder(), "gui/enchantmentGUI.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file); //Public for "./GenerateItems"

    public static void updateUserInferface(Player player, int slot, int pageNumber, ItemStack itemStack, ItemStack pickaxe, String type) {
            //Copied from enchantmentGUI.java
            //First thing first, grab Block data before creating block:

            String enchantmentName = (cfg.getString(String.format("GUI.%d.%d.Enchantment", pageNumber, slot)));
            String display_name = (cfg.getString(String.format("GUI.%d.%d.Display_Name", pageNumber, slot)));

            String itemBlock = (cfg.getString(String.format("GUI.%d.%d.Material", pageNumber, slot)));

            //Create the itemStack so it can get the material
            ItemStack item;

            item = new ItemStack(Material.valueOf(itemBlock));

            //Create and set the meta of the item
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(display_name.replaceAll("&", "§"));

            //Next is to get the Lore of the item.
            ArrayList<String> lore = new ArrayList<>();
            ArrayList<String> newLore = new ArrayList<>();
            lore.addAll(cfg.getStringList(String.format("GUI.%d.%d.Lore", pageNumber, slot)));

            for (String newLoreS : lore) {
                int enchantLevel = Integer.valueOf(getLevel.grabEnchantLevel(pickaxe, enchantmentName));
                Long enchantPrice = getPrice.grabPrice((long) enchantLevel, enchantmentName);
                String enchantChance = getProcPercent.percentConverter(enchantLevel, enchantmentName);
                
                newLoreS = newLoreS.replaceAll("&", "§").replaceAll("LEVEL", String.valueOf(enchantLevel)).replaceAll("COST", enchantPrice.toString()).replaceAll("PROCCHANCE", enchantChance);

                newLore.add(newLoreS);
            }

            meta.setLore(newLore);

            itemStack.setItemMeta(meta);

            player.getOpenInventory().setItem(slot, itemStack);
        }
}
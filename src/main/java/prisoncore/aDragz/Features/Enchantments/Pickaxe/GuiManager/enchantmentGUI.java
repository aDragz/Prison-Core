package prisoncore.aDragz.Features.Enchantments.Pickaxe.GuiManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getLevel;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getPrice;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getProcPercent;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class enchantmentGUI {
    private static final main plugin = main.getPlugin(main.class);
    
    public static File file = new File(plugin.getDataFolder(), "gui/enchantmentGUI.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file); //Public for "./GenerateItems"
    
    public static String inventoryNameBase = cfg.getString("GUI.Title").replaceAll("&", "§");
    
    public static HashMap<UUID, Inventory> map = new HashMap<UUID, Inventory>();
    
    public static ItemStack FillerItemStack = cfg.getItemStack("GUI.Filler_Block");
    
    public static void createInventory(Player player, ItemStack pickaxe) {
        try {
            player.openInventory(generateInventory(1, pickaxe));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Inventory generateInventory(int pageNumber, ItemStack pickaxe) throws ExecutionException, InterruptedException {
        ItemStack FillerBlock = FillerItemStack;
        ArrayList<Integer> itemSlots = new ArrayList<>();
        int maxSize = 54;
        
        Inventory inv = Bukkit.getServer().createInventory(null, 54, inventoryNameBase);
        
        
        for (int i = 0; i < maxSize; ) {
            if (cfg.contains(String.format("GUI.%d.%d", pageNumber, i))) {
                itemSlots.add(i);
                String type = (cfg.getString(String.format("GUI.%d.%d.Type", pageNumber, i)));
                
                switch (type) {
                    case ("ENCHANTMENT"):
                        //First thing first, grab Block data before creating block:
                        
                        String  enchantmentName = (cfg.getString(String.format("GUI.%d.%d.Enchantment", pageNumber, i)));
                        String display_name = (cfg.getString(String.format("GUI.%d.%d.Display_Name", pageNumber, i)));
                        
                        String itemBlock = (cfg.getString(String.format("GUI.%d.%d.Material", pageNumber, i)));
                        
                        //Create the itemStack so it can get the material
                        ItemStack item;
                        
                        item = new ItemStack(Material.valueOf(itemBlock));
                        
                        //Create and set the meta of the item
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(display_name.replaceAll("&", "§"));
                        
                        //Next is to get the Lore of the item.
                        ArrayList<String> lore = new ArrayList<>();
                        ArrayList<String> newLore = new ArrayList<>();
                        lore.addAll(cfg.getStringList(String.format("GUI.%d.%d.Lore", pageNumber, i)));
                        
                        for (String newLoreS : lore) {
                            String enchantLevel = getLevel.grabEnchantLevel(pickaxe, enchantmentName).replaceAll("§fEfficiency: §d", "");
                            Long enchantPrice = getPrice.grabPrice(Long.valueOf(enchantLevel), enchantmentName);
                            String enchantChance = getProcPercent.percentConverter(Integer.parseInt(enchantLevel), enchantmentName);
                            
                            newLoreS = newLoreS.replaceAll("&", "§").replaceAll("LEVEL", enchantLevel).replaceAll("COST", enchantPrice.toString()).replaceAll("PROCCHANCE", enchantChance);
                            
                            newLore.add(newLoreS);
                        }
                        
                        meta.setLore(newLore);
                        
                        item.setItemMeta(meta);
                        inv.setItem(i, item);
                        
                        break;
                    default:
                        break;
                }
                
            } else {
                inv.setItem(i, FillerBlock);
            }
            i++;
        }
        return inv;
    }
}

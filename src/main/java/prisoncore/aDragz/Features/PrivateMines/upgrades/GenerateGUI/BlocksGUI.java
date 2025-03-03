package prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateGUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.Features.PrivateMines.upgrades.Blocks.blocksGrabTier;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems.EFFECTS;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems.PAGES;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class BlocksGUI {
    private static final main plugin = main.getPlugin(main.class);
    
    public static File file = new File(plugin.getDataFolder(), "gui/blocks.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file); //Public for "./GenerateItems"
    
    public static HashMap<UUID, Inventory> map = new HashMap<UUID, Inventory>();
    
    static int InventorySize = 54;
    
    static ItemStack FillerBlock = new ItemStack(EFFECTS.fillerItemStack());
    static ItemMeta FillerMeta = FillerBlock.getItemMeta();
    
    public static Inventory generateInventory(Player player, int pageNumber) throws ExecutionException, InterruptedException {
        Inventory inv = Bukkit.getServer().createInventory(null, InventorySize, ChatColor.GREEN + "" + ChatColor.BOLD + "Mine Blocks | " + pageNumber);
        
        map.put(player.getUniqueId(), inv);
        
        //Create Filler, Do it out of for statement, so it doesn't waste time.
        FillerMeta.setDisplayName(" ");
        FillerBlock.setItemMeta(FillerMeta);
        
        //grab size of inventory
        List<String> itemSlots = new ArrayList<>();
        itemSlots.addAll(returnAllBlocks(pageNumber));
        
        int maxSize = itemSlots.size();
        
        for (int i = 0; i < maxSize; ) {
            //First thing first, grab Block data before creating block:
            
            //String display_name =
            String display_name = ChatColor.GRAY + itemSlots.get(i).toString();
            
            //Create the itemStack so it can get the material
            ItemStack item;
            
            item = new ItemStack(Material.valueOf(itemSlots.get(i).toUpperCase()));
            
            //Create and set the meta of the item
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(display_name.replaceAll("&", "§").replaceAll("_", " "));
            
            //Next is to get the Lore of the item.
            ArrayList<String> lore = new ArrayList<>();
            ArrayList<String> newLore = new ArrayList<>();
            
            lore.add("&cUnlocks at: Tier " + blocksGrabTier.tier(i, pageNumber));
            lore.add("");
            
            //check if it's unlocked
            
            //if slot tier is >= (more than or equal to) my tier, say yes
            
            int slotTier = blocksGrabTier.slotToTier(i, pageNumber);
            int currentTier = private_mines_grabValues.currentTier(player);
            
            if (slotTier <= currentTier) {
                lore.add("&a&lUnlocked!");
            } else {
                int unlocksIn = (slotTier - currentTier);
                
                lore.add("&c&lLocked!");
                lore.add(String.format("&7Unlocks in %d tiers", unlocksIn));
            }
            
            for (String newLoreS : lore) {
                newLoreS = newLoreS.replaceAll("&", "§");
                
                newLore.add(newLoreS);
            }
            
            meta.setLore(newLore);
            
            item.setItemMeta(meta);
            inv.setItem(i, item);
            
            i++;
        }
        
        if (pageNumber == 1) {
            inv.setItem(53, PAGES.page_forward());
        } else if (pageNumber == 2) {
            inv.setItem(45, PAGES.page_backward());
            inv.setItem(53, PAGES.page_forward());
        } else if (pageNumber == 3) {
            inv.setItem(45, PAGES.page_backward());
        }
        
        return inv;
    }
    
    public static List<String> returnAllBlocks(int pageNumber) {
        return cfg.getStringList(String.format("Blocks.%d", pageNumber));
    }
}

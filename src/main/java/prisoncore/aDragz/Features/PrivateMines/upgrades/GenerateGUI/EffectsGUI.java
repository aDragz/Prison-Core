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
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems.EFFECTS;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems.PAGES;
import prisoncore.aDragz.Features.PrivateMines.upgrades.grabItems.itemGrabberHandler;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class EffectsGUI {
    private static final main plugin = main.getPlugin(main.class);
    
    public static File file = new File(plugin.getDataFolder(), "gui/effects.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file); //Public for "./GenerateItems"
    
    public static HashMap<UUID, Inventory> map = new HashMap<UUID, Inventory>();
    
    //Grab Filler Block:
    //static ItemStack FillerBlock = new ItemStack(Objects.requireNonNull(config.getItemStack("GUI.Filler_Block")));
    //static ItemStack FillerBlock = new ItemStack(Material.getMaterial(cfg.getString(String.format("GUI.Filler_Block"))));
    static ItemStack FillerBlock = new ItemStack(EFFECTS.fillerItemStack());
    static ItemMeta FillerMeta = FillerBlock.getItemMeta();
    
    public static ItemStack FORWARD_PAGE = new ItemStack(itemGrabberHandler.grabItem("page_forward")); //Public for PAGES.java
    public static ItemStack PREVIOUS_PAGE = new ItemStack(itemGrabberHandler.grabItem("page_backward")); //Public for PAGES.java
    
    static int InventorySize = cfg.getInt("GUI.Size");
    
    public static void createInventory(Player player) {
        try {
            player.openInventory(generateInventory((player)));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Inventory generateInventory(Player player) throws ExecutionException, InterruptedException {
        Inventory inv = Bukkit.getServer().createInventory(null, InventorySize, ChatColor.GREEN + "" + ChatColor.BOLD + "Mine Management | Effects");
        
        map.put(player.getUniqueId(), inv);
        
        //Create Filler, Do it out of for statement, so it doesn't waste time.
        FillerMeta.setDisplayName(" ");
        FillerBlock.setItemMeta(FillerMeta);
        
        //grab size of inventory
        
        ArrayList<Integer> itemSlots = new ArrayList<>();
        int maxSize = cfg.getInt("GUI.Size");
        
        for (int i = 0; i < maxSize; ) {
            if (cfg.contains(String.format("GUI.%d", i))) {
                itemSlots.add(i);
                String type = (cfg.getString(String.format("GUI.%d.Type", i)));
                
                switch (type) {
                    case ("Effects"):
                        //First thing first, grab Block data before creating block:
                        
                        String display_name = (cfg.getString(String.format("GUI.%d.Display_Name", i)));
                        
                        //Create the itemStack so it can get the material
                        ItemStack item;
                        
                        item = new ItemStack(Material.getMaterial(cfg.getString(String.format("GUI.%d.Material", i))));
                        
                        //Create and set the meta of the item
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(display_name.replaceAll("&", "§"));
                        
                        //Next is to get the Lore of the item.
                        ArrayList<String> lore = new ArrayList<>();
                        ArrayList<String> newLore = new ArrayList<>();
                        lore.addAll(cfg.getStringList(String.format("GUI.%d.Lore", i)));
                        
                        for (String newLoreS : lore) {
                            newLoreS = newLoreS.replaceAll("&", "§");
                            
                            newLore.add(newLoreS);
                        }
                        
                        meta.setLore(newLore);
                        
                        item.setItemMeta(meta);
                        inv.setItem(i, item);
                        
                        break;
                    case "FORWARD_PAGE":
                        inv.setItem(i, PAGES.page_forward());
                        break;
                    case "BACKWARDS_PAGE":
                        inv.setItem(i, PAGES.page_backward());
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
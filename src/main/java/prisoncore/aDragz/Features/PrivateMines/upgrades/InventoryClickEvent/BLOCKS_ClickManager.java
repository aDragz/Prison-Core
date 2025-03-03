package prisoncore.aDragz.Features.PrivateMines.upgrades.InventoryClickEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import prisoncore.aDragz.Features.PrivateMines.PMineCommandHandler;
import prisoncore.aDragz.Features.PrivateMines.upgrades.Blocks.blocksGrabTier;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateGUI.BlocksGUI;
import prisoncore.aDragz.Features.PrivateMines.upgrades.grabItems.itemGrabberHandler;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.EventListener;
import java.util.concurrent.ExecutionException;

public class BLOCKS_ClickManager implements Listener, EventListener {

    /*
    What do I want to do when you click the MINE GUI?

    Green = Nothing
    Yellow = Nothing
    Red = Upgrade if you can afford it + previous is unlocked.
     */

    static ItemStack FORWARD_PAGE = new ItemStack(itemGrabberHandler.grabItem("page_forward"));
    static ItemStack BACKWARD_PAGE = new ItemStack(itemGrabberHandler.grabItem("page_backward"));
    static String FORWARD_PAGE_STRING = itemGrabberHandler.grabDisplayName("page_forward");
    static String BACKWARD_PAGE_STRING = itemGrabberHandler.grabDisplayName("page_backward");

    static String UPGRADE_STRING = itemGrabberHandler.grabDisplayName("upgrade");
    static ItemStack UPGRADE = new ItemStack(itemGrabberHandler.grabItem("upgrade"));

    static String EFFECTS_STRING = itemGrabberHandler.grabDisplayName("effects");
    static ItemStack EFFECTS = new ItemStack(itemGrabberHandler.grabItem("effects"));

    static String MINETP_STRING = itemGrabberHandler.grabDisplayName("minetp");
    static ItemStack MINETP = new ItemStack(itemGrabberHandler.grabItem("minetp"));
    
    private static final main plugin = main.getPlugin(main.class);

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent event) throws ExecutionException, InterruptedException {
        Player player = (Player) event.getWhoClicked();

        String invName = ChatColor.GREEN + "" + ChatColor.BOLD +
                "Mine Blocks | "; //Not full name, I need to grab the page number so I can actually move to the next page


        //Grab page number through the last char through the inv display name.
        int i = invName.length();

        if (player.getOpenInventory().getTitle().contains(invName)) {
            event.setCancelled(true);
            int currentPageNumber = Integer.parseInt(String.valueOf(player.getOpenInventory().getTitle().charAt(i))); //grabs the page number the player is on.
            
            ItemStack clickedItem = event.getCurrentItem();
            
            if (clickedItem.getType() == FORWARD_PAGE.getType()) {
                
                if (clickedItem.getItemMeta().getDisplayName().equals(FORWARD_PAGE_STRING)) {
                    player.openInventory(BlocksGUI.generateInventory((player), currentPageNumber + 1));
                }
                
                return;
            } else if (clickedItem.getType() == BACKWARD_PAGE.getType()) {
                if (clickedItem.getItemMeta().getDisplayName().equals(BACKWARD_PAGE_STRING)) {
                    player.openInventory(BlocksGUI.generateInventory((player), currentPageNumber - 1));
                }
                
                return;
            }
            
            int slotTier = blocksGrabTier.tier(event.getSlot(), currentPageNumber);
            int currentTier = private_mines_grabValues.currentTier(player);
            
            if (slotTier <= currentTier) {
                //Change block for gang
                
                File leaderFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
                FileConfiguration leaderCfg = YamlConfiguration.loadConfiguration(leaderFile);
                
                File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", leaderCfg.getString("Info.Gang.Name")));
                FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);
                
                gangCfg.set("Mine.Block", event.getCurrentItem().getType().toString());
                
                try {
                    gangCfg.save(gangFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                
                PMineCommandHandler.resetMine(player, false, false);
            }
        }
    }
}

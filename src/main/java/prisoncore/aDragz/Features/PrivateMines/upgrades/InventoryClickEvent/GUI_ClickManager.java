package prisoncore.aDragz.Features.PrivateMines.upgrades.InventoryClickEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import prisoncore.aDragz.Features.PrivateMines.PMineCommandHandler;
import prisoncore.aDragz.Features.PrivateMines.gui;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateGUI.EffectsGUI;
import prisoncore.aDragz.Features.PrivateMines.upgrades.grabItems.itemGrabberHandler;
import prisoncore.aDragz.Features.PrivateMines.upgrades.upgradeManager.upgradeSize;

import java.util.EventListener;
import java.util.concurrent.ExecutionException;

public class GUI_ClickManager implements Listener, EventListener {

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

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent event) throws ExecutionException, InterruptedException {
        Player player = (Player) event.getWhoClicked();

        String invName = ChatColor.GREEN + "" + ChatColor.BOLD +
                "Mine Management | "; //Not full name, I need to grab the page number so I can actually move to the next page


        //Grab page number through the last char through the inv display name.
        int i = invName.length();

        if (player.getOpenInventory().getTitle().contains(invName)) {
            event.setCancelled(true);
            int currentPageNumber = Integer.parseInt(String.valueOf(player.getOpenInventory().getTitle().charAt(i))); //grabs the page number the player is on.

            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem.getType() == FORWARD_PAGE.getType()) {

                if (clickedItem.getItemMeta().getDisplayName().equals(FORWARD_PAGE_STRING)) {
                    player.openInventory(gui.generateInventory((player), currentPageNumber + 1));
                }
            } else if (clickedItem.getType() == BACKWARD_PAGE.getType()) {
                if (clickedItem.getItemMeta().getDisplayName().equals(BACKWARD_PAGE_STRING)) {
                    player.openInventory(gui.generateInventory((player), currentPageNumber - 1));
                }
            } else if (clickedItem.getType() == UPGRADE.getType()) {
                if (clickedItem.getItemMeta().getDisplayName().equals(UPGRADE_STRING)) {
                    try {
                        upgradeSize.upgradeMineSize(player);
                    } catch (Exception e) {
                        Bukkit.getConsoleSender().sendMessage(e.getMessage());
                    }
                }
            } else if (clickedItem.getType() == EFFECTS.getType()) {
                if (clickedItem.getItemMeta().getDisplayName().equals(EFFECTS_STRING)) {
                    try {
                        EffectsGUI.createInventory(player);
                    } catch (Exception e) {
                        Bukkit.getConsoleSender().sendMessage(e.getMessage());
                    }
                }
            } else if (clickedItem.getType() == MINETP.getType()) {
                if (clickedItem.getItemMeta().getDisplayName().equals(MINETP_STRING)) {
                    try {
                        PMineCommandHandler.mineTp(player);
                    } catch (Exception e) {
                        Bukkit.getConsoleSender().sendMessage(e.getMessage());
                    }
                }
            }
        }
    }
}

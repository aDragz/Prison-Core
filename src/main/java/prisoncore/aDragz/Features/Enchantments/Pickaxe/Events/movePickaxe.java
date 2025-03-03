package prisoncore.aDragz.Features.Enchantments.Pickaxe.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EventListener;

public class movePickaxe implements Listener, EventListener {

    @EventHandler
    public static void inventoryClickEvent(InventoryClickEvent event) {
        if (event.getClick().isKeyboardClick()) {
            event.setCancelled(true);
            sendMessage((Player) event.getWhoClicked());
            return;
        }
        try {
            if (event.getCurrentItem().getType().equals(Material.GOLDEN_PICKAXE)) {
                if (event.getSlot() == 0) { //pickaxe
                    event.setCancelled(true);
                    sendMessage((Player) event.getWhoClicked());
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    @EventHandler
    public static void itemMoveEvent(InventoryMoveItemEvent event) {
        if (event.getItem().equals(new ItemStack(Material.GOLDEN_PICKAXE))) { //pickaxe
            event.setCancelled(true);
            sendMessage((Player) event.getInitiator());
        }
    }

    @EventHandler
    public static void moveShield(PlayerSwapHandItemsEvent event) {
            event.setCancelled(true);
    }

    static void sendMessage(Player player) {
        player.sendMessage("§cYou cannot move this item!");
    }
}

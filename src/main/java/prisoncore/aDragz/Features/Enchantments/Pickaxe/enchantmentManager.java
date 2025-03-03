package prisoncore.aDragz.Features.Enchantments.Pickaxe;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.GuiManager.enchantmentGUI;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.EventListener;

public class enchantmentManager implements EventListener, Listener {

    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    static String pickMaterial = cfgYaml.getString("Pickaxe.Material");
    static String dropType = cfgYaml.getString("Pickaxe.Drop-Type");

    @EventHandler
    public static void playerDropEvent(PlayerDropItemEvent event) {

        //Check if the item dropped is a pickaxe, so it can still cancel the event to avoid dropping the Pickaxe
        if (event.getItemDrop().getItemStack().getType().equals(Material.valueOf(pickMaterial))) {
            event.setCancelled(true);

            if (dropType.equals("DROP")) {
                Player player = event.getPlayer();

                enchantmentGUI.createInventory(player, event.getItemDrop().getItemStack()); //Open the GUI
            }
        }
    }

    @EventHandler
    public static void checkRightClick(PlayerInteractEvent event) {
        if (dropType.equals("RIGHT_CLICK")) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getItem() != null && event.getItem().getType().equals(Material.valueOf(pickMaterial))) {
                    event.setCancelled(true);

                    Player player = event.getPlayer();

                    enchantmentGUI.createInventory(player, event.getItem()); //Open the GUI
                }
            }
        }
    }
}
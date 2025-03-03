package prisoncore.aDragz.Features.PrivateMines.upgrades.InventoryClickEvent;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.EventListener;
import java.util.concurrent.ExecutionException;

public class EFFECTS_ClickManager implements Listener, EventListener {

    private static final main plugin = main.getPlugin(main.class);

    public static File file = new File(plugin.getDataFolder(), "gui/effects.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file); //Public for "./GenerateItems"

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent event) throws ExecutionException, InterruptedException {
        Player player = (Player) event.getWhoClicked();

        String invName = ChatColor.GREEN + "" + ChatColor.BOLD +
                "Mine Management | Effects";

        if (player.getOpenInventory().getTitle().contains(invName)) {
            event.setCancelled(true);

            int slot = event.getSlot();

            //Check if it's an effect
            if (cfg.getString(String.format("GUI.%d.Type", slot)).equals("Effects")) {

                //Grab effects And what to do
                String EffectName = cfg.getString(String.format("GUI.%d.EffectName", slot));
                int multiplier = cfg.getInt(String.format("GUI.%d.Multiplier", slot));

                giveEffect(player, EffectName, multiplier);
            }
        }
    }

    public static void giveEffect(Player player, String EffectName, int multiplier) {
        if (player.hasPotionEffect(PotionEffectType.getByName(EffectName))) {
            //ENABLED

            player.removePotionEffect(PotionEffectType.getByName(EffectName));
        } else {
            //DISABLED

            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(EffectName), 1000000, multiplier - 1)); //-1 because 4 = 5 in minecraft terms.
        }
    }
}

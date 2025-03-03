package prisoncore.aDragz.Features.Pve.Events.Mobs;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.EventListener;

public class creeperExplosion implements EventListener, Listener {
    
    @EventHandler
    public static void explode(EntityExplodeEvent event) {
        Bukkit.getWorld("pve").playSound(event.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1000F, -10F);
        event.setCancelled(true);
    }
}

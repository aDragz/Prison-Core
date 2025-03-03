package prisoncore.aDragz.Features.blockCount.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import prisoncore.aDragz.Features.blockCount.Data.storeBlocks;

import java.util.EventListener;

public class blocksPlayerJoinEvent implements EventListener, Listener {
    @EventHandler
    public static void playerJoin(PlayerJoinEvent event) {
        if (!storeBlocks.blocks.containsKey(event.getPlayer().getUniqueId().toString())) {
            storeBlocks.blocks.put(event.getPlayer().getUniqueId().toString(), 0l);
        }
    }
}

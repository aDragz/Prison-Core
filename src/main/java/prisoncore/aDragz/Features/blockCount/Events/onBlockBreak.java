package prisoncore.aDragz.Features.blockCount.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.blockCount.Data.storeBlocks;

public class onBlockBreak {
    public static void addBlockPlayer(Player player) {
            storeBlocks.blocks.put(player.getUniqueId().toString(), storeBlocks.blocks.get(player.getUniqueId().toString()) + 1l);
    }
}

package prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.EventListener;
import java.util.HashMap;
import java.util.UUID;


public class totalBlocksBroken implements Listener, EventListener {

    static HashMap<UUID, Long> blocksBroken = new HashMap<>();

    //@EventHandler
    public static void blockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID id = player.getUniqueId();
        if (!blocksBroken.containsKey(id)) {
            blocksBroken.put(id, Long.valueOf(1));

            return;
        }

        Long amount = blocksBroken.get(id);

        blocksBroken.put(id, amount += 1);

        //player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Hello World"));

    }

}

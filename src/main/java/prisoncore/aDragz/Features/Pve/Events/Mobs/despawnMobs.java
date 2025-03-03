package prisoncore.aDragz.Features.Pve.Events.Mobs;

import org.bukkit.Bukkit;

public class despawnMobs {
    
    public static void despawnMobs() {
        //Bukkit.getWorld("pve").getEntitiesByClass(Mob.class).forEach(Entity :: remove);
        
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kill @e[type=minecraft:zombie]");
    }
}

package prisoncore.aDragz.Features.Admin.Features.PVE.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import prisoncore.aDragz.Features.Pve.Events.Mobs.onStartUp;
import prisoncore.aDragz.Features.Pve.Mobs.Skeletons.GoldSkeleton;
import prisoncore.aDragz.Features.Pve.Mobs.Skeletons.LeatherSkeleton;
import prisoncore.aDragz.Features.Pve.Mobs.Zombies.BasicZombie;
import prisoncore.aDragz.Features.Pve.Mobs.Zombies.LeatherZombie;

public class mobsReset {
    
    private static final int size = onStartUp.locations.size(); //Used for the "for" statement inside Runnable
    public static boolean pendingReset = false;
    
    
    //Type =:
    //0 = Loop
    //1 = player tp / admin
    public static void resetMobs(int type) {
        //Despawn then respawn first
        if (despawnMobs(type))
            spawnMobs();
        //Spawn after the despawn
        
    }
    
    public static boolean despawnMobs(int type) {
        
        if (pendingReset) {
            if (type == 0) {
                return false;
            }
        }
        
        pendingReset = false;
        
        Bukkit.broadcastMessage("§7§l--------------§c§lPVE§7§l---------------");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("                 §6§lResetting PVE!          ");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§7§l--------------------------------");
        
        if (!Bukkit.getWorld("pve").getPlayers().isEmpty()) {
            Bukkit.getWorld("pve").getEntitiesByClass(Mob.class).forEach(Mob :: remove); //Removes all entities from the game
            return true;
        } else {
            pendingReset = true;
            return false;
        }
    }
    
    public static void spawnMobs() {
        for (int i = 0; i < size;) {
            Location location = (onStartUp.locations.get(i));
            String type = onStartUp.type.get(i);
            
            if (type.equals("Basic_Zombie"))
                BasicZombie.spawnZombie(location);
            else if (type.equals("Leather_Zombie"))
                LeatherZombie.spawnZombie(location);
            
            //Skeletons
            else if (type.equals("Leather_Skeleton")) {
                LeatherSkeleton.spawnSkeleton(location);
            } else if (type.equals("Golden_Skeleton"))
                GoldSkeleton.spawnSkeleton(location);
            
            i++;
        }
    }
}

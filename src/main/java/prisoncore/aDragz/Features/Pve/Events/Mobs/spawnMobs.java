package prisoncore.aDragz.Features.Pve.Events.Mobs;


import org.bukkit.Bukkit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getServer;

public class spawnMobs {
    
    
    public static void generateMobs() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool ( 1 );
        
        Runnable run = () -> {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Prison-Core"), ()->getServer().dispatchCommand(getServer().getConsoleSender(), "admin pve reset"));
        };
        
        try {
            executor.scheduleAtFixedRate(run, 30L, 30L, TimeUnit.MINUTES);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }
}

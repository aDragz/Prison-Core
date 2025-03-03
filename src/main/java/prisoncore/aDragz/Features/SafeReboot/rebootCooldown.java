package prisoncore.aDragz.Features.SafeReboot;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.Features.RankupInfinite.Data.Storage.saveRanksInfinite;
import prisoncore.aDragz.Features.RankupStandard.Data.saveRanks;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class rebootCooldown {
    private final static main plugin = main.getPlugin(main.class);
    
    public static HashMap<String, Long> cooldownMap = new HashMap<>(); //Cooldown list, do not remove as I want it to be static.
    
    public static int cooldownLengthint = -1;
    
    public static boolean set(int cooldownLength, CommandSender player) throws InterruptedException {
        if (cooldownLength == 0 ) {
            rebootNow();
            return true;
        }
        cooldownMap.put("rebooting", System.currentTimeMillis());
        
        cooldownLengthint = cooldownLength;
        
        return false;
    }
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/reboot.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    public static boolean rebootNow() throws InterruptedException {
        try {
            Bukkit.broadcastMessage(cfgYaml.getString("Messages.Rebooting_Now").replaceAll("&", "§"));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "multi save");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "token save");
            
            try {
                saveRanks.autoSaveRanks();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                saveRanksInfinite.autoSaveRanks();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            Thread.sleep(1000);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
        } catch (Exception e) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
        }
        
        return true;
    }
    
    public static void timer() {
        
        List<Integer> intervals = cfgYaml.getIntegerList("Intervals");
        
        Runnable run = ()->{
            if (cooldownMap.containsKey("rebooting")) {
                long secondsLeft = ((cooldownMap.get("rebooting") / 1000) + cooldownLengthint) - (System.currentTimeMillis() / 1000);
                
                if (intervals.contains((int) secondsLeft)) { //For the messages
                    Bukkit.broadcastMessage(cfgYaml.getString(String.format("Messages.%d", secondsLeft)).replaceAll("&", "§"));
                }
                
                if (secondsLeft == 0) {
                    try {
                        rebootNow();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        
        try {
            plugin.getServer().getScheduler().runTaskTimer(plugin, run, 0L, 20L);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }
}

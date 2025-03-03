package prisoncore.aDragz.Features.Tokens.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import prisoncore.aDragz.Features.Admin.Features.Pickaxe.Commands.pickaxeGive;
import prisoncore.aDragz.Features.Logs.LogManagement.Tokens.storeTokenTransactions;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;
import prisoncore.aDragz.main;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class tokenPlayerJoin implements EventListener, Listener {

    private static final main plugin = main.getPlugin(main.class);

    static File cfgFile = new File(plugin.getDataFolder(), "tokens.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    static File cfgTransactionsFile = new File(plugin.getDataFolder(), "token_transactions.yml");
    static FileConfiguration cfgTransactionsYaml = YamlConfiguration.loadConfiguration(cfgTransactionsFile);

    @EventHandler
    public static void playerJoinEvent(PlayerJoinEvent event) {
        //Grab the player ID. Then try to find in the multipliers.yml file.
        UUID ID = event.getPlayer().getUniqueId();
        
        if (Objects.isNull(storeTokens.tokens.get(event.getPlayer().getUniqueId().toString()))) {
            try {
                    storeTokens.tokens.put(event.getPlayer().getUniqueId().toString(), 0L);
                    
                    //This means they have not joined server
                    //First time pickaxe give:
                    
                    String args[] = Arrays.asList("pickaxe", "give", event.getPlayer().toString(), "Efficiency", "10").toArray(new String[0]);
                    
                    pickaxeGive.givePickaxe(event.getPlayer(), args);
                    
                    //Transactions
                    
                    if (Objects.isNull(cfgTransactionsYaml.get(ID.toString()))) {
                        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        LocalDateTime timeNow = LocalDateTime.now();
                        List<String> transactionsCreate = Collections.singletonList(String.format("§8(§9§lToken§8§l-§9§lLogs§8) §f0 §7--> §f0 §7- §aCreated Tokens File §8(§f%s§8)", date.format(timeNow)));
                        storeTokenTransactions.transactions.put(event.getPlayer().getUniqueId().toString(), transactionsCreate);
                    }
                } catch (Exception Ignored) {
                if (!event.getPlayer().hasPermission("PrisonCore.Admin")) {
                    event.getPlayer().kickPlayer("An Error has occurred - Please contact an Administrator!");
                }
            }
            
            
        }
    }
}

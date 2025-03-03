package prisoncore.aDragz.Features.Tokens;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EventListener;

public class tokensFileManager implements EventListener, Listener {
    private final static main plugin = main.getPlugin(main.class);

    //First time join

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        File token = new File(plugin.getDataFolder(), String.format("Tokens/%s.yml", event.getPlayer().getUniqueId()));
        FileConfiguration tokenCfg = YamlConfiguration.loadConfiguration(token);

        if (!token.exists()) {
            ArrayList<String> transactions = new ArrayList<>();

            DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime timeNow = LocalDateTime.now();

            transactions.add(String.format("§8[§9§lToken§8§l-§9§lLogs§8] §f0 §7--> §f0 §7- §aCreated Tokens File §8[§f%s§8]", date.format(timeNow)));

            tokenCfg.set("IGN", event.getPlayer().getName());
            tokenCfg.set("Tokens", 0);
            tokenCfg.set("Transactions", transactions);
            tokenCfg.save(token);
        }
    }

}

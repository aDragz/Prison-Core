package prisoncore.aDragz.Features.Sell.Sellall.events;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Sell.Multipliers.grabMultiplier;
import prisoncore.aDragz.main;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class storeMoneyPrices {
    //Every 60 seconds, send a player a message!

    public static HashMap<String, Float> playerSellAmount = new HashMap<String, Float>(); //Using Player, as I dealt anyone would mine a block, and change ign in under 60 seconds.

    private static ArrayList<Player> onlinePlayers = new ArrayList<Player>();
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###.00");
    
    private final static main plugin = main.getPlugin(main.class);
    static File messagesFile = new File(plugin.getDataFolder(), "Messages.yml");
    static FileConfiguration messagesCfg = YamlConfiguration.loadConfiguration(messagesFile);
    
    private static List<String> totalSoldMessage = messagesCfg.getStringList("mine.total_sold");

    public static void sendMessages() {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool ( 1 );

        Runnable run = () -> {
            //Add all to a clear ArrayList
            if (Bukkit.getOnlinePlayers().size() != 0) {
                onlinePlayers.addAll(Bukkit.getOnlinePlayers());
            }

            for (Player player : Bukkit.getOnlinePlayers()) { //Grabs every Player in onlinePlayers
                if (!Objects.isNull(playerSellAmount.get(player.getName()))) {
                    String amount = String.valueOf(playerSellAmount.get(player.getName()));
                    Float longAmount = Float.parseFloat(amount);
                    playerSellAmount.remove(player.getName());

                    if (longAmount != 0F) {
                        //player.getPlayer().sendMessage("§e§lTotal Sold§r: §a$§fAMOUNT"
                        //        .replaceAll("AMOUNT", decimalFormat.format(longAmount)));
                        
                        totalSoldMessage.forEach(S -> {
                            player.sendMessage(S.toString().replaceAll("TOTAL", decimalFormat.format(longAmount))
                                    .replaceAll("MULTI", String.valueOf(grabMultiplier.grabUUIDMultiplier(player.getUniqueId())))
                                    .replaceAll("&", "§"));
                        });
                    }
                }
            }

            //Clear ArrayList or it will make an infinite loop
            if (onlinePlayers.size() != 0) {
                onlinePlayers.clear();
            }
            return;
        };

        try {
            executor.scheduleAtFixedRate(run, 2L, 2L, TimeUnit.MINUTES);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }
}

package prisoncore.aDragz.Features.Gangs.management.gangChat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;

public class gangChat implements Listener {
    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();

    private static final String messagePrefix = config.getString("Type.Gangs.Chat.Prefix");
    private static final String messageFormat = config.getString("Type.Gangs.Chat.Format");

    @EventHandler
    public static void onChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Long gangID = private_mines_grabValues.grabGangID(player).longValue();

        if (activeGangChat.IDsList.contains(gangID)) {
            if (checkChatEvent(event.getMessage())) {
                event.setCancelled(true);

                ArrayList<String> members = new ArrayList<String>();

                File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangCheckInfo.grabGangName(player)));
                FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

                members.addAll(gangCheckInfo.grabOnlinePlayers(gangFile, gangCfg));

                String finalMessage = messageFormat
                        .replaceAll("&", "§")
                        .replaceAll("GANGNAME", gangCheckInfo.grabGangName(player))
                        .replaceAll("SENDERNAME", player.getName())
                        .replaceAll("SENDERNAMELOWER", player.getName().toLowerCase())
                        .replaceAll("SENDERNAMEHIGHER", player.getName().toUpperCase())
                        .replaceAll("MESSAGE", event.getMessage().substring(1));

                for (int i = 0; i != members.size(); ) {
                    try {
                        Player memberplayer = Bukkit.getPlayer(members.get(i));

                        //"&8[&6GANGNAME&8] &cSENDERNAME &7» &fMESSAGE"

                        if (memberplayer.isOnline()) {
                            memberplayer.sendMessage(finalMessage);
                        }
                        i += 1;
                    } catch (NullPointerException e) {
                        i += 1;
                    }
                }
            }
        }
    }

    public static Boolean checkChatEvent(String chatMessage) {
        
        return chatMessage.startsWith(messagePrefix);
    }

}

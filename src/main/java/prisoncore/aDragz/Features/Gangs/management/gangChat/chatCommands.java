package prisoncore.aDragz.Features.Gangs.management.gangChat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class chatCommands {
    private static final main plugin = main.getPlugin(main.class);

    public static void commands(String[] args, Long ID, Player player) throws IOException {

        if (args.length != 2) {
            if (activeGangChat.IDsList.contains(ID)) {
                player.sendMessage(grabMessagesValue.type("gang", "chat_no_args").replaceAll("&", "§"));
            } else {
                player.sendMessage(grabMessagesValue.type("gang", "chat_no_args_disabled").replaceAll("&", "§"));
                
            }
            return;
        }

        switch (args[1].toLowerCase()) {
            case "toggle":

                File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangCheckInfo.grabGangName(player)));
                FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

                if (activeGangChatToggle.toggleChat(ID, gangFile, gangCfg)) {
                    player.sendMessage(grabMessagesValue.type("gang", "chat_enable").replaceAll("&", "§"));
                } else {
                    player.sendMessage(grabMessagesValue.type("gang", "chat_disable").replaceAll("&", "§"));
                }
        }
    }
}

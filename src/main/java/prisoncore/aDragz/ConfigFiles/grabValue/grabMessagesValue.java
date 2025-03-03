package prisoncore.aDragz.ConfigFiles.grabValue;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.Objects;

public class grabMessagesValue implements Listener {
    //This file makes it easier to grab values like rankup_accept
    private final static main plugin = main.getPlugin(main.class);
    static File messagesFile = new File(plugin.getDataFolder(), "Messages.yml");
    static FileConfiguration messagesCfg = YamlConfiguration.loadConfiguration(messagesFile);

    public static String type(String type, String value) {
        //grab value from messages.yml
        //
        //example:
        //message = grabMessagesValue.type("rankup", "accept").replaceAll("&", "§")
        //        .replaceAll("NEXTRANKNAME", nextRankName.toString()) //Enter so it's easier to read instead of 1 massive line
        //        .replaceAll("RANKPRICE", String.valueOf(rankPrice)); //Makes into readable message from the user
        //player.sendMessage(message);

        if (Objects.isNull(messagesCfg.getString(type.toLowerCase() + "." + value.toLowerCase()))) {
            return String.format("§cValue not found! Value = §f%s §c/ §f%s §c- Check §a%s §cfor more information", type, value, "https://www.prisoncore.dev/configs/messages-yml");
        }

        return messagesCfg.getString(type.toLowerCase() + "." + value.toLowerCase());
    }
}
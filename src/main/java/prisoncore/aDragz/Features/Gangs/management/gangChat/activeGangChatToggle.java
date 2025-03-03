package prisoncore.aDragz.Features.Gangs.management.gangChat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;


public class activeGangChatToggle {
    public static Boolean toggleChat(Long ID, File gangFile, FileConfiguration gangCfg) throws IOException {
        try {
            if (activeGangChat.IDsList.contains(ID)) {
                toggleChatSave(false, gangFile, gangCfg); //Disable
                activeGangChat.IDsList.remove(ID);
                return false;
            } else {
                toggleChatSave(true, gangFile, gangCfg); //Enable
                activeGangChat.IDsList.add(ID);
                return true;
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
            return false;
        }
    }

    public static void toggleChatSave(Boolean toggle, File gangFile, FileConfiguration gangCfg) throws IOException {
        gangCfg.set("Info.Features.Chat", toggle);
        gangCfg.save(gangFile);
    }
}

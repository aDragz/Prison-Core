package prisoncore.aDragz.Features.Gangs.management;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.Features.Gangs.management.gangChat.activeGangChatToggle;
import prisoncore.aDragz.Features.PrivateMines.PMineCommandHandler;
import prisoncore.aDragz.Features.PrivateMines.conditions.cooldown;
import prisoncore.aDragz.Features.PrivateMines.generateLocation;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class gangCreate {
    private final static main plugin = main.getPlugin(main.class);
    private static final FileConfiguration config = plugin.getConfig();
    private static final boolean gangChatValue = config.getBoolean("Type.Gangs.Chat.Default_Value");

    static String message;

    public static void createGang(Player player, String[] args) throws IOException {
        if (gangCheckInfo.checkGangStatus(player)) {
            message = grabMessagesValue.type("gang", "gang_found").replaceAll("&", "§");
            player.sendMessage(message);
            return;
        }

        if (args.length <= 1) {
            message = grabMessagesValue.type("gang", "no_name").replaceAll("&", "§");
            player.sendMessage(message);
            return;
        }
        
        String gangName = args[1];
        
        //Work it so it removes certain characters and words:
        //For now, very basic swear filter. But I will make a proper system soon
        if (gangName.contains("&")) {
            player.sendMessage(grabMessagesValue.type("gang", "no_name").replaceAll("&", "§") + ChatColor.DARK_RED + " - Contains &");
            return;
        }

        File gangFolder = new File(plugin.getDataFolder(), ("Gangs/")); //For the generateGangID
        File gangFile = new File(gangFolder + String.format("/%s.yml", gangName));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

        if (gangFile.exists()) {
            message = grabMessagesValue.type("gang", "exists").replaceAll("&", "§");
            player.sendMessage(message);
            return;
        }
        
        if (!player.hasPermission("PrisonCore.Admin")) {
            if (cooldown.upgradeCooldown(player)) {
                return;
            }
        }

        //Set default variables for easy access
        gangCfg.set("Info.Leader", player.getName());
        gangCfg.set("Info.Bank", 0);

        gangCfg.set("Members", "");

        gangCfg.set("Invites", "");

        //Generate Private Mine Configs:
        gangCfg.set("Mine.Tier", 1);

        gangCfg.save(gangFile);

        //Place into an ArrayList, so they cannot tp to mine currently
        PMineCommandHandler.disableTpPlayers.add(player);

        message = grabMessagesValue.type("gang", "created").replaceAll("&", "§")
                .replaceAll("GANGNAME", gangName);
        player.sendMessage(message);

        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

        playerCfg.set("Info.Gang.Name", gangName);
        playerCfg.save(playerFile);

        int gangID = generateGangID(player, gangFolder, gangFile, gangCfg);

        gangCheckInfo.clearInvites(player);

        //Enable gang chat:
        if (gangChatValue) {
            Long i = Long.valueOf(gangID);
            activeGangChatToggle.toggleChat(i, gangFile, gangCfg);
        }

        //Create player mine:
        generateLocation.generateMine(player, gangID);
    }

    static Integer generateGangID(Player player, File folder, File file, FileConfiguration cfg) {
        //ID is mainly for mine creation to generate the location of the main.
        //ID is made from the amount of files in the gang location, and then adds 1.
        //This generates the ID so there are no accidental duplicates of the same number.

        //Was using long, now int. Because it has a high of over 2 Million. I highy dealt any server will have
        //2 million players in 1 prison season.

        int ID;

        try { //Incase there are no files, it creates a NullPointerException, where it will not generate ID. To prevent, use this try/catch.
            ID = folder.list().length + 1; //Gets the amount of files to generate the ID.
        } catch (NullPointerException e) {
            ID = 1;
        }

        cfg.set("Info.ID", ID);
        try {
            cfg.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }

        return ID;
    }
}

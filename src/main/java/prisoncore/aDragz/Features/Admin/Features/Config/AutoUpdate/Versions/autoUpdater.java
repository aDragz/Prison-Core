package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.v216.updateEnchantments216;
import prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.v218.updateEnchantments218;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class autoUpdater {
    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();

    static File messagesFile = new File(plugin.getDataFolder(), "Messages.yml");
    static FileConfiguration messagesCfg = YamlConfiguration.loadConfiguration(messagesFile);

    static File enchantmentsFile = new File(plugin.getDataFolder(), "Messages.yml");
    static FileConfiguration enchantmentsCfg = YamlConfiguration.loadConfiguration(messagesFile);


    public static int grabOldVersion() {
        return config.getInt("Version");
    }

    public static void setNewVersion(int newVersion) {
        config.set("Version", newVersion);
        plugin.saveConfig();
    }

    public static void setConfigFiles(CommandSender player) throws IOException {
        int oldVersion = grabOldVersion();

        player.sendMessage("Updating Prison-Core");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "Changes:");

        if (oldVersion < 215) {
            messagesCfg.set("mine.tp_decline", "&c&lPrison&8&l-&c&lCore &8&l» &cYou cannot tp to your mine currently!");
            messagesCfg.save(messagesFile);

            player.sendMessage(ChatColor.GREEN + "Messages.yml - " + ChatColor.GRAY + "mine.tp_decline");

            setNewVersion(215);
        }

        if (oldVersion < 216) {
            updateEnchantments216.updateMessages();
            messagesCfg.save(messagesFile);

            player.sendMessage(ChatColor.GREEN + "Enchantments.yml - " + ChatColor.GRAY + "Pickaxe.Lore");
            player.sendMessage(ChatColor.GREEN + "Enchantments.yml - " + ChatColor.GRAY + "Pickaxe.Lore-Special.Blocks-Broken.Enabled");
            player.sendMessage(ChatColor.GREEN + "Enchantments.yml - " + ChatColor.GRAY + "Pickaxe.Lore-Special.Blocks-Broken.Line");
            player.sendMessage(ChatColor.GREEN + "Enchantments.yml - " + ChatColor.GRAY + "Pickaxe.Lore-Special.Blocks-Broken.Lore");
            player.sendMessage(ChatColor.GREEN + "Enchantments.yml - " + ChatColor.GRAY + "Pickaxe.Lore-Special.Pickaxe-Level.Enabled");
            player.sendMessage(ChatColor.GREEN + "Enchantments.yml - " + ChatColor.GRAY + "Pickaxe.Lore-Special.Pickaxe-Level.Line");
            player.sendMessage(ChatColor.GREEN + "Enchantments.yml - " + ChatColor.GRAY + "Pickaxe.Lore-Special.Pickaxe-Level.Lore");

            setNewVersion(216);
        }

        if (oldVersion < 218) {
            updateEnchantments218.updateMessages();
            enchantmentsCfg.save(enchantmentsFile);

            player.sendMessage(ChatColor.GREEN + "Enchantments.yml - " + ChatColor.GRAY + "Pickaxe.Drop-Type");

            setNewVersion(218);
        }

        //Finished all updates:

        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "Please use " + ChatColor.DARK_GRAY + "'/reboot'" + ChatColor.GRAY + " to apply these changes!");
    }
}

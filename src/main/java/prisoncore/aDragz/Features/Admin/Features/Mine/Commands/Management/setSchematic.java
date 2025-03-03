package prisoncore.aDragz.Features.Admin.Features.Mine.Commands.Management;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class setSchematic {
    private final static main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "config.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    public static void setSchematic(CommandSender player, String schemName) throws IOException {
        if (Objects.isNull(schemName)) {
            player.sendMessage(ChatColor.DARK_RED + "Invalid Schematic");
        }
        
        try {
            
            File schem = new File(plugin.getDataFolder() + "/Schematics");
            File schemLocation = new File(schem + String.format("/%s.schem", schemName));
            
            if (schemLocation.exists()) {
                cfgYaml.set("Type.Private_Mine.Schematic_Name", schemName.toString());
                cfgYaml.save(cfgFile);
                
                player.sendMessage(ChatColor.GREEN + "Set Schematic to " + schemName.toString());
                player.sendMessage("");
                player.sendMessage(ChatColor.GRAY + "Please use " + ChatColor.DARK_GRAY + "'/reboot'" + ChatColor.GRAY + " to apply these changes!");
            } else {
                player.sendMessage(ChatColor.DARK_RED + "Invalid Schematic '" + ChatColor.RED + schemLocation.getPath() + ChatColor.DARK_RED + "' does not exist");
                player.sendMessage(ChatColor.GREEN + "(Use '/prisoncore schematic (name)' to convert the file to make it usable)");
                player.sendMessage("");
                player.sendMessage(ChatColor.GREEN + "Need more help? Check out these resources:");
                player.sendMessage("https://prisoncore.dev/help/private-mines/schematics");
                player.sendMessage("https://prisoncore.dev/discord");
            }
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
            throw e;
        }
    }
}

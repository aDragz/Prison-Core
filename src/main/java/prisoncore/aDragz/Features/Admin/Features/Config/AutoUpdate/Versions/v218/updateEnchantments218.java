package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.v218;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;

public class updateEnchantments218 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateMessages() throws IOException {
        cfg.set("Pickaxe.Drop-Type", "DROP");

        cfg.save(file);
    }
}

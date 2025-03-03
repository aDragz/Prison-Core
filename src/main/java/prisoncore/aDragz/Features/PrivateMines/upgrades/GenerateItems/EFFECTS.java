package prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.Objects;

public class EFFECTS {
    private static final main plugin = main.getPlugin(main.class);

    public static File file = new File(plugin.getDataFolder(), "gui/effects.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file); //Public for "./GenerateItems

    public static Material fillerItemStack() {
        return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.Filler_Block")));
    }
}

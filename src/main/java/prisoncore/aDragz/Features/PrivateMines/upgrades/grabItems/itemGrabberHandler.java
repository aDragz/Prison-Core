package prisoncore.aDragz.Features.PrivateMines.upgrades.grabItems;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.Objects;

public class itemGrabberHandler {
    private static final main plugin = main.getPlugin(main.class);
    static File upgradesFile = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(upgradesFile);

    public static Material grabItem(String type) {
        switch (type.toLowerCase()) {
            case "upgrade_locked": //Used for Locked_Item in /mine gui
                return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.Tier.Locked")));
            case "upgrade_current":
                return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.Tier.Current")));
            case "upgrade_unlocked":
                return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.Tier.Unlocked")));
            case "upgrade_filler":
                return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.Filler_Block")));
            case "page_forward":
                return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.Pages.Forward.Material")));
            case "page_backward":
                return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.Pages.Backward.Material")));
            case "upgrade":
                return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.Upgrade_Mine.Material")));
            case "effects":
                return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.Effects.Material")));
            case "minetp":
                return Material.getMaterial(Objects.requireNonNull(cfg.getString("GUI.MineTP.Material")));
            default:
                return Material.BARRIER; //Unknown item if null.
        }
    }

    public static String grabDisplayName(String type) {
        switch (type.toLowerCase()) {
            case "page_forward":
                return Objects.requireNonNull(cfg.getString("GUI.Pages.Forward.Display_Name").replaceAll("&", "§"));
            case "page_backward":
                return Objects.requireNonNull(cfg.getString("GUI.Pages.Backward.Display_Name").replaceAll("&", "§"));
            case "upgrade":
                return Objects.requireNonNull(cfg.getString("GUI.Upgrade_Mine.Display_Name").replaceAll("&", "§"));
            case "effects":
                return Objects.requireNonNull(cfg.getString("GUI.Effects.Display_Name").replaceAll("&", "§"));
            case "minetp":
                return Objects.requireNonNull(cfg.getString("GUI.MineTP.Display_Name").replaceAll("&", "§"));
            case "blocks":
                return Objects.requireNonNull(cfg.getString("GUI.Blocks.Display_Name").replaceAll("&", "§"));
            default:
                return "null";
        }
    }
}

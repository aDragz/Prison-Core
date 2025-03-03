package prisoncore.aDragz.Features.PrivateMines.upgrades.Blocks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;

public class blocksGrabTier {
    private static final main plugin = main.getPlugin(main.class);
    private static final File file = new File(plugin.getDataFolder(), ("Configs/private_mine.yml"));
    private static final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    
    private static int pageMultiplier = 176;
    private static int tierMultiplier = cfg.getInt("Block-Tiers.Tier-Increase");
    
    public static int tier(int blockTier, int page) {
        if (page == 2 ) {
            return ((blockTier*tierMultiplier)+(pageMultiplier)+tierMultiplier);
        } if (page == 3 ) {
            return ((blockTier*tierMultiplier)+(pageMultiplier)+ (pageMultiplier) + tierMultiplier + tierMultiplier);
        } else {
            return (blockTier*tierMultiplier);
        }
    }
    
    public static int slotToTier(int slot, int page) {
        if (page == 2 ) {
            return ((slot*tierMultiplier)+(pageMultiplier)+tierMultiplier);
        } else if (page == 3 ) {
            return ((slot * tierMultiplier) + (pageMultiplier) + (pageMultiplier) + tierMultiplier + tierMultiplier);
        } else {
            return (slot*tierMultiplier);
        }
    }
}

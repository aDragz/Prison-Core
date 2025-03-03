package prisoncore.aDragz.Features.PrivateMines.regions.mine_plots;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.RemovalStrategy;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.main;

import java.io.File;

import static prisoncore.aDragz.data.gang_grabValues.gangLeader;

public class deletePlotsRegion {

    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();
    static File upgradesFile = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    static FileConfiguration upgradesCfg = YamlConfiguration.loadConfiguration(upgradesFile);

    public static void deletePlotRegion(Player player) {
        //Used for gang removal.

        World world = Bukkit.getWorld(config.getString("Type.Private_Mine.World"));
        String leader = gangLeader(player);

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(world));

        regions.removeRegion("plot-" + leader, RemovalStrategy.UNSET_PARENT_IN_CHILDREN);
    }
}

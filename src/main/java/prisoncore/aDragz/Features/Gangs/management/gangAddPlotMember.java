package prisoncore.aDragz.Features.Gangs.management;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.main;

public class gangAddPlotMember {
    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();
    static World world = Bukkit.getWorld(config.getString("Type.Private_Mine.World"));

    static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    static RegionManager regions = container.get(BukkitAdapter.adapt(world));

    public static void addPlayerPlotRegion(String leader, Player player) {
        ProtectedRegion region = regions.getRegion("mine-" + leader);

        region.getMembers().addPlayer(player.getPlayer().getName());

        ProtectedRegion regionPlot = regions.getRegion("plot-" + leader);

        regionPlot.getMembers().addPlayer(player.getPlayer().getName());
    }
}

package prisoncore.aDragz.Features.PrivateMines.regions.mine_plots;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.main;

import java.io.File;

import static prisoncore.aDragz.data.gang_grabValues.gangLeader;

public class createPlotsRegion {

    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();
    static File upgradesFile = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    static FileConfiguration upgradesCfg = YamlConfiguration.loadConfiguration(upgradesFile);

    public static void createRegion(Player player, long width, long mineSize, long mineHeight) {

        /*Only create region on first-time setup. And when you are upgrading mine.
        It's a waste of resources to keep on doing it after resetting. Resetting takes
        enough time as it is.
        */

        int halfWidth = (int) (mineSize * 2);

        int xPos1 = (-config.getInt("Type.Private_Mine.Width"));
        int xPos2 = (config.getInt("Type.Private_Mine.Width"));

        int yPos1 = 256; //Different values from static
        int yPos2 = 0; //+1 = So it doesn't get on the bedrock floor

        int zPos1 = (int) ((width - 5 + config.getInt("Type.Private_Mine.Width")) - 1);
        int zPos2 = (int) ((width + 5 - config.getInt("Type.Private_Mine.Width")) - 1);

        String leader = gangLeader(player);

        World world = Bukkit.getWorld(config.getString("Type.Private_Mine.World"));
        BlockVector3 min = BlockVector3.at(xPos1, yPos1, zPos1);
        BlockVector3 max = BlockVector3.at(xPos2, yPos2, zPos2);

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(world));

        ProtectedCuboidRegion mineRg = new ProtectedCuboidRegion("plot-" + leader, min, max);

        mineRg.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
        mineRg.setFlag(Flags.BLOCK_BREAK.getRegionGroupFlag(), RegionGroup.MEMBERS);
        mineRg.setFlag(Flags.BLOCK_PLACE, StateFlag.State.ALLOW);
        mineRg.setFlag(Flags.BLOCK_PLACE.getRegionGroupFlag(), RegionGroup.MEMBERS);
        mineRg.setFlag(Flags.ITEM_PICKUP, StateFlag.State.ALLOW);
        mineRg.setFlag(Flags.ITEM_PICKUP.getRegionGroupFlag(), RegionGroup.MEMBERS);


        mineRg.getMembers().addPlayer(leader); //Adds leader to Plot region

        mineRg.setPriority(1);

        regions.addRegion(mineRg);
    }
}

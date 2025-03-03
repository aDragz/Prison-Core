package prisoncore.aDragz.Features.PrivateMines.regions.mine_blocks;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.PrivateMines.PMineCommandHandler;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;

import static prisoncore.aDragz.data.gang_grabValues.gangLeader;

public class createRegion {

    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();
    static File upgradesFile = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    static FileConfiguration upgradesCfg = YamlConfiguration.loadConfiguration(upgradesFile);

    public static void createRegion(Player player, long width, long mineSize, long mineHeight) {

        /*Only create region on first-time setup. And when you are upgrading mine.
        It's a waste of resources to keep on doing it after resetting. Resetting takes
        enough time as it is.
        */

        //For after first time setup, get all members!
        String leader = gangLeader(player);

        ArrayList<String> BeforeMembers = new ArrayList<>();

        try {
            BeforeMembers.addAll(addRegionMembers(leader));
        } catch (Exception e) {
        }

        int halfWidth = (int) (mineSize / 2);

        int xPos1 = (-halfWidth)-1;
        int xPos2 = (halfWidth)+1;

        int yPos1 = PMineCommandHandler.mineHeight; //Different values from static
        int yPos2 = (int) (PMineCommandHandler.mineHeight - mineHeight)-1;

        int zPos1 = (int) (width - halfWidth)-1;
        int zPos2 = (int) (width + halfWidth)+1;

        World world = Bukkit.getWorld(config.getString("Type.Private_Mine.World"));
        BlockVector3 min = BlockVector3.at(xPos1, yPos1, zPos1);
        BlockVector3 max = BlockVector3.at(xPos2, yPos2, zPos2);

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(world));

        ProtectedCuboidRegion mineRg = new ProtectedCuboidRegion("mine-" + leader, min, max);

        if (config.getBoolean("Type.Private_Mine.Region_Flags.FALL_DAMAGE")) {
            mineRg.setFlag(Flags.FALL_DAMAGE, StateFlag.State.ALLOW);
        } else {
            mineRg.setFlag(Flags.FALL_DAMAGE, StateFlag.State.DENY);
        }

        if (config.getBoolean("Type.Private_Mine.Region_Flags.ITEM_DROP")) {
            mineRg.setFlag(Flags.ITEM_DROP, StateFlag.State.ALLOW);
        } else {
            mineRg.setFlag(Flags.ITEM_DROP, StateFlag.State.DENY);
        }

        if (config.getBoolean("Type.Private_Mine.Region_Flags.ITEM_PICKUP")) {
            mineRg.setFlag(Flags.ITEM_PICKUP, StateFlag.State.ALLOW);
        } else {
            mineRg.setFlag(Flags.ITEM_PICKUP, StateFlag.State.DENY);
        }

        mineRg.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
        mineRg.setFlag(Flags.BLOCK_BREAK.getRegionGroupFlag(), RegionGroup.MEMBERS);
        mineRg.setFlag(Flags.BLOCK_PLACE, StateFlag.State.ALLOW);
        mineRg.setFlag(Flags.BLOCK_PLACE.getRegionGroupFlag(), RegionGroup.MEMBERS);


        if (!BeforeMembers.isEmpty()) {
            for (int i = 0; i != BeforeMembers.size(); ) {
                mineRg.getMembers().addPlayer(BeforeMembers.get(i));
                i += 1;
            }
        } else {
            mineRg.getMembers().addPlayer(leader); //Adds leader to Plot region
        }

        mineRg.setPriority(2);

        regions.addRegion(mineRg);
    }

    static World world = Bukkit.getWorld(config.getString("Type.Private_Mine.World"));

    static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    static RegionManager regions = container.get(BukkitAdapter.adapt(world));

    private static ArrayList<String> addRegionMembers(String leader) {
        ArrayList<String> members = new ArrayList<String>();

        ProtectedRegion region = regions.getRegion("mine-" + leader);

        members.addAll(region.getMembers().getPlayers());

        return members;
    }
}

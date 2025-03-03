package prisoncore.aDragz.Features.PrivateMines.main;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems.BLOCKS;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class resetMine {
    //To reset the mine, the user will need to type 'mine reset'
    
    //This is to reduce lag. It can be ran anytime, but needs a certain amount destroyed in the mine.
    //Like 20,000 out of 100,000 blocks.
    
    //It will use a callable / runnable so it doesn't affect the server performance.
    
    //To calculate the Mine POS, we get all x + y axis. Then the tier sizes. We halve the x
    //If it's 200x200 and the x is 400. It will add 100 and remove 100. So 500 --> 300.
    
    private static final main plugin = main.getPlugin(main.class);
    static File upgradesFile = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    static FileConfiguration upgradesCfg = YamlConfiguration.loadConfiguration(upgradesFile);
    
    static File blocksFile = new File(plugin.getDataFolder(), "gui/blocks.yml");
    static FileConfiguration blocksCfg = YamlConfiguration.loadConfiguration(blocksFile);
    
    private static final FileConfiguration config = plugin.getConfig();
    
    //Grab deafult height for yPos
    static int yPos1 = config.getInt("Type.Private_Mine.Height");
    
    public static boolean resetMine(Player player, long width, long mineSize, long mineHeight, boolean generateBedrock) {
        try {
            //Width + height = Tier width/height
            //X + y = player pos.
            
            int halfWidth = (int) (mineSize / 2);
            
            int xPos1 = (-halfWidth);
            int xPos2 = (halfWidth);

            /*New system for y pos, grab top height, and minus from mine height. 
            So 100 = default. Size = 5. It goes to 95. 10 = 90.*/
            
            /*Old system for y pos, get the height and make that the top.
             * So 100 = 100, 90 = 90. Not best for schematics as it just goes up.
             */
            //int yPos1 = 1;
            //int yPos2 = (int) (mineHeight + 1);            
            
            //int yPos1 is static above code.
            int yPos2 = (int) (yPos1 - mineHeight) + 1;
            
            int zPos1 = (int) (width - halfWidth);
            int zPos2 = (int) (width + halfWidth);

            World world = FaweAPI.getWorld(config.getString("Type.Private_Mine.World")); // Get the WorldEdit world from the spigot world by using BukkitAdapter
            CuboidRegion selection = new CuboidRegion(world, BlockVector3.at(xPos1, yPos1, zPos1), BlockVector3.at(xPos2, yPos2, zPos2)); // make a selection with two points
            
            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) { // get the edit session and use -1 for max blocks for no limit, this is a try with resources statement to ensure the edit session is closed after use
                
                //Pass in the region and pattern to setblocks to
                RandomPattern blocks = new RandomPattern(); // Create the random pattern
                //Make the various WorldEdit blockstates by using the BukkitAdapter from the spigot blockdata
                int currentTier = private_mines_grabValues.currentTier(player);
                BlockState block = BukkitAdapter.adapt(BLOCKS.returnBlock(player).createBlockData());
                blocks.add(block, 1);
                
                editSession.setBlocks((Region) selection, blocks);
                
                if (generateBedrock) {
                    generateBedrock(xPos1, xPos2, yPos1, yPos2, zPos1, zPos2, world);
                }
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(e.getMessage());
                return false;
            }
            return true;
        } catch (Exception Ignored){}
        return generateBedrock;
    }
    
    
    public static boolean generateBedrock(int xPos1, int xPos2, int yPos1, int yPos2, int zPos1, int zPos2, World world) {
        CuboidRegion bedrockSelection = new CuboidRegion(world, BlockVector3.at(xPos1 - 1, yPos1, zPos1 - 1), BlockVector3.at(xPos2 + 1, yPos2, zPos2 + 1)); //Generate bedrock round the blocks. No need for y+1 as you don't want to cover whole area with bedrock. [Mainly top]
        CuboidRegion bedrockFloor = new CuboidRegion(world, BlockVector3.at(xPos1, yPos2 - 1, zPos1), BlockVector3.at(xPos2, yPos2 - 1, zPos2)); //Don't need -1, +1 etc as it's already filled in. Y is 0 because Y=0 all the time.
        
        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) { // get the edit session and use -1 for max blocks for no limit, this is a try with resources statement to ensure the edit session is closed after use
            //Try and create bedrock outside the ring now!
            List<String> mineBorderMaterials = new ArrayList<>();
            mineBorderMaterials.addAll(blocksCfg.getStringList("Border"));
            
            RandomPattern blocks = new RandomPattern(); // Create the random pattern
            
            mineBorderMaterials.forEach(s -> {
                BlockState border = BukkitAdapter.adapt(Material.valueOf((s.toUpperCase())).createBlockData());
                blocks.add(border, 1);
            });
            
            editSession.makeWalls(bedrockSelection, blocks);
            editSession.setBlocks((Region) bedrockFloor, blocks);
        } catch (MaxChangedBlocksException ex) {
            ex.printStackTrace();
        }
        return true;
    }
}

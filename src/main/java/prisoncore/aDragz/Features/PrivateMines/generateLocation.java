package prisoncore.aDragz.Features.PrivateMines;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class generateLocation {
    //To generate mine location, you need a:

    //world
    // x,y,z, axis
    // gang ID
    // Width of the mine

    // y = Depth +5. So a Depth of 100 will be y 105.
    // X = 0
    // Z = 1 * (Width * 2) * Gang ID - So IGN's don't show / interfere with other players

    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();

    static String schemName = config.getString("Type.Private_Mine.Schematic_Name");
    static File file = new File(plugin.getDataFolder() + String.format("/Schematics/%s.schem", schemName));
    static ClipboardFormat format = ClipboardFormats.findByFile(file);

    public static void generateMine(Player player, int gangID) throws IOException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> tp = executor.submit(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                if (!file.exists()) {
                    player.sendMessage(ChatColor.DARK_RED + "File " + file.getName() + " does not exist!");
                    player.sendMessage(ChatColor.RED + "Need help? Check out " + ChatColor.GREEN + "https://www.prisoncore.dev/help/private-mines/schematics");
                    return false;
                }
                byte x = 0; //just 0, no need for anymore.
                long y = (config.getLong("Type.Private_Mine.Height") + 1);
                long z = ((config.getLong("Type.Private_Mine.Width") * 2) * gangID);

                String worldString = Objects.requireNonNull(config.getString("Type.Private_Mine.World"));
                World world = Bukkit.getWorld(worldString);

                //int gangID = //generate and grab ID. By getting the total amount of files and adding 1 to the value, saving in the file.

                schematic(x, y, z, world);
                //doesn't tp async
                PMineCommandHandler.generateNewMine(player); //Same as reset, just creates region for first-time use mine.
                return true;
            }
        });
        if (tp.isDone()) {
            PMineCommandHandler.mineTp(player);
        }
    }

    public static void schematic(byte x, long y, long z, org.bukkit.World world) {
        Clipboard clipboard = null;

        try (ClipboardReader reader = Objects.requireNonNull(format.getReader(new FileInputStream(file)))) {
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (EditSession edit = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))) {
            Operation operation = new ClipboardHolder(Objects.requireNonNull(clipboard))
                    .createPaste(edit)
                    .to(BlockVector3.at(x, y, z))
                    .ignoreAirBlocks(true)
                    .copyEntities(false)
                    .build();
            Operations.complete(operation);
            edit.flushQueue();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.toString() + " - Please message an Administrator! - a");
        }
    }
}

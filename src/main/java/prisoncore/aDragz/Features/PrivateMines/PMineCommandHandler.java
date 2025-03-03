package prisoncore.aDragz.Features.PrivateMines;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.Features.PrivateMines.conditions.cooldown;
import prisoncore.aDragz.Features.PrivateMines.main.resetMine;
import prisoncore.aDragz.Features.PrivateMines.regions.mine_blocks.createRegion;
import prisoncore.aDragz.Features.PrivateMines.regions.mine_plots.createPlotsRegion;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateGUI.BlocksGUI;
import prisoncore.aDragz.Features.PrivateMines.upgrades.upgradeManager.upgradeSize;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static prisoncore.aDragz.Features.PrivateMines.main.displayHelp.showHelpMenu;
import static prisoncore.aDragz.data.private_mines_grabValues.grabGangID;

public class PMineCommandHandler implements Listener, CommandExecutor {
    //handles all the command for the private mine
    //I am trying a different layout. May be implemented to all features in a different release.
    //This is to try and make it easier to manage bulk commands.

    private static final main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();
    static File upgradesFile = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    static FileConfiguration upgradesCfg = YamlConfiguration.loadConfiguration(upgradesFile);

    //Will reset on reboot rather than saving
    public static ArrayList<Player> disableTpPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("Mine")) {
            //Before anything, we need to check if they are actually in a gang.

            if (!(gangCheckInfo.checkGangStatus(player))) {
                player.sendMessage(grabMessagesValue.type("gang", "gang_not_found").replaceAll("&", "§"));
                return false;
            }

            if (args.length != 0) {
                switch (args[0].toLowerCase()) {
                    case "help":
                        //Displays help menu to [Player]
                        showHelpMenu(player);
                        break;
                    case "tp":
                        mineTp(player);
                        break;
                    case "reset":
                        //Resets the Mine.
                        if (!cooldown.isOnCooldown(player)) {
                            resetMine(player, true, false);
                        }

                        break;
                    case "upgrade":
                            upgradeSize.upgradeMineSize(player);

                        break;
                    case "blocks":
                        try {
                            player.openInventory(BlocksGUI.generateInventory(player, 1));
                        } catch (ExecutionException | InterruptedException e) {
                            Bukkit.getConsoleSender().sendMessage(e.getMessage());
                        }
                        
                        break;
                    default:
                        //Due to no command [/mine], [/mine unknown_command]. It will display this GUI.
                        gui.createInventory(player, args);
                        break;
                }
                return true;
            } else {
                gui.createInventory(player, args);
                return true;
            }
        }

        return false;
    }

    public static int mineHeight = (config.getInt("Type.Private_Mine.Height")); //Grab default height to then minus the height of it.
    public static long xOffset = (config.getInt("Type.Private_Mine.tp_Offset.X"));
    public static long yOffset = (config.getInt("Type.Private_Mine.tp_Offset.Y"));
    public static long zOffset = (config.getInt("Type.Private_Mine.tp_Offset.Z"));


    public static void mineTp(Player player) {
        /*
        First, check if player is in the blacklist for tp's
        For now, this is used for mine generation, so they don't die in the void.
         */

        if (disableTpPlayers.contains(player)) {
            player.sendMessage(grabMessagesValue.type("mine", "tp_decline").replaceAll("&", "§"));
            return;
        }

        try {
            //To do this, we need for first grab gang id. From this, we can calculate the x,y,z values.

            int id = grabGangID(player);

            byte x = 0; //just 0, no need for anymore.
            long y = mineHeight + 3; //This is from UpgradesCfg because it needs to be in height with the mine.
            long z = ((config.getLong("Type.Private_Mine.Width") * 2) * id); //This is from the config, because it needs to location. Due to the schematic creations.

            World world = Bukkit.getWorld(Objects.requireNonNull(config.getString("Type.Private_Mine.World")));

            //Add 3 to Y so it goes slightly above the mine
            Location tpLoc = new Location(world, x + xOffset, y + yOffset, z + zOffset);

            player.teleport(tpLoc);

            player.sendMessage(grabMessagesValue.type("mine", "tp").replaceAll("&", "§"));
        } catch (IllegalArgumentException e) {
            player.sendMessage(grabMessagesValue.type("admin", "unknown_world").replaceAll("&", "§")
                    .replaceAll("WORLD", config.getString("Type.Private_Mine.World")));
            if (player.hasPermission("PrisonCore.Admin.Mine.SetWorld"))
                player.sendMessage("Usage: /admin mine setWorld <World_Name>");
        }
    }

    public static void resetMine(OfflinePlayer player, Boolean generateBedrock, Boolean admin) {

        try {
            mineTp(player.getPlayer());
            
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Boolean> MineResetBoolean = (Future<Boolean>) executor.submit(() -> {
            
            String gangName = gangCheckInfo.grabGangName(player.getPlayer()); //For mine reset
            
            int currentTier = private_mines_grabValues.currentTier(player.getPlayer());
            int id = grabGangID(player);
            
            long width = ((config.getLong("Type.Private_Mine.Width") * 2) * id);
            long mineSize = (upgradesCfg.getLong(String.format("%d.Size", currentTier)));
            long mineHeight = (upgradesCfg.getLong(String.format("%d.Height", currentTier)));
            
            if (resetMine.resetMine(player.getPlayer(), width, mineSize, mineHeight, generateBedrock)) {
                if (!admin) {
                    player.getPlayer().sendMessage(grabMessagesValue.type("mine", "reset").replaceAll("&", "§")
                            .replaceAll("GANGNAME", gangName));
                }
            }
            });
        } catch (Exception e) {
            Bukkit.broadcastMessage(ChatColor.DARK_RED + e.getMessage());
        }
    }

    public static void generateNewMine(Player player) {
        int currentTier = private_mines_grabValues.currentTier(player);
        int id = grabGangID(player);

        long width = ((config.getLong("Type.Private_Mine.Width") * 2) * id);
        long mineSize = (upgradesCfg.getLong(String.format("%d.Size", currentTier)));
        long mineHeight = (upgradesCfg.getLong(String.format("%d.Height", currentTier)));

        if (Objects.equals(true, resetMine.resetMine(player, width, mineSize, mineHeight, true))) {
            createRegion.createRegion(player, width, mineSize, mineHeight); //Create region where they can destroy / place blocks if config allows them to.
            createPlotsRegion.createRegion(player, width, mineSize, mineHeight);

            //doesn't allow tp async - for good Mine generation
            //mineTp(player);
            player.sendMessage(grabMessagesValue.type("mine", "generated").replaceAll("&", "§"));
            disableTpPlayers.remove(player);
        }
    }

    public static void createRegionRequirements(Player player) {
        /*Used for upgrading mine. Where I don't want to keep creating different longs aka width, size, height etc.
        Easier if I add something new like world.*/

        int currentTier = private_mines_grabValues.currentTier(player);
        int id = grabGangID(player);

        long width = ((config.getLong("Type.Private_Mine.Width") * 2) * id);
        long mineSize = (upgradesCfg.getLong(String.format("%d.Size", currentTier)));

        createRegion.createRegion(player, width, mineSize, mineHeight);
    }
}

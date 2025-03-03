package prisoncore.aDragz.Features.Enchantments.EnchantmentList.advanced;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getLevel;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore.updateLore;
import prisoncore.aDragz.Features.Sell.giveMoney;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Objects;

public class tnt implements Listener, EventListener {
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    static FileConfiguration config = plugin.getConfig();
    
    static World world = FaweAPI.getWorld(config.getString("Type.Private_Mine.World"));
    
    private static int maxValue = cfgYaml.getInt("Tnt.Max-Value")+1; //+1 or it'll cancel out at 999
    private static int maxLayers = cfgYaml.getInt("Tnt.Max-Layers");
    
    static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    static RegionManager regions = container.get(world);
    
    public static void procTnt(Player player, int posYLow, String leader) {
        //Grab region
        
        ProtectedRegion region = regions.getRegion("mine-" + leader);
        
        int level = Integer.parseInt(getLevel.grabEnchantLevel(player.getInventory().getItem(0), "Tnt"));
        
        int pos1X = (region.getPoints().get(0).getX())+1;
        int pos2X = (region.getPoints().get(2).getX())-1;
        int pos1Z = (region.getPoints().get(0).getZ())+1;
        int pos2Z = (region.getPoints().get(2).getZ())-1;
        
        //Jackhammer, So it stays with the block
        //ceil rounds, making whole number
        int posYHigh = (int) (Math.ceil(posYLow) + (level / 100))+3; //ceil((Grab level) + (level/100));
        
        if (posYHigh >= maxLayers) {
            posYHigh = 13;
        }
        
        int blockAmount = 0;
        
        try {
            Region rg = new CuboidRegion(world,
                    BlockVector3.at(pos1X, posYLow, pos1Z),
                    BlockVector3.at(pos2X, posYLow+posYHigh, pos2Z));
            
            //blockAmount = (int) rg.getVolume();
            
            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) { // get the edit session and use -1 for max blocks for no limit, this is a try with resources statement to ensure the edit session is closed after use
                
                //Pass in the region and pattern to setblocks to
                RandomPattern blocks = new RandomPattern(); // Create the random pattern
                //Make the various WorldEdit blockstates by using the BukkitAdapter from the spigot blockdata
                BlockState block = BaseBlock.getState(0, 0);
                blocks.add(block, 1);
                
                editSession.setBlocks(rg, blocks);
                
                blockAmount = editSession.getBlockChangeCount();
            }
            
            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 100, 25);
            giveMoney.giveMoney(player, private_mines_grabValues.currentTier(player), blockAmount, null);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }
    
    //Pickaxe Lore:
    public static boolean addTntEnchant(Player player, int value, int oldAmountToAdd, ItemStack pickaxe) {
        if (value < maxValue) {
            try {
                ItemMeta pickMeta = pickaxe.getItemMeta();
                ArrayList<String> pickLore = (ArrayList<String>) Objects.requireNonNull(updateLore.updatePickaxeMeta(pickaxe.getItemMeta(), "Tnt", value, oldAmountToAdd));
                
                pickMeta.setLore(pickLore);
                pickaxe.setItemMeta(pickMeta);
                player.getInventory().setItem(0, pickaxe);
                return true; //Updates GUI
            } catch (Exception e) {
                player.sendMessage(ChatColor.DARK_RED + "ERROR: " + e.getMessage());
                return false;
            }
        }
        player.sendMessage(grabMessagesValue.type("enchants", "max_enchant").replaceAll("&", "§"));
        return false;
    }
}

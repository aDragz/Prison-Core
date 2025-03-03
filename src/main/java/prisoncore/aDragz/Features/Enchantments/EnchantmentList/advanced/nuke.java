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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
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
import java.util.List;
import java.util.Objects;

public class nuke implements Listener, EventListener {
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    static FileConfiguration config = plugin.getConfig();
    
    static World world = FaweAPI.getWorld(config.getString("Type.Private_Mine.World"));
    
    private static int maxValue = cfgYaml.getInt("Nuke.Max-Value")+1; //+1 or it'll cancel out at 999
    
    static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    static RegionManager regions = container.get(world);
    
    private static int yValue = config.getInt("Type.Private_Mine.Height");
    
    static File blocksFile = new File(plugin.getDataFolder(), "gui/blocks.yml");
    static FileConfiguration blocksCfg = YamlConfiguration.loadConfiguration(blocksFile);
    
    public static void proc(Player player, String leader) {
        //Grab region
        
        
        ProtectedRegion region = regions.getRegion("mine-" + leader);
        try {
            int level = Integer.parseInt(getLevel.grabEnchantLevel(player.getInventory().getItem(0), "Laser"));
            
            int pos1X = (region.getPoints().get(0).getX())+1;
            int pos2X = (region.getPoints().get(2).getX())-1;
            int pos1Z = (region.getPoints().get(0).getZ())+1;
            int pos2Z = (region.getPoints().get(2).getZ())-1;
            
            int pos1Y = yValue; //Default max height
            
            int pos2Y = pos1Y;
            
            //Grab low pos2Y:
            List<String> mineBorderMaterials = new ArrayList<>();
            mineBorderMaterials.addAll(blocksCfg.getStringList("Border"));
            for (int i = pos1Y; i >= 0; i--) {
                if (mineBorderMaterials.toString().toUpperCase().contains(Bukkit.getWorld(world.getName()).getBlockAt(pos1X, i, pos1Z).getBlockData().getMaterial().toString())) {
                    pos2Y = i++; //Add 1 so it does the one above
                    break;
                }
            }
            int blockAmount = 0;
            
            Region rg = new CuboidRegion(world,
                    BlockVector3.at(pos1X, pos1Y, pos1Z),
                    BlockVector3.at(pos2X, pos2Y +1, pos2Z));
            
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
            
            player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.MASTER, 100, 25);
            giveMoney.giveMoney(player, private_mines_grabValues.currentTier(player), blockAmount * 2, null);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }
    
    //Pickaxe Lore:
    public static boolean addEnchant(Player player, int value, int oldAmountToAdd, ItemStack pickaxe) {
        if (value < maxValue) {
            try {
                ItemMeta pickMeta = pickaxe.getItemMeta();
                ArrayList<String> pickLore = (ArrayList<String>) Objects.requireNonNull(updateLore.updatePickaxeMeta(pickaxe.getItemMeta(), "Nuke", value, oldAmountToAdd));
                
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

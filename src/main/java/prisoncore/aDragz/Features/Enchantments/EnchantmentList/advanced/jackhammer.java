package prisoncore.aDragz.Features.Enchantments.EnchantmentList.advanced;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BaseBlock;
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
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore.updateLore;
import prisoncore.aDragz.Features.Sell.giveMoney;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Objects;

public class jackhammer implements Listener, EventListener {
    
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    static FileConfiguration config = plugin.getConfig();
    
    static World world = FaweAPI.getWorld(config.getString("Type.Private_Mine.World"));
    
    static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    static RegionManager regions = container.get(world);
    
    private static int maxValue = cfgYaml.getInt("Jackhammer.Max-Value")+1; //+1 or it'll cancel out at 999
    
    public static void procJackhammer(Player player, int posY, Material block, String leader) {
        //Grab region
        
        ProtectedRegion region = regions.getRegion("mine-" + leader);
        
        int pos1X = (region.getPoints().get(0).getX())+1;
        int pos2X = (region.getPoints().get(2).getX())-1;
        int pos1Z = (region.getPoints().get(0).getZ())+1;
        int pos2Z = (region.getPoints().get(2).getZ())-1;
        
        int blockAmount = 0;
        
        try {
            for (int x = pos1X; x <= pos2X; x++) {
                for (int z = pos1Z; z <= pos2Z; z++) {
                    FaweAPI.getWorld(world.getName()).setBlock(x, posY, z, BaseBlock.getState(0, 0));
                    blockAmount++;
                }
            }
            giveMoney.giveMoney(player, private_mines_grabValues.currentTier(player), blockAmount, null);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }
    
    //Pickaxe Lore:
    public static boolean addJackhammerEnchant(Player player, int value, int oldAmountToAdd, ItemStack pickaxe) {
        if (value < maxValue) {
            try {
                ItemMeta pickMeta = pickaxe.getItemMeta();
                ArrayList<String> pickLore = (ArrayList<String>) Objects.requireNonNull(updateLore.updatePickaxeMeta(pickaxe.getItemMeta(), "Jackhammer", value, oldAmountToAdd));
                
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

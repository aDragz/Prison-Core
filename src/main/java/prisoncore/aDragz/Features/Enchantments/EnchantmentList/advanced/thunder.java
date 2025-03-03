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
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class thunder {
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
    static FileConfiguration config = plugin.getConfig();
    
    static World world = FaweAPI.getWorld(config.getString("Type.Private_Mine.World"));
    
    private static int maxValue = cfgYaml.getInt("Thunder.Max-Value")+1; //+1 or it'll cancel out at 999
    
    static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    static RegionManager regions = container.get(world);
    
    static File blocksFile = new File(plugin.getDataFolder(), "gui/blocks.yml");
    static FileConfiguration blocksCfg = YamlConfiguration.loadConfiguration(blocksFile);
    
    private static int yValue = config.getInt("Type.Private_Mine.Height");
    
    public static void procThunder(Player player, Material block, Location location, String leader, Double lightningAmount) {
        //Grab region
        
        //Get leader ign
        
        ProtectedRegion region = regions.getRegion("mine-" + leader);
        
        int level = Integer.parseInt(getLevel.grabEnchantLevel(player.getInventory().getItem(0), "Thunder"));
        int pos1XMin = (region.getPoints().get(0).getX())+1;
        int pos2XMax = (region.getPoints().get(2).getX())-1;
        int x = location.getBlockX();
        int z = location.getBlockZ();
        int pos1Y = yValue; //Base level, because it's the top of the mine.
        int pos2Y = pos1Y;
        
        Bukkit.getWorld(world.getName()).strikeLightningEffect(location);
        
        //Grab low pos2Y:
        List<String> mineBorderMaterials = new ArrayList<>();
        mineBorderMaterials.addAll(blocksCfg.getStringList("Border"));
        for (int i = pos1Y; i >= 0; i--) {
            if (mineBorderMaterials.toString().toUpperCase().contains(Bukkit.getWorld(world.getName()).getBlockAt(x, i, z).getBlockData().getMaterial().toString())) {
                pos2Y = i++; //Add 1 so it does the one above
                break;
            }
        }
        
        //Jackhammer, So it stays with the block
        //ceil rounds, making whole number
        //int posYHigh = (int) (Math.ceil(pos1Y) + (level / 100)); //ceil((Grab level) + (level/100));
        
        int blockAmount = 0;
        
        int maxLightning = cfgYaml.getInt("Thunder.Max-Lightning");
        
        if (lightningAmount > maxLightning)
            lightningAmount = (double) maxLightning;
        
        try {
            for (Double l = lightningAmount; l > 0; l-- ) {
                if (l == 1) {
                    breakMine(pos1Y, pos2Y, x, z, player);
                } else {
                    //Generate random number
                    Random r = new Random();
                    int newPosX = r.nextInt(pos2XMax-pos1XMin) + pos1XMin;
                    
                    //Generate new lightning:
                    Location locationNew = new Location(Bukkit.getWorld(world.getName()), newPosX, location.getBlockY(), z);
                    Bukkit.getWorld(world.getName()).strikeLightningEffect(locationNew);
                    
                    blockAmount += (breakMine(pos1Y, pos2Y, newPosX, z, player)) * 2;
                }
                blockAmount++;
            }
            giveMoney.giveMoney(player, private_mines_grabValues.currentTier(player), blockAmount, null);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }
    
    //Pickaxe Lore:
    public static boolean addThunderEnchant(Player player, int value, int oldAmountToAdd, ItemStack pickaxe) {
        if (value < maxValue) {
            try {
                ItemMeta pickMeta = pickaxe.getItemMeta();
                ArrayList<String> pickLore = (ArrayList<String>) Objects.requireNonNull(updateLore.updatePickaxeMeta(pickaxe.getItemMeta(), "Thunder", value, oldAmountToAdd));
                
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
    
    public static int breakMine(int pos1Y, int pos2Y, int x, int z, Player player) {
        int blockAmount = 0;
        for (int y = pos1Y; y > pos2Y; y--) {
            FaweAPI.getWorld(world.getName()).setBlock(x, y, z, BaseBlock.getState(0, 0));
            blockAmount++;
        }
        return blockAmount;
    }
}

package prisoncore.aDragz.Features.Sell;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.Features.Enchantments.EnchantmentList.basic.exp;
import prisoncore.aDragz.Features.Enchantments.EnchantmentList.basic.fortune;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Effects.grabPickaxeLevel;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.procEnchantManager;
import prisoncore.aDragz.Features.Sell.Multipliers.grabMultiplier;
import prisoncore.aDragz.Features.Sell.Sellall.blockPrice;
import prisoncore.aDragz.Features.Sell.Sellall.events.storeMoneyPrices;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;
import prisoncore.aDragz.Features.blockCount.Events.onBlockBreak;
import prisoncore.aDragz.data.effects.createPercentageBar;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

;

public class giveMoney implements EventListener, Listener {
    
    private final static main plugin = main.getPlugin(main.class);
    
    private static final FileConfiguration config = plugin.getConfig();
    static final World world = Bukkit.getWorld(config.getString("Type.Private_Mine.World"));
    
    static final File blocksFile = new File(plugin.getDataFolder(), "gui/blocks.yml");
    static final FileConfiguration blocksCfg = YamlConfiguration.loadConfiguration(blocksFile);
    
    static List<String> borderMaterial = (blocksCfg.getStringList("Border"));

    static final File enchantmentsFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static final FileConfiguration enchantmentsCfg = YamlConfiguration.loadConfiguration(enchantmentsFile);
    
    @EventHandler
    public static void event(BlockBreakEvent event) throws ExecutionException, InterruptedException {
        try {
            Player player = (Player) event.getPlayer();
            Material minedBlock = event.getBlock().getType(); //Some reason - it doesn't work in callable
            Block block = event.getBlock();

            //Makes it so it checks region, and if it is in a mine region, it cancels block drop and drops exp
            //So your plot doesn't drop exp and it drops blocks

            if (Objects.equals(checkRegion(player, minedBlock, block), 1)) {
                event.setDropItems(false);
                event.getPlayer().giveExp((int) ((exp.grabExpLevel(player) / 20) + 10));

                onBlockBreak.addBlockPlayer(player);
            } else if (Objects.equals(checkRegion(player, minedBlock, block), 2)) {
                //check Mined block, to see if it's the mine border (default = bedrock):
                event.setCancelled(true);
            }

            //Update Player Item Lore

            int inventorySlot = player.getInventory().getHeldItemSlot();
            ItemStack pickaxeItem = player.getInventory().getItemInMainHand();

            ItemMeta itemMeta = pickaxeItem.getItemMeta();

            List<String> itemLore = itemMeta.getLore();
            List<String> newItemLore = new ArrayList<>();

            //Grab BLOCKS String
            newItemLore.addAll(itemLore);

            if (enchantmentsCfg.getBoolean("Pickaxe.Lore-Special.Blocks-Broken.Enabled"))
                newItemLore.set(enchantmentsCfg.getInt("Pickaxe.Lore-Special.Blocks-Broken.Line"), enchantmentsCfg.getString("Pickaxe.Lore-Special.Blocks-Broken.Lore")
                        .replaceAll("BLOCKS", "%prisoncore_blocks%")
                        .replaceAll("&", "§"));

            if (enchantmentsCfg.getBoolean("Pickaxe.Lore-Special.Pickaxe-Level.Enabled"))
                newItemLore.set(enchantmentsCfg.getInt("Pickaxe.Lore-Special.Pickaxe-Level.Line"), enchantmentsCfg.getString("Pickaxe.Lore-Special.Pickaxe-Level.Lore")
                        .replaceAll("LEVEL", String.valueOf(grabPickaxeLevel.grabLevel(player)))
                        .replaceAll("PERCENTAGE", createPercentageBar.generateBar(grabPickaxeLevel.grabLevelDecimal(player)))
                        .replaceAll("&", "§"));

            itemMeta.setLore(PlaceholderAPI.setPlaceholders(player, newItemLore));

            pickaxeItem.setItemMeta(itemMeta);

            player.getInventory().setItem(inventorySlot, pickaxeItem);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }

    //0 = false
    //1 = true
    //2 = mine border
    public static int checkRegion(Player player, Material minedBlock, Block block) {
        World world = player.getWorld();
        
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        
        Location location = new Location(world, x, y, z);
        ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(
                new BukkitWorld(location.getWorld())).getApplicableRegions(
                BlockVector3.at(location.getX(), location.getY(), location.getZ()));
        
        if (regions.getRegions().toString().contains("mine-")) { //Checks if player is in a mine region.
            if (String.valueOf(borderMaterial).toUpperCase().equals(minedBlock.toString())) {
                return 2;
            }
            
            try {
                giveMoney(player, private_mines_grabValues.currentTier(player), 1, location); //Runs giveMoney(), including enchants, multiplier etc.
                //Proc enchants:
                procEnchantManager.procEnchants(player, block); //Runs all enchants
                return 1;
            } catch (Exception e) { //In case of error from region.
                Bukkit.getConsoleSender().sendMessage(e.getMessage() + " - " + e.getCause()); //Error message
                return 1;
            }
        }
        return 0;
    }
    
    public static void giveMoney(Player player, int tier, int amount, Location location) {
        try {
            //Check region name, if it equals mines then continue.
            //Grab block value
            float fortuneMultiplier = (fortune.grabFortuneLevel(player)/200)+1; //Max = 10,000. So max multiplier = 500. +1 makes it so it always adds
            float price = blockPrice.price(tier) * fortuneMultiplier; //Does fortune here, so it applies to enchants.
            
            double multiplier = grabMultiplier.grabUUIDMultiplier(player.getUniqueId()); //Player always has multiplier, min = 1
            
            price = (float) (price * multiplier) * amount; //Includes fortune times.
            main.econ.depositPlayer(player.getPlayer(), price); //Gives player money for amount price
            
            long beforeTokens = storeTokens.tokens.get(player.getUniqueId().toString());
            long afterTokens = (long) (beforeTokens + ((3L * amount) * multiplier));
            
            storeTokens.tokens.put(player.getUniqueId().toString(), afterTokens);
            
            if (Objects.isNull(storeMoneyPrices.playerSellAmount.get(player.getName()))){
                storeMoneyPrices.playerSellAmount.put(player.getName(), (0F + price)); //(0 + price) * amount - doesn't matter too much, because it's always 0 at [0]
            } else {
                storeMoneyPrices.playerSellAmount.put(player.getName(), (storeMoneyPrices.playerSellAmount.get(player.getName()) + price)); //(player)+value*amount = 110 ! 1010 [wrong brackets]
            }
            
            //if (Objects.nonNull(location))
            //    player.spawnParticle(Particle.WATER_SPLASH, location, 100);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage()); //Sends error message to console
        }
    }
}

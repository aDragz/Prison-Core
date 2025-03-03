package prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getLevel;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Effects.grabPickaxeLevel;
import prisoncore.aDragz.data.effects.createPercentageBar;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class setPickaxeLore {
    
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);

    static final File enchantmentsFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static final FileConfiguration enchantmentsCfg = YamlConfiguration.loadConfiguration(enchantmentsFile);
    
    private static String efficiencyName = cfgYaml.getString("Efficiency.Display-Name").replaceAll("&", "§");
    private static String fortuneName = cfgYaml.getString("Fortune.Display-Name").replaceAll("&", "§");
    private static String expName = cfgYaml.getString("Exp.Display-Name").replaceAll("&", "§");
    private static String money_FinderName = cfgYaml.getString("Money-Finder.Display-Name").replaceAll("&", "§");
    private static String token_FinderName = cfgYaml.getString("Token-Finder.Display-Name").replaceAll("&", "§");
    private static String tntName = cfgYaml.getString("Tnt.Display-Name").replaceAll("&", "§");
    private static String jackhammerName = cfgYaml.getString("Jackhammer.Display-Name").replaceAll("&", "§");
    private static String thunderName = cfgYaml.getString("Thunder.Display-Name").replaceAll("&", "§");
    private static String laserName = cfgYaml.getString("Laser.Display-Name").replaceAll("&", "§");
    private static String charityName = cfgYaml.getString("Charity.Display-Name").replaceAll("&", "§");
    private static String nukeName = cfgYaml.getString("Nuke.Display-Name").replaceAll("&", "§");
    
    
    public static ItemStack changeLore(Player player, ItemStack pickaxeItem, int playerInventorySlot, String[] args) {


        ItemMeta pickaxeMeta = pickaxeItem.getItemMeta();

        List<String> itemLore = cfgYaml.getStringList("Pickaxe.Lore");
        ArrayList<String> newItemLore = new ArrayList<>();

        itemLore.forEach(s -> newItemLore.add(s.replaceAll("&", "§")));

        if (enchantmentsCfg.getBoolean("Pickaxe.Lore-Special.Blocks-Broken.Enabled"))
            newItemLore.set(enchantmentsCfg.getInt("Pickaxe.Lore-Special.Blocks-Broken.Line"), enchantmentsCfg.getString("Pickaxe.Lore-Special.Blocks-Broken.Lore")
                    .replaceAll("BLOCKS", "%prisoncore_blocks%")
                    .replaceAll("&", "§"));

        if (enchantmentsCfg.getBoolean("Pickaxe.Lore-Special.Pickaxe-Level.Enabled"))
            newItemLore.set(enchantmentsCfg.getInt("Pickaxe.Lore-Special.Pickaxe-Level.Line"), enchantmentsCfg.getString("Pickaxe.Lore-Special.Pickaxe-Level.Lore")
                    .replaceAll("LEVEL", String.valueOf(grabPickaxeLevel.grabLevel(player)))
                    .replaceAll("PERCENTAGE", createPercentageBar.generateBar(grabPickaxeLevel.grabLevelDecimal(player)))
                    .replaceAll("&", "§"));

        //Create enchants:
        
        try {
            for (int i = 3; i <= args.length; ) {
                //Grab name

                if (args[i].equalsIgnoreCase("efficiency")) {
                    newItemLore.add(String.format("§f%s%s", efficiencyName, args[i += 1]));
                    //Add eff

                } else if (args[i].equalsIgnoreCase("fortune")) {
                    newItemLore.add(String.format("§f%s%s", fortuneName, args[i += 1]));
                } else if (args[i].equalsIgnoreCase("exp")) {
                    newItemLore.add(String.format("§f%s%s", expName, args[i += 1]));
                } else if (args[i].equalsIgnoreCase("money")) {
                    newItemLore.add(String.format("§f%s%s", money_FinderName, args[i += 1]));
                } else if (args[i].equalsIgnoreCase("token")) {
                    newItemLore.add(String.format("§f%s%s", token_FinderName, args[i += 1]));
                } else if (args[i].equalsIgnoreCase("tnt")) {
                    newItemLore.add(String.format("§f%s%s", tntName, args[i += 1]));
                } else if (args[i].equalsIgnoreCase("jackhammer")) {
                    newItemLore.add(String.format("§f%s%s", jackhammerName, args[i += 1]));
                } else if (args[i].equalsIgnoreCase("thunder")) {
                    newItemLore.add(String.format("§f%s%s", thunderName, args[i += 1]));
                } else if (args[i].equalsIgnoreCase("laser")) {
                    newItemLore.add(String.format("§f%s%s", laserName, args[i += 1]));
                }  else if (args[i].equalsIgnoreCase("charity")) {
                    newItemLore.add(String.format("§f%s%s", charityName, args[i += 1]));
                } else if (args[i].equalsIgnoreCase("nuke")) {
                    newItemLore.add(String.format("§f%s%s", nukeName, args[i += 1]));
                }
                
                i++; //nextNumber adds 1
            }
        } catch (IndexOutOfBoundsException ignored) {}

        pickaxeMeta.setLore(PlaceholderAPI.setPlaceholders(player, newItemLore));

        pickaxeItem.setItemMeta(pickaxeMeta);

        pickaxeItem.addUnsafeEnchantment(Enchantment.DIG_SPEED, Integer.parseInt(getLevel.grabEnchantLevel(pickaxeItem, "Efficiency")));


        player.getInventory().setItem(playerInventorySlot, pickaxeItem);

        return pickaxeItem;
    }
}
package prisoncore.aDragz.Features.Enchantments.EnchantmentManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;

public class getLevel {
    
    //First, I got to check the item
    //Grab the lore and check if it contains it
    //If not, it's 0. Else use contains()
    
    //returns level
    
    private static final main plugin = main.getPlugin(main.class);
    
    static File cfgFile = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    static FileConfiguration cfgYaml = YamlConfiguration.loadConfiguration(cfgFile);
    
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
    
    public static String grabEnchantLevel(ItemStack pickaxe, String EnchantType) {
        
        ArrayList<String> lore = new ArrayList<>();
        
        try {
            ItemMeta pickaxeMeta = pickaxe.getItemMeta();
            
            lore.addAll(pickaxeMeta.getLore());
            
            for (int i = 0; i != pickaxeMeta.getLore().size();) {
                if (pickaxeMeta.getLore().get(i).contains(EnchantType)) {
                    return pickaxeMeta.getLore().get(i)
                            .replaceAll(efficiencyName, "")
                            .replaceAll(fortuneName, "")
                            .replaceAll(expName, "")
                            .replaceAll(money_FinderName, "")
                            .replaceAll(token_FinderName, "")
                            .replaceAll(tntName, "")
                            .replaceAll(jackhammerName, "")
                            .replaceAll(thunderName, "")
                            .replaceAll(laserName, "")
                            .replaceAll(charityName, "")
                            .replaceAll(nukeName, "");
                }
                i +=1;
            }
        } catch (Exception e) {
            return "0";
        }
        return "0";
    }
}

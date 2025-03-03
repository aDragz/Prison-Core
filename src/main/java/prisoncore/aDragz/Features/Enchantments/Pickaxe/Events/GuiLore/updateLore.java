package prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class updateLore {
    
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
    
    
    
    //oldAmountToAdd = For 1, 10, 100 adds, it needs to remove old number.
    public static List<String> updatePickaxeMeta(ItemMeta pickaxeMeta, String enchantName, int newValue, int oldAmountToAdd) {
        //First thing first, grab Block data before creating block:
        ArrayList<String> pickaxeArray = new ArrayList<>();
        
        pickaxeArray.addAll(pickaxeMeta.getLore());
        
        for (int i = 0; i != pickaxeMeta.getLore().size(); ) {
            if (pickaxeArray.get(i).contains(enchantName)) {
                //String old = pickaxeArray.get(i);
                
                //String newString = old.replace( "" + (newValue - oldAmountToAdd), "" + newValue); //DO NOT USE .REPLACEALL - REPLACES COLOUR CODE
                
                if (enchantName.equalsIgnoreCase("efficiency")) {
                    
                    pickaxeArray.set(i, (efficiencyName + newValue));
                    
                } else if (enchantName.equalsIgnoreCase("fortune")) {
                    
                    pickaxeArray.set(i, (fortuneName + newValue));
                    
                } else if (enchantName.equalsIgnoreCase("exp")) {
                    
                    pickaxeArray.set(i, (expName + newValue));
                    
                } else if (enchantName.equalsIgnoreCase("money finder")) {
                    
                    pickaxeArray.set(i, (money_FinderName + newValue));
                    
                } else if (enchantName.equalsIgnoreCase("token finder")) {
                    
                    
                    pickaxeArray.set(i, (token_FinderName + newValue));
                    
                } else if (enchantName.equalsIgnoreCase("tnt")) {
                    
                    pickaxeArray.set(i, (tntName + newValue));
                    
                } else if (enchantName.equalsIgnoreCase("jackhammer")) {
                    
                    pickaxeArray.set(i, (jackhammerName + newValue));
                    
                } else if (enchantName.equalsIgnoreCase("thunder")) {
                    
                    pickaxeArray.set(i, (thunderName + newValue));
                    
                } else if (enchantName.equalsIgnoreCase("laser")) {
                    
                    pickaxeArray.set(i, (laserName + newValue));
                    
                }  else if (enchantName.equalsIgnoreCase("charity")) {
                    
                    pickaxeArray.set(i, (charityName + newValue));
                    
                } else if (enchantName.equalsIgnoreCase("nuke")) {
                    
                    pickaxeArray.set(i, (nukeName + newValue));
                    
                }
                
                return pickaxeArray;
            }
            i += 1;
        }
        
        //Like my own try-catch, if it does not return it goes here. To add it.
        
        //If none:
        if (enchantName.equalsIgnoreCase("efficiency")) {
            
            pickaxeArray.add(efficiencyName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("fortune")) {
            
            pickaxeArray.add(fortuneName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("exp")) {
            
            pickaxeArray.add(expName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("money finder")) {
            
            pickaxeArray.add(money_FinderName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("token finder")) {
            
            
            pickaxeArray.add(token_FinderName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("tnt")) {
            
            pickaxeArray.add(tntName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("jackhammer")) {
            
            pickaxeArray.add(jackhammerName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("thunder")) {
            
            pickaxeArray.add(thunderName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("laser")) {
            
            pickaxeArray.add(laserName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("charity")) {
            
            pickaxeArray.add(charityName + oldAmountToAdd);
            
            return pickaxeArray;
        } else if (enchantName.equalsIgnoreCase("nuke")) {
            
            pickaxeArray.add(nukeName + oldAmountToAdd);
            
            return pickaxeArray;
        }
        
        //Own try catch:
        //Previous will return so it will never hit this unless enchantName does not exist.
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Unknown Enchant name - " + enchantName.toUpperCase());
        return pickaxeArray;
    }
    
}
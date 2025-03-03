package prisoncore.aDragz.Features.PrivateMines;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems.BLOCKS;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems.GLOBAL;
import prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems.PAGES;
import prisoncore.aDragz.Features.PrivateMines.upgrades.grabItems.itemGrabberHandler;
import prisoncore.aDragz.data.private_mines_grabValues;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class gui {
    private static final main plugin = main.getPlugin(main.class);

    public static File file = new File(plugin.getDataFolder(), "gui/upgrades.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file); //Public for "./GenerateItems"

    public static HashMap<UUID, Inventory> map = new HashMap<UUID, Inventory>();

    //Grab Filler Block:
    static ItemStack FillerBlock = new ItemStack(itemGrabberHandler.grabItem("upgrade_filler"));
    static ItemMeta FillerMeta = FillerBlock.getItemMeta();

    //Grab Mine_Upgrade Blocks:
    static ItemStack TierPassed = new ItemStack(itemGrabberHandler.grabItem("upgrade_current"));
    static ItemStack TierCurrent = new ItemStack(itemGrabberHandler.grabItem("upgrade_unlocked"));
    static ItemStack TierLocked = new ItemStack(itemGrabberHandler.grabItem("upgrade_locked"));

    public static ItemStack FORWARD_PAGE = new ItemStack(itemGrabberHandler.grabItem("page_forward")); //Public for PAGES.java
    public static ItemStack PREVIOUS_PAGE = new ItemStack(itemGrabberHandler.grabItem("page_backward")); //Public for PAGES.java

    static String MINETP_STRING = itemGrabberHandler.grabDisplayName("minetp");
    static ItemStack MINETP = new ItemStack(itemGrabberHandler.grabItem("minetp"));

    static int InventorySize = cfg.getInt("GUI.Size");

    public static void createInventory(Player player, String[] args) {
        try {
            int pageNumber;
            if (args.length != 0) {
                try {
                    pageNumber = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    pageNumber = 1;
                }
            } else {
                pageNumber = 1;
            }
            player.openInventory(generateInventory((player), pageNumber));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Inventory generateInventory(Player player, int pageNumber) throws ExecutionException, InterruptedException {
        
        Inventory inv = Bukkit.getServer().createInventory(null, InventorySize, ChatColor.GREEN + "" + ChatColor.BOLD + String.format(
                "Mine Management | %d", pageNumber));
        
        map.put(player.getUniqueId(), inv);
        
        //Create Filler, Do it out of for statement, so it doesn't waste time.
        FillerMeta.setDisplayName(" ");
        FillerBlock.setItemMeta(FillerMeta);
        
        //First thing to do is to grab the current mine tier
        String gangName = gangCheckInfo.grabGangName(player); //Usually puts in gangFile, but used later on in cases. As of now, used for stats. Hence, why it's asked now. To save resources for later.
        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangName));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);
        
        int currentTier = private_mines_grabValues.currentTier(player); //Grab current tier from player
        
        //Now is to go through the list and create all  slots before adding ones with no items:
        
        //grab size of inventory
        
        ArrayList<Integer> itemSlots = new ArrayList<>();
        int maxSize = cfg.getInt("GUI.Size");
        
        for (int i = 0; i < maxSize; ) {
            if (cfg.contains(String.format("GUI.%d.%d", pageNumber, i))) {
                itemSlots.add(i);
                String type = (cfg.getString(String.format("GUI.%d.%d.Type", pageNumber, i)));
                
                switch (type) {
                    case ("MINE_UPGRADE"):
                        //First thing first, grab Block data before creating block:
                        
                        int tier = (cfg.getInt(String.format("GUI.%d.%d.Tier", pageNumber, i)));
                        String display_name = (cfg.getString(String.format("GUI.%d.%d.Display_Name", pageNumber, i)));
                        
                        //Create the itemStack so it can get the material
                        ItemStack item;
                        
                        if (currentTier < tier) {
                            item = new ItemStack(TierLocked);
                        } else if (currentTier == tier) {
                            item = new ItemStack(TierCurrent);
                        } else {
                            item = new ItemStack(TierPassed);
                        }
                        
                        //Create and set the meta of the item
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(display_name.replaceAll("&", "§"));
                        
                        //Next is to get the Lore of the item.
                        ArrayList<String> lore = new ArrayList<>();
                        ArrayList<String> newLore = new ArrayList<>();
                        lore.addAll(cfg.getStringList(String.format("GUI.%d.%d.Lore", pageNumber, i)));
                        
                        String block = String.valueOf(cfg.get(String.format("%d.Block", tier)))
                                .replaceAll("_", " ");
                        
                        for (String newLoreS : lore) {
                            newLoreS = newLoreS.replaceAll("&", "§").replaceAll("TIER", String.valueOf(tier))
                                    .replaceAll("BLOCK", block);
                            
                            newLore.add(newLoreS);
                        }
                        
                        meta.setLore(newLore);
                        
                        item.setItemMeta(meta);
                        inv.setItem(i, item);
                        
                        break;
                    case ("STATS"):
                        //First, make array to create the lore, and necessary variables for the items
                        //catch if it cannot detect the block as a material:
                        Material configBlock = Material.matchMaterial(Objects.requireNonNull(cfg.getString(String.format("GUI.%d.%d.Material", pageNumber, i))));
                        
                        ItemStack statsItem = new ItemStack(configBlock);
                        
                        ItemMeta statsMeta = statsItem.getItemMeta();
                        ArrayList<String> statsLore = new ArrayList<>();

                            /*Generate Lore and the statistics:
                            Statistics first:*/

                            /*We already have the:
                            player name [player]
                            Current Mine Tier
                            Gang name
                             */
                        double playerBalance = main.econ.getBalance(player); //Amount of money the player has.
                        ArrayList<String> UUIDgangMembers = new ArrayList<>();
                        ArrayList<String> IGN = new ArrayList<>();
                        
                        //Add Gang Members
                        ArrayList<String> formattedGangMembers = new ArrayList<>();
                        UUIDgangMembers.addAll(gangCfg.getStringList("Members"));
                        
                        if (UUIDgangMembers.size() == 0) {
                            IGN.add(0, "No Members!");
                        } else {
                            IGN.addAll(gangCheckInfo.IDtoIGN(UUIDgangMembers));
                        }
                        IGN.forEach(s->formattedGangMembers.add(String.format("§c%s", s))); //Not the most efficient as of now, but it will do for now.
                        
                        //Add to stats lore
                        statsLore.add(String.format("§eName: §c%s", player.getName()));
                        statsLore.add(String.format(Locale.ENGLISH, "§eBalance: §f$§a%1$,.2f", playerBalance));
                        statsLore.add(String.format("§eCurrent Block: §c%s", BLOCKS.returnBlock(player)));
                        statsLore.add(""); //Spacer
                        statsLore.add(String.format("§eGang Name: §c%s", gangName));
                        statsLore.add(String.format("§eCurrent Tier: §c%d", currentTier));
                        statsLore.add(""); //Spacer
                        statsLore.add("§eMembers:");
                        statsLore.addAll(formattedGangMembers);
                        statsLore.add("");
                        statsLore.add(String.format("§ePage Number: §c%d", pageNumber));
                        
                        String displayName = cfg.getString(String.format("GUI.%d.%d.Display_Name", pageNumber, i));
                        statsMeta.setDisplayName(displayName.replaceAll("&", "§"));
                        statsMeta.setLore(statsLore);
                        statsItem.setItemMeta(statsMeta);
                        
                        inv.setItem(i, statsItem);
                        break;
                    case ("EFFECTS"):
                        Material effectsConfigBlock = Material.matchMaterial(Objects.requireNonNull(cfg.getString(String.format("GUI.Effects.Material", pageNumber, i))));
                        
                        ItemStack effectsItem = new ItemStack(effectsConfigBlock);
                        
                        ItemMeta effectsMeta = effectsItem.getItemMeta();
                        ArrayList<String> effectsLore = new ArrayList<>();
                        ArrayList<String> effectsLoreNew = new ArrayList<>();
                        
                        
                        ArrayList<String> effectsLoreConfList = new ArrayList<>();
                        effectsLoreConfList.addAll(cfg.getStringList(String.format("GUI.Effects.Lore", pageNumber, i)));
                        
                        effectsLore.addAll(effectsLoreConfList);
                        
                        for (String newLoreS : effectsLoreConfList) {
                            newLoreS = newLoreS.replaceAll("&", "§");
                            effectsLoreNew.add(newLoreS);
                        }
                        
                        String effectsDisplayName = cfg.getString(String.format("GUI.Effects.Display_Name", pageNumber, i));
                        effectsMeta.setDisplayName(effectsDisplayName.replaceAll("&", "§"));
                        effectsMeta.setLore(effectsLoreNew);
                        effectsItem.setItemMeta(effectsMeta);
                        
                        inv.setItem(i, effectsItem);
                        break;
                    case ("UPGRADE"):
                        
                        inv.setItem(i, GLOBAL.generateItem("Upgrade_Mine", pageNumber, player));
                        break;
                    case ("MINETP"):
                        inv.setItem(i, GLOBAL.generateItem("MineTP", pageNumber, player));
                        break;
                    case "FORWARD_PAGE":
                        inv.setItem(i, PAGES.page_forward());
                        break;
                    case "BACKWARDS_PAGE":
                        inv.setItem(i, PAGES.page_backward());
                        break;
                    default:
                        break;
                }
                
            } else {
                inv.setItem(i, FillerBlock);
            }
            i++;
        }
        return inv;
    }
}
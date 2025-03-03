package prisoncore.aDragz.Features.Enchantments.Pickaxe.GuiManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Enchantments.EnchantmentList.advanced.*;
import prisoncore.aDragz.Features.Enchantments.EnchantmentList.basic.*;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getLevel;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getPrice;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.GuiLore.updateGUI;
import prisoncore.aDragz.Features.Tokens.Actions.enchantSpend;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.EventListener;
import java.util.Objects;

public class clickManager implements EventListener, Listener {
    private static final main plugin = main.getPlugin(main.class);

    public static File enchFile = new File(plugin.getDataFolder(), "gui/enchantmentGUI.yml");
    public static FileConfiguration enchCfg = YamlConfiguration.loadConfiguration(enchFile); //Public for "./GenerateItems"

    @EventHandler
    public static void clickEnchantInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        //grab inventory name


        if (player.getOpenInventory().getTitle().contains(enchantmentGUI.inventoryNameBase.replaceAll("PAGENUMBER", ""))) {
            try {
                event.setCancelled(true);

                //grab clicked enchant name:
                int slot = event.getSlot();

                String enchantName = Objects.requireNonNull(enchCfg.getString(String.format("GUI.1.%s.Enchantment", slot)));

                /*
                grab click type (numbers)
                left = 1
                right = 10
                shift+left = 100
                */

                int enchantAmountToAdd = 0; //So I can add multiple with the left, right & shift clicks.
                int enchantmentLevel = Integer.parseInt(getLevel.grabEnchantLevel(player.getPlayer().getInventory().getItem(0), enchantName));
                
                long totalPrice = 0L;

                if (event.getClick().isRightClick()) { // Add to enchant level: 10
                    //grab current enchant level
                    int topLevel = enchantmentLevel + 10;
                    
                    for (int i = enchantmentLevel; topLevel != i; ) {
                        totalPrice += getPrice.grabPrice((long) i, enchantName);
                        i++;
                    }

                    enchantAmountToAdd = 10;
                } else if (event.getClick().isShiftClick()) {
                    //grab current enchant level

                    int topLevel = enchantmentLevel + 100;

                    for (int i = enchantmentLevel; topLevel != i; ) {
                        totalPrice += getPrice.grabPrice((long) i, enchantName);
                        i++;
                    }

                    enchantAmountToAdd = 100;
                } else if (event.getClick().isLeftClick()) { // Add to enchant level: 1
                    enchantAmountToAdd = 1;
                    totalPrice = getPrice.grabPrice((long) enchantmentLevel, enchantName);
                }
                
                if (!enchantSpend.spendTokens(storeTokens.tokens.get(player.getUniqueId().toString()), totalPrice, player)) {
                    //Cannot afford it
                    player.sendMessage(grabMessagesValue.type("token", "cannot_afford")
                            .replaceAll("&", "§")
                            .replaceAll("PRICE", String.valueOf(totalPrice)));
                    return;
                }

                int newEnchantLevel = enchantmentLevel + enchantAmountToAdd;

                if (enchantName.equals("Efficiency")) {
                        if (efficiency.addEffEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                            // If it adds the enchant, update gui.
                            updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                        }
                } else if (enchantName.equals("Money Finder")) {
                    if (moneyFinder.addMoneyFinderEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        // If it adds the enchant, update gui.
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                } else if (enchantName.equals("Token Finder")) {
                    if (tokenFinder.addTokenFinderEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        // If it adds the enchant, update gui.
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                } else if (enchantName.equals("Fortune")) {
                    if (fortune.addFortuneEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                } else if (enchantName.equals("Exp")) {
                    if (exp.addExpEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                } else if (enchantName.equals("Tnt")) {
                    if (tnt.addTntEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                } else if (enchantName.equals("Jackhammer")) {
                    if (jackhammer.addJackhammerEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                } else if (enchantName.equals("Thunder")) {
                    if (thunder.addThunderEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                } else if (enchantName.equals("Laser")) {
                    if (laser.addLaserEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                } else if (enchantName.equals("Charity")) {
                    if (charity.addCharityEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                } else if (enchantName.equals("Nuke")) {
                    if (nuke.addEnchant(player, newEnchantLevel, enchantAmountToAdd, player.getInventory().getItem(0))) {
                        updateGUI.updateUserInferface(player, event.getSlot(), 1, event.getCurrentItem(), player.getInventory().getItem(0), enchantName);
                    }
                }
            } catch (Exception e) {
                return;
            }
        }
    }
}

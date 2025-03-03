package prisoncore.aDragz.Features.Sell.Multipliers.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Sell.Multipliers.data.savePermMultipliers;
import prisoncore.aDragz.Features.Sell.Multipliers.data.storePermMultipliers;
import prisoncore.aDragz.Features.RankupStandard.Data.saveRanks;

import java.io.IOException;
import java.text.DecimalFormat;

public class multiCommands implements CommandExecutor {
    
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    
    @Override
    public boolean onCommand(CommandSender player, Command command, String label, String[] args) {
        
        if (command.getName().equalsIgnoreCase("multiplier")) {
            if (args.length == 0) {
                if (!player.hasPermission("Prisoncore.Multiplier")) {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Multiplier"));
                    return false;
                }
                try {
                    Player idplayer = (Player) player;
                    player.sendMessage(grabMessagesValue.type("multiplier", "current").replaceAll("&", "§")
                            .replaceAll("MULTIPLIER", String.valueOf(decimalFormat.format(storePermMultipliers.permMultipliers.get(idplayer.getUniqueId().toString())))));
                } catch (NullPointerException e) {
                    player.sendMessage(grabMessagesValue.type("multiplier", "current").replaceAll("&", "§")
                            .replaceAll("MULTIPLIER", "0"));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (!player.hasPermission("Prisoncore.Multiplier.Give")) {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Multiplier.Give"));
                    return false;
                }
                
                try {
                    giveMultiplier.playerGiveMultiplier((Player) player, args, false);
                } catch (IOException e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + e.getMessage());
                }
            } else if (args[0].equalsIgnoreCase("set")) {
                if (!player.hasPermission("Prisoncore.Multiplier.Set")) {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Multiplier.Set"));
                    return false;
                }
                
                try {
                    giveMultiplier.playerGiveMultiplier((Player) player, args, true);
                } catch (IOException e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + e.getMessage());
                }
            } else if (args[0].equalsIgnoreCase("save")) {
                if (!player.hasPermission("Prisoncore.Multiplier.Save")) {
                    player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Multiplier.Save"));
                    return false;
                }
                
                try {
                    player.sendMessage(ChatColor.GREEN + "Saving permanent Multipliers");
                    if (savePermMultipliers.autoSavePermMultiplier()) {
                        player.sendMessage(ChatColor.GREEN + "Saved permanent Multipliers");
                    } else {
                        player.sendMessage(ChatColor.RED + "There are no active permanent Multipliers.");
                    }
                } catch (IOException Ignored) {}
                
                try {
                    player.sendMessage(ChatColor.GREEN + "Saving Ranks");
                    if (saveRanks.autoSaveRanks()) {
                        player.sendMessage(ChatColor.GREEN + "Saved Ranks");
                    } else {
                        player.sendMessage(ChatColor.RED + "Not saved Ranks.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
}

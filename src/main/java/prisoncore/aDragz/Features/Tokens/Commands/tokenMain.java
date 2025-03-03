package prisoncore.aDragz.Features.Tokens.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Logs.LogManagement.Tokens.saveTokenTransactions;
import prisoncore.aDragz.Features.Tokens.data.Tokens.saveTokens;

public class tokenMain implements CommandExecutor {
    @Override
    public boolean onCommand (CommandSender player, Command command, String label, String[] args) {
        
        if (command.getName().equalsIgnoreCase("token")) {
            if (args.length == 0) {
                tokenCheckValue.tokenCheck((Player) player);
            } else if (args[0].equalsIgnoreCase("reset")) {
                if (player.hasPermission("PrisonCore.Tokens.reset")) {
                    tokenReset.resetToken((Player) player, Bukkit.getOfflinePlayer(args[1].toString()));
                }
            } else if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("add")) {
                if (player.hasPermission("PrisonCore.Tokens.give")) {
                    tokenGive.giveToken(player, Bukkit.getOfflinePlayer(args[1].toString()), args); //token give {name}
                }
            } else if (args[0].equalsIgnoreCase("pay")) {
                tokenPay.payToken(player, Bukkit.getOfflinePlayer(args[1].toString()), args); //token give {name}
            } else if (args[0].equalsIgnoreCase("save")) {
                if (player.hasPermission("PrisonCore.Tokens.save")) {
                    try {
                        saveTokens.autoSaveTokens();
                        saveTokenTransactions.autoSaveTransactions();
                    } catch (Exception e) {
                    }
                }
            } else {
                tokenCheckValue.tokenCheckAdmin((Player) player, args);
            }
            return false;
        }
        return false;
    }
}

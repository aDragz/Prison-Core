package prisoncore.aDragz.Features.Logs.CommandHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Logs.Features.Tokens.logsTokenDisplay;
import prisoncore.aDragz.Features.Logs.Features.Tokens.logsTokenResetFile;

import java.io.IOException;

public class commandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender player, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("transactions")) {
            if (player.hasPermission("PrisonCore.Transactions")) {
                if (args.length <= 1) {
                    player.sendMessage(grabMessagesValue.type("transactions", "bad_usage").replaceAll("&", "§"));
                    return false;
                }
                //Grab args
                
                //args: transactions[-1] type[0] to-do[1] player[2]
                //example: transactions tokens check aDragz
                String featureType = args[0];
                String todo = args[1];
                
                switch (featureType.toLowerCase()) {
                    case ("tokens"):
                        if (todo.equalsIgnoreCase("reset")) {
                            try {
                                logsTokenResetFile.resetTokensTransactions();
                            } catch (IOException e) {
                                Bukkit.getConsoleSender().sendMessage(e.getMessage());
                            }
                            break;
                        }
                        
                        //Check tokens:
                        
                        if (args.length != 2) {
                            player.sendMessage(grabMessagesValue.type("transactions", "no_ign").replaceAll("&", "§"));
                        }
                        logsTokenDisplay.checkLogs(player, Bukkit.getOfflinePlayer(args[1].toString()));
                        break;
                    default:
                        player.sendMessage(grabMessagesValue.type("transactions", "unknown_feature").replaceAll("&", "§"));
                }
            } else {
                player.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Transactions"));
            }
        }
        return false;
    }
}

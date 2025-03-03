package prisoncore.aDragz.Features.Gangs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Gangs.management.gangChat.chatCommands;
import prisoncore.aDragz.Features.Gangs.management.*;
import prisoncore.aDragz.data.private_mines_grabValues;

import java.io.IOException;

public class gangCommands implements CommandExecutor, Listener {
    
    static String message;
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender == Bukkit.getConsoleSender()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "You need to be in-game to run this command");
            return false;
        }
        
        Player player = (Player) sender;
        
        if (command.getName().equalsIgnoreCase("gang")) {
            if (args.length != 0) {
                switch (args[0].toLowerCase()) {
                    case "help":
                        gangHelp.helpMenu(player);
                        return true;
                    case "create":
                        try {
                            gangCreate.createGang(player, args);
                            return true;
                        } catch (IOException e) {
                            Bukkit.getConsoleSender().sendMessage(e.getMessage());
                        }
                    case "invites":
                        gangCheckInfo.showPlayerInvites(player);
                        return true;
                    case "join":
                        gangJoin.gangJoin(player, args);
                        return true;
                }
                
                //Check if you are in gang.
                //If you're not, stop code.
                //Else pass through to invite etc etc.
                
                if (!gangCheckInfo.checkGangStatus(player)) {
                    player.sendMessage(grabMessagesValue.type("gang", "gang_not_found").replaceAll("&", "§"));
                    return true;
                }
                
                switch (args[0].toLowerCase()) {
                    case "invite":
                        try {
                            gangInvite.gangInvite(player, args);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "disband":
                        try {
                            gangDisband.disband(player);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "kick":
                        try {
                            gangKick.kick(player, args);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "chat":
                        try {
                            chatCommands.commands(args, private_mines_grabValues.grabGangID(player).longValue(), player);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                return true;
            }
            
            if (!gangCheckInfo.checkGangStatus(player)) {
                message = grabMessagesValue.type("gang", "gang_not_found").replaceAll("&", "§");
                player.sendMessage(message);
                return true;
            }
            
            gangHelp.helpMenu(player);
            //gangCheckInfo.checkStats(player);
        }
        return false;
    }
}

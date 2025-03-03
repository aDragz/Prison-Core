package prisoncore.aDragz.Features.Tokens.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.Logs.Features.Tokens.logsTokenGive;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;

import java.text.DecimalFormat;

public class tokenPay {
    
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###,###,###,###");
    
    
    public static boolean payToken(CommandSender sender, OfflinePlayer player, String[] args) throws NullPointerException {
        
        //Grab Name of Player
        
        //Tokens pay [Player] [Amount]
        OfflinePlayer receiver = Bukkit.getOfflinePlayer(args[1]);;
        
        long beforeTokens = 0L;
        long afterTokens = 0L;
        long receiverBeforeTokens = 0L;
        long receiverAfterTokens = 0L;
        
        if (!receiver.isOnline()) {
            sender.sendMessage(grabMessagesValue.type("global", "unknown_player").replaceAll("&", "§").replaceAll("PLAYER", receiver.getName().toString()));
            return true;
        }
        
        /*try {
            receiver.isOnline(); //Returns NullPointerException if false;
        } catch (NullPointerException e) {
            sender.sendMessage(grabMessagesValue.type("global", "unknown_player").replaceAll("&", "§").replaceAll("PLAYER", receiver.getName().toString()));
            
        }*/
        
        try {
            beforeTokens = storeTokens.tokens.get(Bukkit.getPlayer(sender.getName()).getUniqueId().toString());
            receiverBeforeTokens = storeTokens.tokens.get((receiver.getUniqueId().toString()));
            
            afterTokens = (beforeTokens - Long.valueOf(args[2]));
            receiverAfterTokens = (receiverBeforeTokens + Long.valueOf(args[2]));
            
            if (afterTokens <=-1) {
                sender.sendMessage(grabMessagesValue.type("token", "cannot_afford").replaceAll("&", "§").replaceAll("PRICE", String.valueOf(afterTokens).replaceAll("-", "")));
                return false;
            }
            
        } catch (Exception e) {
            sender.sendMessage(grabMessagesValue.type("token", "cannot_afford").replaceAll("&", "§").replaceAll("PRICE", String.valueOf(afterTokens).replaceAll("-", "")));
            return false;
        }
        try {
            
            
            storeTokens.tokens.put(receiver.getUniqueId().toString(), receiverAfterTokens);
            logsTokenGive.transactionTokenGive(sender.getName().toString(), player, receiverBeforeTokens, receiverAfterTokens);
            
            storeTokens.tokens.put(Bukkit.getPlayer(sender.getName()).getUniqueId().toString(), afterTokens);
            logsTokenGive.transactionTokenGive(sender.getName().toString(), player, beforeTokens, afterTokens);
            
            sender.sendMessage(grabMessagesValue.type("token", "paidSender").replaceAll("&", "§").replaceAll("AMOUNT", decimalFormat.format(Long.valueOf(args[2]))).replaceAll("PLAYER", args[1]));
            receiver.getPlayer().sendMessage((grabMessagesValue.type("token", "paidReceiver").replaceAll("&", "§").replaceAll("PLAYER", sender.getName()).replaceAll("AMOUNT", decimalFormat.format(Long.valueOf(args[2])))));
        } catch (Exception e) {
            sender.sendMessage(ChatColor.DARK_RED + "An error has occurred! Please message an Administrator!");
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
        return false;
    }
}

package prisoncore.aDragz.Features.blockCount.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.Features.blockCount.Data.saveBlocks;
import prisoncore.aDragz.Features.blockCount.Data.storeBlocks;

import java.io.IOException;

public class block implements CommandExecutor {
    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("blocks")) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("save")) {
                    try {
                        saveBlocks.autoSaveTokens();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                
                return true;
            }
            
            Player player = (Player) sender;
            player.sendMessage(grabMessagesValue.type("mine", "blocks").replaceAll("&", "§").replaceAll("BLOCKS", storeBlocks.blocks.get(player.getUniqueId().toString()).toString()));
        }
        return false;
    }
}

package prisoncore.aDragz.Features.Pve.Mobs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.Features.Pve.Events.Mobs.despawnMobs;

public class commands implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("spawnmobs")) {
            if (args[0].equalsIgnoreCase("d")) {
                despawnMobs.despawnMobs();
                return true;
                
            }

            
        }
        return true;
    }
}


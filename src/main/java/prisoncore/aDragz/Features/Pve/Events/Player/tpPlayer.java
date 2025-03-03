package prisoncore.aDragz.Features.Pve.Events.Player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Admin.Features.PVE.Commands.mobsReset;

public class tpPlayer implements CommandExecutor {
    
    public static void tpPlayer(Player player) {
        
        Location tpArea = new Location(Bukkit.getWorld("pve"), 113, 240, 178);
        
        player.getPlayer().teleport(tpArea);
        
        //Check if it should reset enemies.
        if (mobsReset.pendingReset) {
            mobsReset.resetMobs(1);
        }
    }
    
    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("pve")) {
            tpPlayer((Player) sender);
            
            sender.sendMessage("§7§l--------------§c§lPVE§7§l---------------");
            sender.sendMessage("");
            sender.sendMessage("            §6§lYou warped to §c§lPVE!          ");
            sender.sendMessage("");
            sender.sendMessage("§7§l--------------------------------");
        }
        return true;
    }
}

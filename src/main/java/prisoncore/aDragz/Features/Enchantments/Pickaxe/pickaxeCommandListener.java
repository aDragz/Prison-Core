package prisoncore.aDragz.Features.Enchantments.Pickaxe;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Admin.Features.Pickaxe.Commands.pickaxeGive;

public class pickaxeCommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        Player player = (Player) sender;

        if (player.isOnline()) {
            if (command.getName().equalsIgnoreCase("pickaxegive")) {
                if (player.hasPermission("PrisonCore.Admin.Pickaxe.Give")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, SoundCategory.MASTER, 100, -10);

                    pickaxeGive.givePickaxe(target.getPlayer(), args);
                }
            }
            return false;
        }
        return false;
    }
}

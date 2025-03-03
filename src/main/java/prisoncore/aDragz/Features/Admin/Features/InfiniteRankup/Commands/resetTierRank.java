package prisoncore.aDragz.Features.Admin.Features.InfiniteRankup.Commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.Features.RankupInfinite.Data.Storage.storeRanksInfinite;

public class resetTierRank {
    public static void resetRank(CommandSender sender, OfflinePlayer player) {
        storeRanksInfinite.ranks.put(player.getUniqueId().toString(), 1L);
        sender.sendMessage(ChatColor.GREEN + String.format("You have reset %s's rank!", player.getName()));
    }
}

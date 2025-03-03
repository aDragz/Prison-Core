package prisoncore.aDragz.Features.Admin.Features.InfiniteRankup.Commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.Features.RankupInfinite.Data.Storage.storeRanksInfinite;

public class setTierRank {
    public static void setRank(CommandSender sender, OfflinePlayer player, Long tier) {
        storeRanksInfinite.ranks.put(player.getUniqueId().toString(), tier);
        sender.sendMessage(ChatColor.GREEN + String.format("You have set %s's rank!", player.getName()));
    }
}

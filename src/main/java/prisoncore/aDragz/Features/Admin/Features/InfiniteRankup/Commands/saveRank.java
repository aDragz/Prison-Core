package prisoncore.aDragz.Features.Admin.Features.InfiniteRankup.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import prisoncore.aDragz.Features.RankupInfinite.Data.Storage.saveRanksInfinite;

import java.io.IOException;

public class saveRank {
    public static void saveRankup(CommandSender player) throws IOException {
        saveRanksInfinite.autoSaveRanks();
        player.sendMessage(ChatColor.GREEN + "Ranks saved!");
    }
}

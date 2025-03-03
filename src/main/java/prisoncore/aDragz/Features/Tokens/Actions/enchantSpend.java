package prisoncore.aDragz.Features.Tokens.Actions;

import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;

public class enchantSpend {

    public static boolean spendTokens(Long totalTokens, Long tokensNeeded, Player player) {
        if (totalTokens >= tokensNeeded) {
            Long newTokens = totalTokens - tokensNeeded;
            storeTokens.tokens.put(player.getUniqueId().toString(), newTokens);
            return true;
        }
        return false;
    }
}

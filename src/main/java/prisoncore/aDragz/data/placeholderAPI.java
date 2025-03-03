package prisoncore.aDragz.data;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;
import prisoncore.aDragz.Features.PrivateMines.conditions.cooldown;
import prisoncore.aDragz.Features.RankupInfinite.Data.grabRankTier;
import prisoncore.aDragz.Features.RankupInfinite.Data.Storage.storeRanksInfinite;
import prisoncore.aDragz.Features.RankupStandard.Data.storeRanks;
import prisoncore.aDragz.Features.Sell.Multipliers.grabMultiplier;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;
import prisoncore.aDragz.Features.blockCount.Data.storeBlocks;
import prisoncore.aDragz.data.format_numbers.tokens;

import java.text.DecimalFormat;
import java.util.Objects;

public class placeholderAPI extends PlaceholderExpansion {
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###,###,###,###");
    
    public String getIdentifier() {
        return "prisoncore";
    }

    /*
     The author of the Placeholder
     This cannot be null
     */
    public String getAuthor() {
        return "aDragz";
    }

    /*
     Same with #getAuthor() but for versioon
     This cannot be null
     */

    public String getVersion() {
        return "1.0.0";
    }

    /*
    Use this method to setup placeholders
    This is somewhat similar to EZPlaceholderhook
     */
    public String onRequest(OfflinePlayer player, String identifier) {

        //Blocks:
        if (identifier.equalsIgnoreCase("blocks")) {
            return tokens.format(storeBlocks.blocks.get(player.getUniqueId().toString()));
        }
        
        
        //Tokens:

        if (identifier.equalsIgnoreCase("tokens")) {
            return tokens.format(storeTokens.tokens.get(player.getUniqueId().toString()));
            //return decimalFormat.format(storeTokens.tokens.get(player.getUniqueId().toString()));
        }
        
        if (identifier.equalsIgnoreCase("multi")) {
            return String.valueOf(grabMultiplier.grabUUIDMultiplier(player.getUniqueId()));
        }

        if (identifier.equalsIgnoreCase("mine_tier")) {
            return String.valueOf(private_mines_grabValues.currentTier((Player) player));
        }
        
        if (identifier.equalsIgnoreCase("mine_reset_seconds")) {
            try {
                Long seconds = (((cooldown.cooldownMap.get(player.getName()) / 1000) + cooldown.cooldownLength) - (System.currentTimeMillis() / 1000));
                if (seconds >= 1) {
                    return (ChatColor.RED + String.valueOf(seconds) + " Seconds");
                }
                cooldown.cooldownMap.remove(player.getName());
                return ChatColor.GREEN + "None";
            } catch (Exception e) {
                return ChatColor.GREEN + "None";
            }
        }
        
        if (identifier.equalsIgnoreCase("rank")) {
            return storeRanks.ranks.get(player.getUniqueId().toString());
        }
        
        if (identifier.equalsIgnoreCase("rank_tier")) {
            return grabRankTier.grabDoublePlayerRank(storeRanksInfinite.ranks.get(player.getUniqueId().toString()));
        }
        
        if (identifier.equalsIgnoreCase("ranktier")) {
            return storeRanksInfinite.ranks.get(player.getUniqueId().toString()).toString();
        }

        if (identifier.equalsIgnoreCase("gang_name")) {
            return Objects.requireNonNull(gangCheckInfo.grabGangName((Player) player));
        }

        if (identifier.equalsIgnoreCase("gang_leader")) {
            String playerName = gangCheckInfo.grabLeaderIGN(player);
            try {
                if (Bukkit.getOfflinePlayer(playerName).getPlayer().isOnline()) {
                    return (ChatColor.GREEN + playerName.toString());
                }
                return (ChatColor.RED + playerName.toString());
            } catch (Exception e) {
                return (ChatColor.RED + playerName.toString());
            }
        }

        if (identifier.equalsIgnoreCase("gang_member1")) {
            String playerName = gangCheckInfo.grabMembersIGN(player, 0);
            try {
                if (Bukkit.getOfflinePlayer(playerName).getPlayer().isOnline()) {
                    return (ChatColor.GREEN + playerName.toString());
                }
                return (ChatColor.RED + playerName.toString());
            } catch (Exception e) {
                return (ChatColor.RED + playerName.toString());
            }
        }

        if (identifier.equalsIgnoreCase("gang_member2")) {
            String playerName = gangCheckInfo.grabMembersIGN(player, 1);

            try {
                if (Bukkit.getOfflinePlayer(playerName).getPlayer().isOnline()) {
                    return (ChatColor.GREEN + playerName.toString());
                }
                return (ChatColor.RED + playerName.toString());
            } catch (Exception e) {
                return (ChatColor.RED + playerName.toString());
            }


        }

        if (identifier.equalsIgnoreCase("gang_member3")) {
            String playerName = gangCheckInfo.grabMembersIGN(player, 2);

            try {
                if (Bukkit.getOfflinePlayer(playerName).getPlayer().isOnline()) {
                    return (ChatColor.GREEN + playerName.toString());
                }
                return (ChatColor.RED + playerName.toString());
            } catch (Exception e) {
                return (ChatColor.RED + playerName.toString());
            }
        }

        if (identifier.equalsIgnoreCase("gang_member4")) {
            String playerName = gangCheckInfo.grabMembersIGN(player, 3);
            try {
                if (Bukkit.getOfflinePlayer(playerName).getPlayer().isOnline()) {
                    return (ChatColor.GREEN + playerName.toString());
                }
                return (ChatColor.RED + playerName.toString());
            } catch (Exception e) {
                return (ChatColor.RED + playerName.toString());
            }
        }

        if (identifier.equalsIgnoreCase("gang_member5")) {
            String playerName = gangCheckInfo.grabMembersIGN(player, 4);
            try {
                if (Bukkit.getOfflinePlayer(playerName).getPlayer().isOnline()) {
                    return (ChatColor.GREEN + playerName.toString());
                }
                return (ChatColor.RED + playerName.toString());
            } catch (Exception e) {
                return (ChatColor.RED + playerName.toString());
            }
        }
        return "";
    }
}

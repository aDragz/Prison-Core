package prisoncore.aDragz.Features.RankupInfinite.Data;

import java.text.NumberFormat;

public class grabRankTier {
    public static Integer grabPlayerRank(Long rankNumber) {
        Double root = Math.sqrt(rankNumber);
        
        NumberFormat round = NumberFormat.getInstance();
        round.setMaximumFractionDigits(2);
        round.setMinimumFractionDigits(2);
        String rounded = round.format(root);
        
        return Integer.valueOf((rounded.replaceAll(".00", "")));
    }
    
    public static String grabDoublePlayerRank(Long rankNumber) {
        Double root = Math.sqrt(rankNumber);
        
        NumberFormat round = NumberFormat.getInstance();
        round.setMaximumFractionDigits(2);
        round.setMinimumFractionDigits(2);
        String rounded = round.format(root);
        
        return rounded;
    }
}

package prisoncore.aDragz.Features.Sell.Multipliers;

import prisoncore.aDragz.Features.Sell.Multipliers.data.storePermMultipliers;

import java.util.UUID;

public class grabMultiplier {

    public static float grabUUIDMultiplier(UUID ID) {
        return storePermMultipliers.permMultipliers.get(ID.toString());
    }
}

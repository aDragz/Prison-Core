package prisoncore.aDragz.Features.Sell.Sellall;

public class blockPrice {
    //new block price
    //new formula:
    
    /*
    instead of per block, it upgrades with each tier.
    
    Formula: ((tier*tier)/10)+1
     */
    
    public static int price(int tier) {
        return ((tier*tier)/10)+5;
    }
}
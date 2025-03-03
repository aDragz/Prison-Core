package prisoncore.aDragz.Features.Pve.Crates.Ender_Chest.SpawnChests;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

public class generateEnderLocations {
    //Hard coded points:
    public static ArrayList<Location> locations = new ArrayList<>();
    
    public static void generateLocations() {
        locations.add(0, new Location(Bukkit.getWorld("pve"), 107, 222, 190));
    }
}

package prisoncore.aDragz.Features.Pve.Events.Mobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

public class onStartUp {
    
    //Hard coded points:
    public static ArrayList<Location> locations = new ArrayList<>();
    public static ArrayList<String> type = new ArrayList<>();
    
    public static void generateLocations() {
        locations.add(0, new Location(Bukkit.getWorld("pve"), 113, 238, 185));
        type.add(0, "Leather_Zombie");
        locations.add(1, new Location(Bukkit.getWorld("pve"), 112, 238, 185));
        type.add(1, "Leather_Zombie");
        locations.add(2, new Location(Bukkit.getWorld("pve"), 111, 235, 188));
        type.add(2, "Basic_Zombie");
        locations.add(3, new Location(Bukkit.getWorld("pve"), 105, 231, 194)); //Boss_entrance
        type.add(3, "Leather_Zombie"); //Charged_Creeper
        locations.add(4, new Location(Bukkit.getWorld("pve"), 102, 228, 194)); //Boss_inside
        type.add(4, "Leather_Zombie"); //Charged_Creeper
        locations.add(5, new Location(Bukkit.getWorld("pve"), 108, 194, 254));
        type.add(5, "Golden_Skeleton");
        locations.add(6, new Location(Bukkit.getWorld("pve"), 110, 226, 197));
        type.add(6, "Leather_Skeleton");
        locations.add(7, new Location(Bukkit.getWorld("pve"), 112, 223, 201));
        type.add(7, "Leather_Zombie");
        locations.add(8, new Location(Bukkit.getWorld("pve"), 114, 224, 201));
        type.add(8, "Golden_Skeleton");
    }
}

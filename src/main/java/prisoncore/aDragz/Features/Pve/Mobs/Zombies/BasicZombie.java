package prisoncore.aDragz.Features.Pve.Mobs.Zombies;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class BasicZombie {
    
    public static void spawnZombie(Location location) {
        Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        
        zombie.setCustomName("§7§lBasic Zombie");
        zombie.setCustomNameVisible(true);
        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_AXE));
        
        zombie.getEquipment().setItemInMainHandDropChance(0);
        
        zombie.damage(2);
        zombie.setRemoveWhenFarAway(false);
    }
}

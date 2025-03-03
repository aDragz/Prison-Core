package prisoncore.aDragz.Features.Pve.Mobs.Zombies;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class LeatherZombie {
    
    public static void spawnZombie(Location location) {
        Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        
        zombie.setCustomName("§8§lLeather Zombie");
        zombie.setCustomNameVisible(true);
        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
        
        zombie.getEquipment().setItemInMainHandDropChance(0);
        
        zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        
        zombie.getEquipment().setHelmetDropChance(0);
        zombie.getEquipment().setChestplateDropChance(0);
        zombie.getEquipment().setLeggingsDropChance(0);
        zombie.getEquipment().setBootsDropChance(0);
        
        zombie.damage(3);
        zombie.setRemoveWhenFarAway(false);
    }
}

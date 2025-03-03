package prisoncore.aDragz.Features.Pve.Mobs.Skeletons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;

public class LeatherSkeleton {
    
    public static void spawnSkeleton(Location location) {
        Skeleton skeleton = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
        
        skeleton.setCustomName("§8§lLeather Skeleton");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
        skeleton.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
        skeleton.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        skeleton.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 5);
        
        skeleton.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        
        skeleton.getEquipment().setItemInMainHandDropChance(0);
        
        skeleton.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        skeleton.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        skeleton.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        skeleton.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBootsDropChance(0);
        
        skeleton.damage(3);
        skeleton.setRemoveWhenFarAway(false);
    }
}

package prisoncore.aDragz.Features.Pve.Mobs.Skeletons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;

public class GoldSkeleton {
    
    public static void spawnSkeleton(Location location) {
        Skeleton skeleton = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
        
        skeleton.setCustomName("§6§lGolden Skeleton");
        skeleton.setCustomNameVisible(true);
        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
        skeleton.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
        skeleton.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        skeleton.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 5);
        
        skeleton.getEquipment().getItemInMainHand().addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        
        skeleton.getEquipment().setItemInMainHandDropChance(0);
        
        skeleton.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
        skeleton.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
        skeleton.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
        skeleton.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
        
        skeleton.getEquipment().setHelmetDropChance(0);
        skeleton.getEquipment().setChestplateDropChance(0);
        skeleton.getEquipment().setLeggingsDropChance(0);
        skeleton.getEquipment().setBootsDropChance(0);
        
        skeleton.setFireTicks(1);
        skeleton.setRemoveWhenFarAway(false);
    }
}

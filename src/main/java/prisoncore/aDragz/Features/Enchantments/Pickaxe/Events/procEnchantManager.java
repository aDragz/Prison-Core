package prisoncore.aDragz.Features.Enchantments.Pickaxe.Events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getLevel;
import prisoncore.aDragz.Features.Enchantments.EnchantmentManager.getProc;
import prisoncore.aDragz.Features.Gangs.gangCheckInfo;

public class procEnchantManager {

    public static void procEnchants(Player player, Block block) {
        String leader = gangCheckInfo.grabLeaderIGN(player);
        //Run procs for player:
        getProc.procMoneyFinder(Integer.valueOf(getLevel.grabEnchantLevel(player.getPlayer().getInventory().getItem(0), "Money Finder")), player.getPlayer());
        getProc.procTokenFinder(Integer.valueOf(getLevel.grabEnchantLevel(player.getPlayer().getInventory().getItem(0), "Token Finder")), player.getPlayer());
        getProc.procTnt(Integer.valueOf(getLevel.grabEnchantLevel(player.getPlayer().getInventory().getItem(0), "Tnt")), player.getPlayer(), block.getY(), block.getType(), leader);
        getProc.procThunder(Integer.valueOf(getLevel.grabEnchantLevel(player.getPlayer().getInventory().getItem(0), "Thunder")), player.getPlayer(), block.getY(), block.getType(), leader, block.getLocation());
        getProc.procJackhammer(Integer.valueOf(getLevel.grabEnchantLevel(player.getPlayer().getInventory().getItem(0), "Jackhammer")), player.getPlayer(), block.getY(), block.getType(), leader);
        getProc.procLaser(Integer.valueOf(getLevel.grabEnchantLevel(player.getPlayer().getInventory().getItem(0), "Laser")), player.getPlayer(), block.getType(), leader, block.getLocation());
        getProc.procCharity(Integer.valueOf(getLevel.grabEnchantLevel(player.getPlayer().getInventory().getItem(0), "Charity")), player.getPlayer());
        getProc.procNuke(Integer.valueOf(getLevel.grabEnchantLevel(player.getPlayer().getInventory().getItem(0), "Nuke")), player.getPlayer(), block.getType(), leader);
    }
}

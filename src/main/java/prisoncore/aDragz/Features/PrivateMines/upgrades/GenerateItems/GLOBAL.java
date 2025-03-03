package prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.Features.PrivateMines.gui;

import java.util.ArrayList;
import java.util.Objects;

public class GLOBAL {
    /*
    This will make it easier as I can just copy and paste a whole new folder, keeping main code neat.
    This will also make it easier to create new Items and stuff for general purpose.
    There will be a general one, but here will contain bits JUST for Global, or EFFECTS with effects classes. Like replacing text 
    (Kinda like Available effects for the player etc). 
    */
    public static ItemStack generateItem(String type, int pageNumber, Player player) {
        /*
        str:
        type = Type of item

        int:
        pageNumber = Page Number

        player:
        player = player
        */
        Material ConfigBlock = Material.matchMaterial(Objects.requireNonNull(gui.cfg.getString(String.format("GUI.%s.Material", type))));

        ItemStack Item = new ItemStack(ConfigBlock);

        ItemMeta Meta = Item.getItemMeta();
        ArrayList<String> Lore = new ArrayList<>();

        ArrayList<String> LoreConfList = new ArrayList<>();
        LoreConfList.addAll(gui.cfg.getStringList(String.format("GUI.%s.Lore", type)));

        Lore.addAll(LoreConfList);

        switch (type.toLowerCase()) {
            case "upgrade_mine":
                String DisplayName = gui.cfg.getString(String.format("GUI.%s.Display_Name", type));
                Meta.setDisplayName(DisplayName.replaceAll("&", "§"));
                Meta.setLore(UPGRADE_MINE.generateUpgradeLore(Lore, player));
                Item.setItemMeta(Meta);
                break;
            case "minetp":
                String tpDisplayName = gui.cfg.getString(String.format("GUI.%s.Display_Name", type));
                Meta.setDisplayName(tpDisplayName.replaceAll("&", "§"));
                Item.setItemMeta(Meta);
                break;
        }

        return Item;
    }
}

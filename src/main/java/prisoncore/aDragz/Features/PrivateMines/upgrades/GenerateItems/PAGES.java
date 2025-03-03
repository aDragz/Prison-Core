package prisoncore.aDragz.Features.PrivateMines.upgrades.GenerateItems;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prisoncore.aDragz.Features.PrivateMines.gui;

public class PAGES {
    
    static String fPage_DisplayName = gui.cfg.getString("GUI.Pages.Forward.Display_Name").replaceAll("&", "§");

    public static ItemStack page_forward() { //Generates Item for inventory
        ItemStack PageItem = new ItemStack(gui.FORWARD_PAGE);
        ItemMeta PageMeta = PageItem.getItemMeta();

        PageMeta.setDisplayName(fPage_DisplayName);

        PageItem.setItemMeta(PageMeta);

        return PageItem;
    }

    static String bPage_DisplayName = gui.cfg.getString("GUI.Pages.Backward.Display_Name").replaceAll("&", "§");

    public static ItemStack page_backward() { //Generates Item for inventory
        ItemStack PageItem = new ItemStack(gui.PREVIOUS_PAGE);
        ItemMeta PageMeta = PageItem.getItemMeta();

        PageMeta.setDisplayName(bPage_DisplayName);

        PageItem.setItemMeta(PageMeta);

        return PageItem;
    }
}

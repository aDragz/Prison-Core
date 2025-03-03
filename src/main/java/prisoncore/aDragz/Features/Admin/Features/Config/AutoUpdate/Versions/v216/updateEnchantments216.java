package prisoncore.aDragz.Features.Admin.Features.Config.AutoUpdate.Versions.v216;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class updateEnchantments216 {
    private final static main plugin = main.getPlugin(main.class);
    
    static File file = new File(plugin.getDataFolder(), "Configs/enchantments.yml");
    
    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static void updateMessages() throws IOException {

        ArrayList<String> pickaxeLore = new ArrayList<>();

        pickaxeLore.add("");
        pickaxeLore.add("&7&oPress Q to open Enchantments Menu");
        pickaxeLore.add("&7&oUpgrade Amount: Left = 1, Right = 10, Shift+Click = 100");
        pickaxeLore.add("");
        pickaxeLore.add("");
        pickaxeLore.add("&c&lEnchantments:");

        cfg.set("Pickaxe.Lore", pickaxeLore);

        cfg.set("Pickaxe.Lore-Special.Blocks-Broken.Enabled", true);
        cfg.set("Pickaxe.Lore-Special.Blocks-Broken.Line", 4);
        cfg.set("Pickaxe.Lore-Special.Blocks-Broken.Lore", "&eBlocks Broken&f: &aBLOCKS");

        cfg.set("Pickaxe.Lore-Special.Pickaxe-Level.Enabled", true);
        cfg.set("Pickaxe.Lore-Special.Pickaxe-Level.Line", 6);
        cfg.set("Pickaxe.Lore-Special.Pickaxe-Level.Lore", "&ePickaxe Level&f: &aLEVEL PERCENTAGE");

        cfg.save(file);
    }
}

package prisoncore.aDragz.Features.PrisonCoreAdmin.Schematics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

public class createMineSchematic {
    //With this, the player should already have a schematic made.
    //They should use FAWE

    private static final main plugin = main.getPlugin(main.class);

    public static void moveSchematic(Player player, String mineName) {
        //Args are for name
        //player is to grab their UUID

        player.sendMessage(ChatColor.GREEN + "Transferring File!");

        String FAWEFile = String.valueOf((Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit").getDataFolder()));

        File file = new File(FAWEFile + String.format("/schematics/%s.schem", mineName));

        try {
            player.sendMessage(ChatColor.GREEN + "Attempting to Move File!");
            File schem = new File(plugin.getDataFolder() + "/Schematics");
            File target = new File(schem + String.format("/%s.schem", mineName));

            if (!(schem.exists())) {
                player.sendMessage(ChatColor.RED + schem.toString() + " not found! Attempting to create directory");
                try {
                    schem.mkdir();
                    player.sendMessage(ChatColor.GREEN + schem.toPath().toString() + " Created Successfully!");
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + e.getMessage());
                }
            }

            Files.move(file.toPath(), target.toPath());

            player.sendMessage(ChatColor.GREEN + "File Moved Successfully!");
            player.sendMessage(String.format("&8[&a%s&8] &f--> &8[&a%s&8]".replaceAll("&", "§")
                    , file.toPath(), target.toPath()));
        } catch (NoSuchFileException e) {
            player.sendMessage(ChatColor.RED + "Could not find File: " + e.getMessage());
        } catch (IOException e) {
            player.sendMessage(ChatColor.RED + e.toString());
        }
    }
}

package prisoncore.aDragz.Features.Admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class adminTab implements TabCompleter {
    private static final main plugin = main.getPlugin(main.class);
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (command.getName().equals("admin")) {

            if (args.length == 1) {

                ArrayList<String> tabList = new ArrayList<>();
                tabList.clear();

                if (sender.hasPermission("PrisonCore.Admin.Gang")) {
                    tabList.add("gang");
                }

                if (sender.hasPermission("PrisonCore.Admin.Pickaxe")) {
                    tabList.add("pickaxe");
                }

                if (sender.hasPermission("PrisonCore.Admin.Mine")) {
                    tabList.add("mine");
                }

                if (sender.hasPermission("PrisonCore.Admin.Rankup")) {
                    tabList.add("rankup");
                }

                if (sender.hasPermission("PrisonCore.Admin.Update")) {
                    tabList.add("update");
                }
                
                /*if (sender.hasPermission("PrisonCore.Admin.Pve")) {
                    tabList.add("pve");
                }*/

                return tabList;
            }

            /*
            GANG
             */

            if (args[0].equalsIgnoreCase("gang")) { //GANGS

                if (args.length == 2) {

                    ArrayList<String> tabList = new ArrayList<>();
                    tabList.clear();

                    if (sender.hasPermission("Prisoncore.Admin.Gang.Reset"))
                        tabList.add("reset");

                    if (sender.hasPermission("Prisoncore.Admin.Gang.List"))
                        tabList.add("list");

                    if (sender.hasPermission("Prisoncore.Admin.Gang.Info"))
                        tabList.add("info");

                    if (sender.hasPermission("Prisoncore.Admin.Gang.Name"))
                        tabList.add("name");

                    return tabList;
                }

                if (args.length == 3) {

                    ArrayList<String> tabList = new ArrayList<>();

                    if (args[1].equalsIgnoreCase("info")) {
                        if (sender.hasPermission("Prisoncore.Admin.Gang.Info")) {
                            File[] gangFile = new File(plugin.getDataFolder(), "Gangs").listFiles();

                            for (int i = 0; i != gangFile.length; ) {
                                tabList.add(gangFile[i].getName().replaceAll(".yml", ""));

                                i++;
                            }

                            return tabList;
                        }
                    }
                }
            }

            if (args[0].equalsIgnoreCase("pickaxe")) {

                if (args.length == 2) {
                    ArrayList<String> tabList = new ArrayList<>();
                    if (sender.hasPermission("PrisonCore.Admin.Pickaxe.Give"))
                        tabList.add("give");

                    return tabList;
                }

                //For the pickaxe give, check first. As I cannot check args.length or it will be too many lines of code ([2],[4],[6] etc)

                if (args[1].equalsIgnoreCase("give")) {
                    if (sender.hasPermission("PrisonCore.Admin.Pickaxe.Give")) {
                        ArrayList<String> tabList = new ArrayList<>();
                        //Check if args is even or odd

                        /*
                        [0] pickaxe - e
                        [1] give - o
                        [2] aDragz - e
                        [3] Fortune - o
                        [4] 10 - e
                        [5] Exp - o
                        [6] 100 - e
                         */

                        if (args.length == 3) {
                            return null;
                        }

                        if ((args.length % 2) == 0) { //Odd
                            //Add enchants
                            tabList.add("Charity");
                            tabList.add("Efficiency");
                            tabList.add("Exp");
                            tabList.add("Fortune");
                            tabList.add("Money");
                            tabList.add("Nuke");
                            tabList.add("Token");
                            tabList.add("Tnt");
                            tabList.add("Jackhammer");
                            tabList.add("Thunder");
                            tabList.add("Laser");
                        } else {
                            tabList.add("LEVEL");
                        }

                        return tabList;

                    }
                }
            }

            if (args[0].equalsIgnoreCase("mine")) { //GANGS

                if (args.length == 2) {
                    ArrayList<String> tabList = new ArrayList<>();

                    if (sender.hasPermission("PrisonCore.Admin.Mine.Tp"))
                        tabList.add("tp");

                    if (sender.hasPermission("PrisonCore.Admin.Mine.Reset"))
                        tabList.add("reset");

                    if (sender.hasPermission("PrisonCore.Admin.Mine.setSchematic"))
                        tabList.add("setSchematic");

                    if (sender.hasPermission("PrisonCore.Admin.Mine.setWorld"))
                        tabList.add("setWorld");

                    return tabList;
                }

                if (args.length == 3) {
                    ArrayList<String> tabList = new ArrayList<>();
                    if (args[1].equalsIgnoreCase("setWorld")) {
                        Bukkit.getWorlds().forEach(world -> {
                            tabList.add(world.getName());
                        });

                        return tabList;
                    }

                    if (args[1].equalsIgnoreCase("setSchematic")) {
                        File schem = new File(plugin.getDataFolder() + "/Schematics");

                        Arrays.stream(schem.listFiles()).forEach(file -> {
                            tabList.add(file.getName().replaceAll(".schem", ""));
                        });

                        return tabList;
                    }


                }
            }

            if (args[0].equalsIgnoreCase("rankup")) {

                if (args.length == 2) {
                    ArrayList<String> tabList = new ArrayList<>();

                    if (sender.hasPermission("PrisonCore.Admin.Rankup.Reset"))
                        tabList.add("reset");

                    if (sender.hasPermission("PrisonCore.Admin.Rankup.Set"))
                        tabList.add("set");

                    if (sender.hasPermission("PrisonCore.Admin.Rankup.Save"))
                        tabList.add("save");

                    return tabList;
                }
            }
        }
        /*
        if (args[0].equalsIgnoreCase("pve")) { //PVE
         
            if (args.length == 2) {
                ArrayList<String> tabList = new ArrayList<>();
                
                if (sender.hasPermission("PrisonCore.Admin.Pve.ResetMobs")) {
                    tabList.add("reset");
                }
                
                if (sender.hasPermission("PrisonCore.Admin.Pve.ForceTp")) {
                    tabList.add("forcetp");
                }
                
                return tabList;
            }
        }*/
        return null;
    }
}

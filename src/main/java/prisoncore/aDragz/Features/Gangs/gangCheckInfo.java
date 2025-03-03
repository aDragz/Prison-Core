package prisoncore.aDragz.Features.Gangs;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class gangCheckInfo implements Listener {
    private final static main plugin = main.getPlugin(main.class);

    private static final FileConfiguration config = plugin.getConfig();
    static int max_members = config.getInt("Type.Gangs.Max_Members");

    public static boolean checkGangStatus(OfflinePlayer player) {
        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

        return Objects.nonNull(playerCfg.getString("Info.Gang.Name"));
    }

    public static String grabGangName(OfflinePlayer player) {
        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", Bukkit.getOfflinePlayer(player.getName()).getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

        String name = playerCfg.getString("Info.Gang.Name");

        if (Objects.isNull(name)) {
            name = "None";
        }

        return name;
    }

    public static ArrayList<String> grabPlayerInvites(OfflinePlayer player) {
        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

        ArrayList<String> invites = new ArrayList<>(playerCfg.getStringList("Info.Gang.Invites"));

        //Check if there are invites or not
        String invitesFinal;

        invitesFinal = String.join(", ", invites);

        //Check if player has any invites
        if (invitesFinal.length() == 0) {
            invitesFinal = "None";
        }

        return invites;
    }

    public static void adminCheckStats(CommandSender player, OfflinePlayer argsPlayer) {
        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", (argsPlayer).getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

        String gangName = playerCfg.getString("Info.Gang.Name");

        File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangName));
        FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

        String gangLeader = gangCfg.getString("Info.Leader");
        long gangBalance = gangCfg.getInt("Info.Bank");


        ArrayList<String> gangMembersUUID = new ArrayList<>();
        ArrayList<String> gangMembersIGN = new ArrayList<>();
        ArrayList<String> gangInviteList = new ArrayList<>();

        try {
            gangMembersUUID.addAll(gangCfg.getStringList("Members"));
            gangInviteList.addAll(gangCfg.getStringList("Invites"));

            gangMembersIGN.addAll(IDtoIGN(gangMembersUUID));
        } catch (IndexOutOfBoundsException e) {
        }
        int finalMembersIgnSize; //for count, so it doesn't count "none" as a member

        if (gangMembersIGN.size() == 0) {
            finalMembersIgnSize = 0;
            gangMembersIGN.add(0, "None");
        } else {
            finalMembersIgnSize = gangMembersIGN.size();
        }

        if (gangInviteList.size() == 0) {
            gangInviteList.add(0, "None");
        }

        String invitesFinal = String.join(", ", gangInviteList);
        String ignFinal = String.join(", ", gangMembersIGN);

        player.sendMessage(String.format("§eName: §f%s", gangName));
        player.sendMessage(String.format("§eLeader: §f%s", gangLeader));
        player.sendMessage(String.format("§eMembers: §f%s {%d/%d}", ignFinal, finalMembersIgnSize, max_members));
        player.sendMessage(String.format("§eInvites: §f%s {%d/%d}", invitesFinal, 0, 3));
    }

    public static void showPlayerInvites(OfflinePlayer player) {
        File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", player.getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

        ArrayList<String> invites = new ArrayList<>(playerCfg.getStringList("Info.Gang.Invites"));

        //Check if there are invites or not
        String invitesFinal;

        invitesFinal = String.join(", ", invites);

        //Check if player has any invites
        if (invitesFinal.length() == 0) {
            invitesFinal = "None";
        }

        player.getPlayer().sendMessage(grabMessagesValue.type("gang", "invite_show").replaceAll("&", "§")
                .replaceAll("INVITENAME", invitesFinal));
    }

    public static void clearInvites(Player player) {
        File playerFile = new File(plugin.getDataFolder(), String.format("/PlayerData/%s.yml", player.getUniqueId()));
        FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

        ArrayList<String> invites = new ArrayList<>();
        ArrayList<String> gangInviteNames = new ArrayList<>();

        invites.addAll(playerCfg.getStringList("Info.Gang.Invites"));

        File gangFile;
        FileConfiguration gangCfg;
        int total = invites.size();

        for (int i = 0; i <= total - 1; ) {
            gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", invites.get(i)));
            gangCfg = YamlConfiguration.loadConfiguration(gangFile);

            invites = new ArrayList<>(gangCfg.getStringList("Invites"));
            for (String name : invites) {
                if (!name.equals(player.getName())) {
                    gangInviteNames.add(name);
                }
            }

            if (gangInviteNames.size() == 0) {
                gangCfg.set("Invites", null);
            } else {
                gangCfg.set("Invites", gangInviteNames);
            }

            try {
                gangCfg.save(gangFile);
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(e.getMessage());
            }

            i += 1;
        }

        if (playerCfg.getStringList("Info.Gang.Invites").size() != 0) {
            playerCfg.set("Info.Gang.Invites", "");
            try {
                playerCfg.save(playerFile);
            } catch (IOException e) {
            }
        }
    }

    public static void clearPlayerInvites(File file, FileConfiguration cfg, String cfgPath) {
        cfg.set(cfgPath, "");
        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearInviteName(Player player, String gangName, ArrayList<String> invites, File file, FileConfiguration cfg, String cfgPath) {
        //Used like in gang join. if it cannot find gang in gangs.yml, it removes from list.
        //Can also use for gang members list. As long as it has all the required values
        //Do not send message to player, act in the background from them not knowing.

        //Player = player
        //gangName = gang name you want to remove
        //invites = list of invites you want to remove gangName from
        //file = player file
        //cfg = player file
        //cfgPath = path of the invites list

        if (!invites.contains(gangName)) {
            return;
        }

        invites.remove(gangName);

        cfg.set(cfgPath, invites);

        try {
            cfg.save(file);
        } catch (IOException e) {
        }
    }


    public static ArrayList<String> IDtoIGN(ArrayList<String> IDs) {
        ArrayList<String> ign = new ArrayList<>();

        int total = IDs.size();

        for (int i = 0; i < total; ) {
            try {
                IDs.get(i);

                ign.add(Bukkit.getOfflinePlayer(UUID.fromString(IDs.get(i))).getName());

                i += 1;
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(e.getMessage());
                return null;
            }
        }

        return ign;
    }

    public static ArrayList<String> grabOnlinePlayers(File file, FileConfiguration fileCfg) {

        ArrayList<String> finalList = new ArrayList<>();


        finalList.add(fileCfg.getString("Info.Leader"));
        finalList.addAll(IDtoIGN((ArrayList<String>) fileCfg.getStringList("Members")));

        return finalList;
    }

    public static String grabLeaderIGN(OfflinePlayer player) {
        try {
            File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", (player).getUniqueId()));
            FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

            String gangName = playerCfg.getString("Info.Gang.Name");

            if (Objects.isNull(gangName))
                return "None";

            File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangName));
            FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

            return gangCfg.getString("Info.Leader");
        } catch (Exception e) {
            return "None";
        }
    }

    public static String grabMembersIGN(OfflinePlayer player, int number) {
        try {
            File playerFile = new File(plugin.getDataFolder(), String.format("PlayerData/%s.yml", (player).getUniqueId()));
            FileConfiguration playerCfg = YamlConfiguration.loadConfiguration(playerFile);

            String gangName = playerCfg.getString("Info.Gang.Name");

            File gangFile = new File(plugin.getDataFolder(), String.format("Gangs/%s.yml", gangName));
            FileConfiguration gangCfg = YamlConfiguration.loadConfiguration(gangFile);

            ArrayList<String> gangMembersUUID = new ArrayList<>();
            ArrayList<String> gangMembersIGN = new ArrayList<>();

            try {
                gangMembersUUID.addAll(gangCfg.getStringList("Members"));

                gangMembersIGN.addAll(IDtoIGN(gangMembersUUID));

                return gangMembersIGN.get(number);
            } catch (IndexOutOfBoundsException e) {
            }
        } catch (Exception e) {
            return " ";
        }
        return " ";
    }
}
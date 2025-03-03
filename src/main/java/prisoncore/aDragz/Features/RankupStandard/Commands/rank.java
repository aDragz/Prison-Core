package prisoncore.aDragz.Features.RankupStandard.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import prisoncore.aDragz.ConfigFiles.cache.ranksCache;
import prisoncore.aDragz.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class rank implements CommandExecutor, Listener {
    //
    // Checks if player ran /ranks. Then displayed the ranks with the pricing.
    //
    private static final main plugin = main.getPlugin(main.class);

    FileConfiguration config = plugin.getConfig();

    public void onEnable() {

        if (config.getBoolean("Features.Ranks.Enabled")) {
            ArrayList<String> ranksArray = new ArrayList<>();

            File file = new File(plugin.getDataFolder(), "Cache/RanksCache.yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

            List<String> ranksName = cfg.getStringList("Ranks.rankName");

            ranksArray.add(ranksName.toString());

            //ranksMessage.addAll(messages);
            //displayRanks(player);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("ranks")) {
            ArrayList<String> message = new ArrayList<>();
            message.addAll(ranksCache.ranksFinalMessage);

            int messageTotal = message.size();

            for (int i = 0; i != messageTotal; ) {
                String tempMessage; //goes into player message array after so it's temp

                tempMessage = (message.get(i));

                player.sendMessage(tempMessage);
                i += 1;
            }
        }
        return false;
    }
}

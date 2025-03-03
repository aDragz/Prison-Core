package prisoncore.aDragz;

import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import prisoncore.aDragz.ConfigFiles.cache.ranksCache;
import prisoncore.aDragz.ConfigFiles.onFirstJoin.messages;
import prisoncore.aDragz.ConfigFiles.onFirstJoin.ranks;
import prisoncore.aDragz.Features.Admin.adminCommandManager;
import prisoncore.aDragz.Features.Admin.adminTab;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.Events.movePickaxe;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.GuiManager.clickManager;
import prisoncore.aDragz.Features.Enchantments.Pickaxe.enchantmentManager;
import prisoncore.aDragz.Features.Gangs.gangCommands;
import prisoncore.aDragz.Features.Gangs.gangsTab;
import prisoncore.aDragz.Features.Gangs.management.gangChat.activeGangChat;
import prisoncore.aDragz.Features.Gangs.management.gangChat.gangChat;
import prisoncore.aDragz.Features.Logs.CommandHandler.commandListener;
import prisoncore.aDragz.Features.Logs.CommandHandler.logsTab;
import prisoncore.aDragz.Features.Logs.LogManagement.Tokens.saveTokenTransactions;
import prisoncore.aDragz.Features.Logs.LogManagement.Tokens.storeTokenTransactions;
import prisoncore.aDragz.Features.Player.Commands.Admin.moderator_commands.minor.checkGang;
import prisoncore.aDragz.Features.Pouches.Events.PouchJoinEvent;
import prisoncore.aDragz.Features.PrisonCoreAdmin.adminCommandHandler;
import prisoncore.aDragz.Features.PrivateMines.PMineCommandHandler;
import prisoncore.aDragz.Features.PrivateMines.minesTab;
import prisoncore.aDragz.Features.PrivateMines.upgrades.InventoryClickEvent.BLOCKS_ClickManager;
import prisoncore.aDragz.Features.PrivateMines.upgrades.InventoryClickEvent.EFFECTS_ClickManager;
import prisoncore.aDragz.Features.PrivateMines.upgrades.InventoryClickEvent.GUI_ClickManager;
import prisoncore.aDragz.Features.PrivateMines.upgrades.upgradeManager.fileHandler;
import prisoncore.aDragz.Features.RankupInfinite.Commands.rankupInfinite;
import prisoncore.aDragz.Features.RankupInfinite.Commands.rankupMaxInfinite;
import prisoncore.aDragz.Features.RankupInfinite.Data.Storage.saveRanksInfinite;
import prisoncore.aDragz.Features.RankupInfinite.Data.Storage.storeRanksInfinite;
import prisoncore.aDragz.Features.RankupInfinite.Events.ranksPlayerJoinInfinite;
import prisoncore.aDragz.Features.RankupStandard.Commands.rank;
import prisoncore.aDragz.Features.RankupStandard.Commands.rankup;
import prisoncore.aDragz.Features.RankupStandard.Data.saveRanks;
import prisoncore.aDragz.Features.RankupStandard.Data.storeRanks;
import prisoncore.aDragz.Features.RankupStandard.Events.ranksPlayerJoin;
import prisoncore.aDragz.Features.SafeReboot.rebootCommands;
import prisoncore.aDragz.Features.SafeReboot.rebootCooldown;
import prisoncore.aDragz.Features.Sell.Multipliers.cmds.multiCommands;
import prisoncore.aDragz.Features.Sell.Multipliers.data.savePermMultipliers;
import prisoncore.aDragz.Features.Sell.Multipliers.data.storePermMultipliers;
import prisoncore.aDragz.Features.Sell.Multipliers.multiTab;
import prisoncore.aDragz.Features.Sell.Multipliers.onPlayerJoinEvent;
import prisoncore.aDragz.Features.Sell.Sellall.events.storeMoneyPrices;
import prisoncore.aDragz.Features.Sell.giveMoney;
import prisoncore.aDragz.Features.Tokens.Commands.tokenMain;
import prisoncore.aDragz.Features.Tokens.Events.tokenPlayerJoin;
import prisoncore.aDragz.Features.Tokens.data.Tokens.saveTokens;
import prisoncore.aDragz.Features.Tokens.data.Tokens.storeTokens;
import prisoncore.aDragz.Features.Tokens.tokensFileManager;
import prisoncore.aDragz.Features.Tokens.tokensTab;
import prisoncore.aDragz.Features.blockCount.Commands.block;
import prisoncore.aDragz.Features.blockCount.Data.saveBlocks;
import prisoncore.aDragz.Features.blockCount.Data.storeBlocks;
import prisoncore.aDragz.Features.blockCount.Events.blocksPlayerJoinEvent;
import prisoncore.aDragz.data.placeholderAPI;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public final class main extends JavaPlugin implements Listener {
    
    FileConfiguration config = getConfig();
    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    
    @Override
    public void onEnable() {
        
        //Load plugins:
        
        //Set bStats [Server Statistics]
        int ID = 16316;
        new Metrics(this, ID);
        
        //Vault
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            log.severe(String.format("[%s] - This can include but not limited to: '%s'!", getDescription().getName(), "EssentialsX"));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        if (!(getServer().getPluginManager().isPluginEnabled("FastAsyncWorldEdit"))) {
            log.severe(String.format("[%s] - Disabled due to no FastAsyncWorldEdit dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        if (!(getServer().getPluginManager().isPluginEnabled("WorldGuard"))) {
            log.severe(String.format("[%s] - Disabled due to no WorldGuard dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListener here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
            new placeholderAPI().register();
        }
        
        //Load Files + Configs.
        
        saveDefaultConfig();
        
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        
        //reboot:
        getCommand("reboot").setExecutor(new rebootCommands());
        rebootCooldown.timer(); //Runs timer, do it now so it repeats one time, rather than many. Or it will lag the server, and display many instances
        
        //pouches:
        //first player join event for the whole core:
        this.getServer().getPluginManager().registerEvents(new PouchJoinEvent(), this);
        
        
        if (config.getBoolean("Features.Ranks.Enabled")) {
            try {
                if (ranks.checkConfig()) {
                    
                    Bukkit.getConsoleSender().sendMessage(config.getString("Type.Ranks.Type"));
                    
                    if (Objects.equals(config.getString("Type.Ranks.Type").toLowerCase(), "infinite")) {
                        
                        getCommand("rankup").setExecutor(new rankupInfinite());
                        getCommand("rankupmax").setExecutor(new rankupMaxInfinite());
                        storeRanksInfinite.generateRanks();
                        
                        this.getServer().getPluginManager().registerEvents(new ranksPlayerJoinInfinite(), this);
                        
                        sendConsoleMessage("∞ ranks", true);
                        
                    } else {
                        getCommand("ranks").setExecutor(new rank());
                        getCommand("rankup").setExecutor(new rankup());
                        createCache("ranks");
                        
                        storeRanks.generateRanks();
                        
                        this.getServer().getPluginManager().registerEvents(new ranksPlayerJoin(), this);
                        
                        sendConsoleMessage("ranks", true);
                        
                    }
                } else {
                    sendConsoleMessage("ranks", false);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        //Load messages.yml
        File messagesfile = new File(getDataFolder(), "Messages.yml");
        
        if (!(messagesfile.exists())) { //Checks if it does not exist
            try {
                messages.createFile();  //Creates file inside class
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        //Check if it exists after creation.
        //As it ignores if it already exists and I want it to say,
        //even after first-time use.
        sendConsoleMessage("messages", messagesfile.exists());
        
        
        //Function Commands:
        
        getCommand("prisoncore").setExecutor(new adminCommandHandler());
        getCommand("admin").setExecutor(new adminCommandManager());
        getCommand("admin").setTabCompleter(new adminTab());
        
        /*PVE
        getCommand("spawnmobs").setExecutor(new commands());
        this.getServer().getPluginManager().registerEvents(new creeperExplosion(), this);
        this.getServer().getPluginManager().registerEvents(new mobKill(), this);
        onStartUp.generateLocations();
        spawnMobs.generateMobs();
        getCommand("pve").setExecutor(new tpPlayer());
         */
        
        try {
            getCommand("blocks").setExecutor(new block());
            
            storeBlocks.generateMapPermMultiplier();
            this.getServer().getPluginManager().registerEvents(new blocksPlayerJoinEvent(), this);
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        if (config.getBoolean("Features.Transactions.Enabled")) {
            
            getCommand("transactions").setExecutor(new commandListener());
            getCommand("transactions").setTabCompleter(new logsTab());
            
        }
        
        if (config.getBoolean("Features.Gangs.Enabled")) {
            getCommand("gang").setExecutor(new gangCommands());
            getCommand("gang").setTabCompleter(new gangsTab());
            sendConsoleMessage("gangs", true);
            
            this.getServer().getPluginManager().registerEvents(new gangChat(), this);
            activeGangChat.generateList();
            
            getCommand("checkgang").setExecutor(new checkGang());
            
            //Check if Private Mines are enabled (needs gangs to be enabled)
            if (config.getBoolean("Features.Private_Mines.Enabled")) {
                fileHandler.makeFiles(); //For Mines
                
                saveDefaultConfig();
                
                getCommand("mine").setExecutor(new PMineCommandHandler());
                getCommand("mine").setTabCompleter(new minesTab());
                
                //Events:
                
                this.getServer().getPluginManager().registerEvents(new GUI_ClickManager(), this);
                this.getServer().getPluginManager().registerEvents(new EFFECTS_ClickManager(), this);
                this.getServer().getPluginManager().registerEvents(new BLOCKS_ClickManager(), this);
                
                //Enchantments (for private mines to be enabled):
                
                this.getServer().getPluginManager().registerEvents(new enchantmentManager(), this);
                this.getServer().getPluginManager().registerEvents(new clickManager(), this);
                
                this.getServer().getPluginManager().registerEvents(new tokensFileManager(), this);
                this.getServer().getPluginManager().registerEvents(new tokenPlayerJoin(), this);
                
                getCommand("token").setExecutor(new tokenMain());
                getCommand("token").setTabCompleter(new tokensTab());
                
                sendConsoleMessage("private mines", true);
            } else {
                sendConsoleMessage("private mines", false);
            }
        } else {
            sendConsoleMessage("gangs", false);
            sendConsoleMessage("private mines", false);
        }
        
        if (config.getBoolean("Features.Sell.Enabled")) {
            this.getServer().getPluginManager().registerEvents(new giveMoney(), this);
            //Multipliers:
            try { //multi
                storePermMultipliers.generateMapPermMultiplier();
            } catch (IOException Ignored) {}
            
            getCommand("multiplier").setExecutor(new multiCommands()); //multi
            getCommand("multiplier").setTabCompleter(new multiTab());
            
            try {
                storeTokens.generateMapPermMultiplier();
                storeTokenTransactions.generateMapPermTransactions();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.getServer().getPluginManager().registerEvents(new onPlayerJoinEvent(), this);
            this.getServer().getPluginManager().registerEvents(new movePickaxe(), this);
            
            //Start sellall timer
            storeMoneyPrices.sendMessages();
            
            sendConsoleMessage("sell", true);
            sendConsoleMessage("multipliers", true);
        } else {
            sendConsoleMessage("sell", false);
            sendConsoleMessage("multipliers", false);
        }
    }
    
    //Vault:
    
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        sendConsoleMessage("vault", true);
        return econ != null;
    }
    
    public static Economy getEconomy() {
        return econ;
    }
    
    @Override
    public void onDisable() {
        //Delete Cache
        
        File Cachefile = new File(getDataFolder(), "Cache/RanksCache.yml");
        
        if (Cachefile.exists()) {
            Cachefile.delete();
            Bukkit.getConsoleSender().sendMessage("[Prison-Core] Deleted " + Cachefile.getAbsolutePath().toUpperCase());
        }
        
        //Save Settings
        try {
            activeGangChat.saveList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            savePermMultipliers.autoSavePermMultiplier();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        try {
            saveRanks.autoSaveRanks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            saveRanksInfinite.autoSaveRanks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            saveTokenTransactions.autoSaveTransactions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        try {
            saveTokens.autoSaveTokens();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        try {
            saveBlocks.autoSaveTokens();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void sendConsoleMessage(String typeName, Boolean returnValue) {
        //Example:
        //
        //if (messages.createFile() == true) {  //Creates file inside class
        //                   sendConsoleMessage("messages.yml", true);
        //                } else { sendConsoleMessage("messages.yml", false);}
        
        String value;
        
        if (returnValue.equals(true)) {
            value = "enabled";
        } else {
            value = "disabled";
        }
        
        Bukkit.getConsoleSender().sendMessage("[Prison-Core] " + typeName.toUpperCase() + " has been " + value);
    }
    
    public void createCache(String type) throws IOException {
        //HashMap<String, ArrayList> ranks = new HashMap<String, ArrayList>();
        if (type.equals("ranks")) {
            ranksCache.Cache();
        }
    }
    
    //private BukkitAudiences adventure;
    //
    //public @NonNull BukkitAudiences adventure() {
    //    if(this.adventure == null) {
    //        throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
    //    }
    //    return this.adventure;
    //}
}
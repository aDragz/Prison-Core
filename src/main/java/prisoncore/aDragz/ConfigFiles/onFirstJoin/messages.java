package prisoncore.aDragz.ConfigFiles.onFirstJoin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import prisoncore.aDragz.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class messages implements Listener {
    private final static main plugin = main.getPlugin(main.class);

    /*

    Old: message = grabMessagesValue.type("gang", "exists").replaceAll("&", "§");
    //player.sendMessage(message);

    New way:

    player.sendMessage(grabMessagesValue.type("mine", "tp_decline").replaceAll("&", "§"));
     */

    static File file = new File(plugin.getDataFolder(), "Messages.yml");

    static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static Boolean createFile() throws IOException {
        

        createGlobalMessages();
        createAdmin();
        createGangs();
        createRankup();
        createMessage();
        createMine();
        createMultiplier();
        createToken();
        createEnchants();
        createTransactions();
        createRebootMessages();
        createRankupMessages();
        return true;
    }

    public static void createGlobalMessages() throws IOException {
        cfg.set("global.never_joined_player", "&c&lPrison&8&l-&c&lCore &8&l» &4Player &cPLAYER &4has never Joined the Server!");
        cfg.set("global.no_permission", "&c&lPrison&8&l-&c&lCore &8&l» &4No permission! &c - PERMISSION"); //PERMISSION displays permission needed for command. Useful for lapping permissions.
        cfg.set("global.no_player_name", "&c&lPrison&8&l-&c&lCore &8&l» &4Please Enter Player Name!");
        cfg.set("global.unknown_player", "&c&lPrison&8&l-&c&lCore &8&l» &4Player &cPLAYER &4is Offline!");
        
        cfg.save(file);
    }

    public static void createAdmin() throws IOException {
        cfg.set("admin.delete_gangs", "&c&lPrison&8&l-&c&lCore &8&l» &cDeleting all gangs! This may take a while!");
        cfg.set("admin.delete_gangs_complete", "&c&lPrison&8&l-&c&lCore &8&l» &4Deleted all gangs!");
        cfg.set("admin.delete_gangs_incomplete", "&c&lPrison&8&l-&c&lCore &8&l» &4Could not delete all gangs!");
        cfg.set("admin.give_pickaxe", "&c&lPrison&8&l-&c&lCore &8&l» &aYou gave &fPLAYER &aa pickaxe!");
        cfg.set("admin.info_gangs_deleted", "&c&lPrison&8&l-&c&lCore &8&l» &4This gang has been deleted!");
        cfg.set("admin.info_gangs_incomplete", "&c&lPrison&8&l-&c&lCore &8&l» &4Could not display info for that gang!");
        cfg.set("admin.info_gangs_name", "&c&lPrison&8&l-&c&lCore &8&l» &fPLAYER's &agang name: &fGANGNAME");
        cfg.set("admin.list_gangs_incomplete", "&c&lPrison&8&l-&c&lCore &8&l» &4Could not list all gangs!");
        cfg.set("admin.no_args", "&c&lPrison&8&l-&c&lCore &8&l» &cPlease enter args (Use tab)! - &4INFO");
        cfg.set("admin.no_feature", "&c&lPrison&8&l-&c&lCore &8&l» &cPlease enter a feature!");
        cfg.set("admin.unknown_feature", "&c&lPrison&8&l-&c&lCore &8&l» &4Unknown feature!");
        cfg.set("admin.schem_no_args", "&c&lPrison&8&l-&c&lCore &8&l» &4Please enter Schematic Name!");
        cfg.set("admin.updated", "&c&lPrison&8&l-&c&lCore &8&l» &aYou have Updated the configs!");
        cfg.set("admin.unknown_world", "&c&lPrison&8&l-&c&lCore &8&l» &cWorld &fWORLD &cdoes not exist!");

        cfg.save(file);
    }

    public static void createRankup() throws IOException {
        //NEXTRANKNAME = next rankup name
        //RANKPRICE = ranks price
        //PLAYERMINUSRANK = Player_Balance - RANKPRICE

        cfg.set("rankup.accept", "&eYou ranked up to rank &fNEXTRANKNAME &efor &a$&fRANKPRICE");
        cfg.set("rankup.decline", "&cYou do not have enough money to rankup to &fNEXTRANKNAME&c! You need &a$&fPLAYERMINUSRANK &cmore!");

        cfg.save(file);
    }

    public static void createGangs() throws IOException {
        //GANGNAME - Gang Name
        //IGN - original player
        //IGN2 - second player
        //INVITENAME - Gang invite name
        
        cfg.set("gang.chat_enable", "&c&lPrison&8&l-&c&lCore &8&l» &aGang Chat Enabled!&f Use &a# &fat the start of your message to use gang chat!");
        cfg.set("gang.chat_disable", "&c&lPrison&8&l-&c&lCore &8&l» &cGang Chat Disabled!");
        cfg.set("gang.chat_no_args", "&c&lPrison&8&l-&c&lCore &8&l» &fUse &a# &fat the start of your message to use gang chat!");
        cfg.set("gang.chat_no_args_disabled", "&c&lPrison&8&l-&c&lCore &8&l» &cGang Chat is Disabled! Use &a/gang chat toggle &cto enable it!");
        cfg.set("gang.created", "&c&lPrison&8&l-&c&lCore &8&l» &aGang &fcreated&a! &f - &6GANGNAME");
        cfg.set("gang.disband_deleted", "&c&lPrison&8&l-&c&lCore &8&l» &aGang &fDeleted&a!");
        cfg.set("gang.disband_not_leader", "&c&lPrison&8&l-&c&lCore &8&l» &cYou are not the &fLeader&c!");
        cfg.set("gang.disband_player", "&c&lPrison&8&l-&c&lCore &8&l» &cGang has been &fDisbanded&c!");
        cfg.set("gang.exists", "&c&lPrison&8&l-&c&lCore &8&l» &cPlease enter a different &fname&c! &4Gang already &fexists&4!");
        cfg.set("gang.no_name", "&c&lPrison&8&l-&c&lCore &8&l» &cPlease enter a &fname&c!");
        cfg.set("gang.gang_found", "&c&lPrison&8&l-&c&lCore &8&l» &aYou are already in a &egang&a!");
        cfg.set("gang.gang_not_found", "&c&lPrison&8&l-&c&lCore &8&l» &4Please create a &egang&4!");
        cfg.set("gang.invite_no_name", "&c&lPrison&8&l-&c&lCore &8&l» &cPlease enter an &fIGN&c!&f --> &a/gang invite <name>");
        cfg.set("gang.invite_offline", "&c&lPrison&8&l-&c&lCore &8&l» &cIGN2 is offline");
        cfg.set("gang.invite_already_in_gang", "&c&lPrison&8&l-&c&lCore &8&l» &cIGN2 is already in a gang");
        cfg.set("gang.invite_already_sent", "&c&lPrison&8&l-&c&lCore &8&l» &cYou already invited &fIGN2 &cto your gang");
        cfg.set("gang.invite_sent", "&c&lPrison&8&l-&c&lCore &8&l» &aYou sent a gang invite to &fIGN2");
        cfg.set("gang.invite_join", "&c&lPrison&8&l-&c&lCore &8&l» &aYou joined the gang &fGANGNAME");
        cfg.set("gang.invite_receive", "&c&lPrison&8&l-&c&lCore &8&l» &aYou received a gang invite from &fIGN");
        cfg.set("gang.invite_receive_command", "&c&lPrison&8&l-&c&lCore &8&l» &aTo Join, Use Command &c/&agang join GANGNAME");
        cfg.set("gang.invite_show", "&c&lPrison&8&l-&c&lCore &8&l» &cInvites: &fINVITENAME");
        cfg.set("gang.invite_no_gang_name", "&c&lPrison&8&l-&c&lCore &8&l» &cPlease enter the &fgang &cname!");
        cfg.set("gang.invite_no_gang_found", "&c&lPrison&8&l-&c&lCore &8&l» &cGang &fGANGNAME &cnot found.");
        cfg.set("gang.max_members", "&c&lPrison&8&l-&c&lCore &8&l» &cYou have reached the max amount of &fmembers &cfor your gang!");
        cfg.set("gang.kick_no_name", "&c&lPrison&8&l-&c&lCore &8&l» &cPlease enter a name!");
        cfg.set("gang.kick_not_leader", "&c&lPrison&8&l-&c&lCore &8&l» &cYou are not the &fLeader&c!");
        cfg.set("gang.kick_unavailable", "&c&lPrison&8&l-&c&lCore &8&l» &cYou cannot kick this user!");
        cfg.set("gang.kick_success", "&c&lPrison&8&l-&c&lCore &8&l» &aYou kicked this user!");

        cfg.save(file);
    }

    public static void createMessage() throws IOException {
        cfg.set("commands.gamemode", "&c&lPrison&8&l-&c&lCore &8&l» &aGamemode changed to &fGAMEMODE");
        cfg.set("commands.gamemode_args", "&c&lPrison&8&l-&c&lCore &8&l» &aGamemode changed to &fGAMEMODE&a for &fNAME");
        cfg.set("commands.gamemode_player_offline", "&c&lPrison&8&l-&c&lCore &8&l» &fNAME &cis Offline!");
        cfg.set("commands.night_vision_enable", "&c&lPrison&8&l-&c&lCore &8&l» &aNight Vision &fenabled");
        cfg.set("commands.night_vision_disable", "&c&lPrison&8&l-&c&lCore &8&l» &cNight Vision &fdisabled");
        cfg.set("commands.vanish_enable", "&c&lPrison&8&l-&c&lCore &8&l» &aVanish &fEnabled");
        cfg.set("commands.vanish_disable", "&c&lPrison&8&l-&c&lCore &8&l» &cVanish &fDisabled");

        cfg.save(file);
    }

    public static void createMine() throws IOException {
        cfg.set("mine.blocks", "&c&lPrison&8&l-&c&lCore &8&l» &aYou have &fBLOCKS &ablocks mined!");
        cfg.set("mine.upgrade_successful", "&c&lPrison&8&l-&c&lCore &8&l» &aMine upgraded successfully!");
        cfg.set("mine.upgrade_fail", "&c&lPrison&8&l-&c&lCore &8&l» &cYou do not meet the requirements!");
        cfg.set("mine.upgrade_fail_money_requirement", "&c&lPrison&8&l-&c&lCore &8&l» &cYou need &a$&fAMOUNT &cmore to Upgrade!");
        cfg.set("mine.upgrade_fail_exp_requirement", "&c&lPrison&8&l-&c&lCore &8&l» &cYou need &fAMOUNT &cmore &fexp &cto Upgrade!");
        cfg.set("mine.upgrade_fail_permission_requirement", "&c&lPrison&8&l-&c&lCore &8&l» &cYou need Rank &fNAME &cto Upgrade!");
        cfg.set("mine.upgrade_top_rank", "&c&lPrison&8&l-&c&lCore &8&l» &aYou have the top upgrade!");
        cfg.set("mine.reset", "&c&lPrison&8&l-&c&lCore &8&l» &6GANGNAME&a's P-Mine &ahas been successfully reset!");
        cfg.set("mine.generated", "&c&lPrison&8&l-&c&lCore &8&l» &aMine has been successfully created!");
        cfg.set("mine.tp", "&c&lPrison&8&l-&c&lCore &8&l» &aTeleported to your mine!");
        cfg.set("mine.tp_admin", "&c&lPrison&8&l-&c&lCore &8&l» &cTeleported to &fPLAYER's &cMine!");
        cfg.set("mine.tp_decline", "&c&lPrison&8&l-&c&lCore &8&l» &cYou cannot tp to your mine currently!");
        cfg.set("mine.reset_admin", "&c&lPrison&8&l-&c&lCore &8&l» &cReset &fPLAYER's &cMine!");
        
        ArrayList<String> totalSold = new ArrayList<>();
        
        totalSold.add("&eTotal Sold:");
        totalSold.add("");
        totalSold.add("&a$&fTOTAL");
        totalSold.add("&fMultiplier: &dMULTIx");
        
        cfg.set("mine.total_sold", totalSold);

        cfg.save(file);
    }

    public static void createMultiplier() throws IOException {
        cfg.set("multiplier.current", "&c&lPrison&8&l-&c&lCore &8&l» &aYou have a &fMULTIPLIER&ax Multiplier!");
        cfg.set("multiplier.give", "&c&lPrison&8&l-&c&lCore &8&l» &aYou were given a &fMULTIPLIER&ax Multiplier!");
        cfg.set("multiplier.set", "&c&lPrison&8&l-&c&lCore &8&l» &aYour multiplier has been set to a &fMULTIPLIER&ax Multiplier!");

        cfg.save(file);
    }

    public static void createToken() throws IOException {
        cfg.set("token.balance", "&c&lPrison&8&l-&c&lCore &8&l» &eYou have &9AMOUNT &etokens!");
        cfg.set("token.balance_admin", "&c&lPrison&8&l-&c&lCore &8&l» &fPLAYER &ehas &9AMOUNT &etokens!");
        cfg.set("token.cannot_afford", "&c&lPrison&8&l-&c&lCore &8&l» &4You cannot afford this! &eYou need &fPRICE &eTokens");
        cfg.set("token.give", "&c&lPrison&8&l-&c&lCore &8&l» &cYou gave &fPLAYER &9AMOUNT &ctokens!");
        cfg.set("token.given", "&c&lPrison&8&l-&c&lCore &8&l» &aYou were given &9AMOUNT &atokens!");
        cfg.set("token.paidsender", "&c&lPrison&8&l-&c&lCore &8&l» &cYou paid &fPLAYER &9AMOUNT &ctokens!");
        cfg.set("token.paidreceiver", "&c&lPrison&8&l-&c&lCore &8&l» &aYou were paid &9AMOUNT &atokens by &fPLAYER!");
        cfg.set("token.reset", "&c&lPrison&8&l-&c&lCore &8&l» &cYou reset &fPLAYER's &ctokens!");
        
        cfg.save(file);
    }

    public static void createEnchants() throws IOException {
        cfg.set("enchants.max_enchant", "&c&lPrison&8&l-&c&lCore &8&l» &eYou already have the maximum enchantment level!");
        cfg.set("enchants.equip_pickaxe", "&c&lPrison&8&l-&c&lCore &8&l» &cPlease equip your pickaxe!");
        cfg.set("enchants.charity_give", "&c&lPrison&8&l-&c&lCore &8&l» &6You were given &fTOKENS &eTokens &6And &f$&aMONEY &6with Charity!");
        
        cfg.save(file);
    }

    public static void createTransactions() throws IOException {
        cfg.set("transactions.bad_usage", "&c&lPrison&8&l-&c&lCore &8&l» &4Wrong usage!");
        cfg.set("transactions.no_ign", "&c&lPrison&8&l-&c&lCore &8&l» &4Please enter an IGN!");
        cfg.set("transactions.unknown_feature", "&c&lPrison&8&l-&c&lCore &8&l» &4Unknown Feature!");


        cfg.save(file);
    }
    
    public static void createRebootMessages() throws IOException {
        cfg.set("reboot.invalid", "&c&lPrison&8&l-&c&lCore &8&l» &4Please enter a number! Or type '/reboot' to reboot now!");
        cfg.set("reboot.not_rebooting", "&c&lPrison&8&l-&c&lCore &8&l» &4The server is not rebooting!");
        cfg.set("reboot.status", "&c&lPrison&8&l-&c&lCore &8&l» &aRebooting in &8TIME&a!");
        cfg.set("reboot.stop", "&c&lPrison&8&l-&c&lCore &8&l» &cYou have stopped the reboot!");
        
        cfg.save(file);
    }
    
    public static void createRankupMessages() throws IOException {
        cfg.set("rankup.rankup", "&c&lPrison&8&l-&c&lCore &8&l» &aYou Ranked to level &8LEVEL&a!");
        cfg.set("rankup.rankupmax", "&c&lPrison&8&l-&c&lCore &8&l» &aYou Ranked to level &8LEVEL&a, Gaining &8LEVEL &aLevels!");
        cfg.set("rankup.norankup", "&c&lPrison&8&l-&c&lCore &8&l» &4You cannot rankup! You need &f$&aMONEY &4More to rankup!");
        
        cfg.save(file);
    }
}

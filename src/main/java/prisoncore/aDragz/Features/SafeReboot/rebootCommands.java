package prisoncore.aDragz.Features.SafeReboot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import prisoncore.aDragz.ConfigFiles.grabValue.grabMessagesValue;

public class rebootCommands implements Listener, CommandExecutor {
    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("reboot")) {
            if (sender.hasPermission("PrisonCore.Reboot")) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("status")) {
                        if (!sender.hasPermission("PrisonCore.Reboot.Status")) {
                            sender.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Reboot.Status"));
                            return false;
                        }
                        if (rebootCooldown.cooldownMap.containsKey("rebooting")) {
                            int minutes = 0;
                            Long secondsLeft = (rebootCooldown.cooldownMap.get("rebooting") / 1000) + rebootCooldown.cooldownLengthint - (System.currentTimeMillis() / 1000);
                            while (secondsLeft >= 60) {
                                secondsLeft -= 60;
                                minutes++;
                            }
                            
                            if (minutes != 0) {
                                sender.sendMessage(grabMessagesValue.type("reboot", "status").replaceAll("&", "§").replaceAll("TIME", minutes + " Minutes and " + secondsLeft + " Seconds"));
                            } else {
                                sender.sendMessage(grabMessagesValue.type("reboot", "status").replaceAll("&", "§").replaceAll("TIME", secondsLeft + " Seconds"));
                            }
                        } else {
                            sender.sendMessage(grabMessagesValue.type("reboot", "not_rebooting").replaceAll("&", "§"));
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("stop")) {
                        rebootCooldown.cooldownMap.clear();
                        sender.sendMessage(grabMessagesValue.type("reboot", "stop").replaceAll("&", "§"));
                        return true;
                    }
                    try {
                        rebootCooldown.set(Integer.parseInt(args[0]), sender);
                    } catch (NumberFormatException | InterruptedException e) {
                        sender.sendMessage(grabMessagesValue.type("reboot", "invalid").replaceAll("&", "§"));
                        return false;
                    }
                } else {
                    try {
                        rebootCooldown.set(0, sender);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                sender.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Reboot"));
            }
        } else {
            sender.sendMessage(grabMessagesValue.type("global", "no_permission").replaceAll("&", "§").replaceAll("PERMISSION", "PrisonCore.Reboot"));
        }
        return false;
    }
}

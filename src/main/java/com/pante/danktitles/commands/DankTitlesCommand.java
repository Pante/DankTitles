/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles.commands;

import static org.bukkit.Bukkit.getPluginManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Pante
 */
public class DankTitlesCommand extends CommandHandler {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (!checkPlayerPermissions(sender, "dantitles.debug")) {
            return true;
        }
        if (!checkArgumentLength(sender,arguments, 0, 1)){
            return true;
        }
        if (arguments.length == 0 || arguments[0].equalsIgnoreCase("Version")) {
            sender.sendMessage(ChatColor.GOLD + "DankTitles version: " + getPluginManager().getPlugin("DankTitles").getDescription().getVersion());
            return true;
        }
        if (arguments[0].equalsIgnoreCase("Reload")) {
            sender.sendMessage(ChatColor.GOLD + "Reloading DankTitles");
            // TODO: Reload logic here
            return true;
        }
        return true;
    }
    
}

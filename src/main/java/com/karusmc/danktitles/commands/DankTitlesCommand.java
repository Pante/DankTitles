/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles.commands;

import static org.bukkit.Bukkit.getPluginManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Pante
 */
public class DankTitlesCommand extends CommandBase{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        // Methods specified in CommandAPI and implemented in CommandBase
        if (!checkPlayerPermission(sender, "danktitles.debug")) return true;
        if (!checkArgumentLength(sender, args, 0, 1)) return true;
        
        
        if (args.length == 0 || args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(ChatColor.GOLD + "DankTitles version: " + getPluginManager().getPlugin("DankTitles").getDescription().getVersion());
            return true;
        }
        else if (args[0].equalsIgnoreCase("reload")) {
            //TODO: Reloading logic for DankTitlesCommand
            return true;
        }
        else {
            return (argumentIsInvalid(sender));
        }
        
    }
    
}

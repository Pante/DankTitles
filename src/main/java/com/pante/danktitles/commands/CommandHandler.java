/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles.commands;

import com.sun.xml.internal.stream.events.AttributeImpl;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Pante
 */
public abstract class CommandHandler implements CommandExecutor {
    // Method that checks if a sender is a player and has the specified permission
    protected boolean checkPlayerPermissions(CommandSender sender, String permission) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission(permission)){
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return false;
            }
        }
        return true;
    }
    // Method that checks if the number of arguments specified is valid or not
    protected boolean checkArgumentLength(CommandSender sender, String[] args, int minLength, int maxLength){
        if (args.length < minLength || args.length > maxLength) {
            sender.sendMessage(ChatColor.RED + "Invalid number of arguments specified: " + args.length);
            return false;
        }
        return true;
    }
}

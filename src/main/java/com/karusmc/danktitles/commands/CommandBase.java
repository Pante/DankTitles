/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Pante
 */
public abstract class CommandBase implements CommandExecutor, CommandAPI {
    
    @Override
    
    // Implementation of checkPlayerPermission in CommandAPI
    public boolean checkPlayerPermission(CommandSender sender, String permission) {
 
        if (sender instanceof Player) {
            Player player = (Player) sender;
            
            if (!player.hasPermission(permission)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return false;
            }  
            
        } 
        
        return true;
    }
    
    @Override
    
    // Implementation of checkArgumentLength
    public boolean checkArgumentLength(CommandSender sender, String args[], int minLength, int maxLength) {
       
        if (args.length < minLength || args.length > maxLength) {
            sender.sendMessage(ChatColor.RED + "Invalid number of arguments specified.");
            return false;
        }
        
        return true;
    }
    
    @Override
    
    // Implementation of argumentIsInvalid
    public boolean argumentIsInvalid(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "An invalid argument(s) was specified.");
        return true;
    }
}

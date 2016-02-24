/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Pante
 */
public class TitleCommand extends CommandBase {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //TODO: Open menu shit here.
        return true;
    }
    
    @Override
    
    // Implementation of checkPlayerPermission so that it returns false if the sender is not a player
    // Implementation of checkPlayerPermission in CommandAPI
    public boolean checkPlayerPermission(CommandSender sender, String permission) {
 
        if (sender instanceof Player) {
            Player player = (Player) sender;
            
            if (!player.hasPermission(permission)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return false;
            }  
            
        }
        else {
            sender.sendMessage(ChatColor.RED + "This is a player only command");
        }
        
        return true;
    }
}

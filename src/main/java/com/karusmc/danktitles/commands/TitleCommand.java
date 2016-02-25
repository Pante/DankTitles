/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles.commands;

import com.karusmc.danktitles.menus.CategoryMenu;
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
        if (!checkPlayerPermission(sender, "danktitles.title")) return true;
        else {
            CategoryMenu menu = new CategoryMenu(1);
            Player player = (Player) sender;
            menu.display(player);
        }
        
        return true;
    }
    
    
    @Override
    
    // Implementation of checkPlayerPermission in CommandAPI so that it returns false if the sender is not a player
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

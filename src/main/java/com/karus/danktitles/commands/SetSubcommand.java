/*
 * Copyright (C) 2016 PanteLegacy @ karusmc.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.karus.danktitles.commands;

import com.karus.danktitles.DankTitles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class SetSubcommand implements Subcommand, CommandChecker {
    
    @Override
    
    // Subcommand force-sets a player's title back to group default
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from BaseSubcommand, CommandChecker
        if (!checkLength(sender, args, 2, 4)) return;
        if (!checkSender(sender, "danktitles.set")) return;
        
        
        // Checks if the player is valid
        if (Bukkit.getPlayer(args[1]) == null) {
            sender.sendMessage(ChatColor.RED + "No such player exists!");
            return;
        }
        
        Player player = Bukkit.getPlayer(args[1]);
        
        
        // <------ Resets the title if no arguments was specified ------>
        if (args.length == 2) {
            DankTitles.chat.setPlayerPrefix(player, DankTitles.chat
                    .getGroupPrefix(player.getWorld(), DankTitles.permission.getPrimaryGroup(player)));
            
            sender.sendMessage(ChatColor.GOLD + args[1] + "'s title has been reset!");
            
            if (sender instanceof Player && player.isOnline()) {
                Player playerSender = (Player) sender;
                Player playerReciever = (Player) player;
                if (!playerSender.getName().equals(player.getName())) {
                    playerReciever.sendMessage(ChatColor.GOLD + sender.getName() + " has reset your title!");
                }
            } 
            
            return;
        } 
        
        
        // <------ Sets the title according to the arguments specified ------>
        
        
        // Checks if the respective files contain the respective paths
        String titlePath = ("categories." + args[2] + ".titles." + args[3]);
        
        if (!fileHandler.getTitles().contains(titlePath)) {
            sender.sendMessage(ChatColor.RED + "No such title exists!");
            return;
        }
        
        String playerPath = ("players." + player.getUniqueId() + ".titles." + args[2]);
        
        DankTitles.chat.setPlayerPrefix(player, fileHandler.getTitles().getString(titlePath + ".display"));
        
        sender.sendMessage(ChatColor.GOLD + args[1] + "'s title has been set to: " + args[3]);
        
        // Informs the target of the change if online
        if (sender instanceof Player && player.isOnline()) {
                Player playerSender = (Player) sender;
                Player playerReciever = (Player) player;
                if (!playerSender.getName().equals(player.getName())) {
                    playerReciever.sendMessage(ChatColor.GOLD + sender.getName() + "has set your title to: " + args[3]);
                }
            } 
    }
    
}

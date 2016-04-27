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
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class RemoveSubcommand implements Subcommand, CommandChecker {
    
    // Inherities FileHandler fileHandler from BaseSubcommand
    
    @Override
    
    // Subcommand removes the title from a player
    public void execute(CommandSender sender, String[] args) {

        // Methods inherited from BaseSubcommand, CommandChecker
        if (!checkLength(sender, args, 4, 4)) return;
        if (!checkSender(sender, "danktitles.remove")) return;
        
        
        // Checks if the player specified is invalid and has the title
        if (Bukkit.getOfflinePlayer(args[1]) == null) {
            sender.sendMessage(ChatColor.RED + "No such player exists!");
            return;
        }
        
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
        
        
        // Checks if the file contain the respective paths
        String playerPath = ("players." + player.getUniqueId() + ".titles." + args[2]);
        
        if (!checkContain(fileHandler.getPlayers().getStringList(playerPath), args[3])) {
            sender.sendMessage(ChatColor.RED + args[1] + " does not have title: " + args[3]);
            return;
        }
        
        // Checks if the player's name has changed and updates it if it did
        if (!fileHandler.getPlayers().getString("players." + player.getUniqueId() + ".name").equals(player.getName())) {
            fileHandler.getPlayers().set("players." + player.getUniqueId() + ".name", player.getName());
        }
        
        
        
        // Removes the title
        List<String> tempList = fileHandler.getPlayers().getStringList(playerPath);
        tempList.remove(args[2]);
        
        fileHandler.getPlayers().set(playerPath, tempList);
        
        sender.sendMessage(ChatColor.GOLD + args[1] + " has been stripped of title: " + args[3]);
        
        if (sender instanceof Player && player.isOnline()) {
            Player playerSender = (Player) sender;
            Player playerReciever = (Player) player;
            if (!playerSender.getName().equals(player.getName())) {
                playerReciever.sendMessage(ChatColor.GOLD + sender.getName() + " has removed title: " + args[3] + " from you!");
            }
        } 
        
        try {
            DankTitles.instance.dataHandler.save();
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Failed to save changes to disk!");
        }
        
    }
}

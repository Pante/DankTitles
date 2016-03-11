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

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class AddSubcommand extends BaseSubcommand {
    
    // Inherities FileHandler fileHandler from BaseSubcommand
    
    @Override
    
    // Implementation of method inheritied from BaseSubcommand, Subcommand
    // Subcommand gives the specified title to the player
    public void execute(CommandSender sender, String[] args) {
        
        
        // Methods inheritied from BaseSubcommand, CommandChecker
        if (!checkArgumentNumber(sender, args, 4, 4)) return;
        if (!checkPlayer(sender, "danktitles.add")) return;
        
        
        // Checks if the player is valid
        if (checkNull(Bukkit.getOfflinePlayer(args[1]))) {
            sender.sendMessage(ChatColor.RED + "No such player exists!");
            return;
        }
        
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
        
        
        // Checks if the respective files contain the respective paths
        String titlePath = ("categories." + args[2] + ".titles." + args[3]);
        
        if (!fileHandler.getTitles().contains(titlePath)) {
            sender.sendMessage(ChatColor.RED + "No such title exists!");
            return;
        }
        
        String playerPath = ("players." + player.getUniqueId() + ".titles." + args[2]);
        
        if (checkContain(fileHandler.getPlayers().getStringList(playerPath), args[3])) {
            sender.sendMessage(ChatColor.RED + args[1] + " already has title: " + args[3]);
            return;
        }
        
        
        // Checks if the player's name has changed and updates it if it did
        if (!fileHandler.getPlayers().getString("players." + player.getUniqueId() + ".name").equals(player.getName())) {
            fileHandler.getPlayers().set("players." + player.getUniqueId() + ".name", player.getName());
        }
        
        // Adds the title
        List<String> tempList = fileHandler.getPlayers().getStringList(playerPath);
        tempList.add(args[3]);
        
        fileHandler.getPlayers().set(playerPath, tempList);
            
        sender.sendMessage(ChatColor.GOLD + args[1] + " has been given the title: " + args[3]);
        
        
        // Attempts to save to disk
        try {
            DankTitles.instance.getDataHandler().save();
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Failed to save changes to disk!");
        }
        
    }
}

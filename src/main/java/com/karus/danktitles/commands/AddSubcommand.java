/*
 * Copyright (C) 2016 PanteLegacy @ karusmc.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.karus.danktitles.commands;

import com.karus.danktitles.DankTitles;
import com.karus.danktitles.backend.FileHandler;
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
class AddSubcommand extends BaseSubcommand {
    
    @Override
    
    // Implementation of method inheritied from BaseSubcommand, Subcommand
    // Class responsible for giving a player a title
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from BaseSubcommand, CommandUtility
        if (!checkArgument(sender, args, 4, 4)) return;
        if (!checkPlayer(sender, "danktitles.add")) return;
        
        
        // Checks if the players and titles specified are valid
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[3]);
        String path = ("categories." + args[1] + ".titles.");
        
        
        if (player == null || !(FileHandler.getInstance().getTitles().contains(path + args[2]))) {
            
            DankTitles.getInstance().getLogger().info("Value 1" + (player == null));
            DankTitles.getInstance().getLogger().info("Value 2" + !FileHandler.getInstance().getTitles().contains(path + args[2]));
            
            sender.sendMessage(ChatColor.RED + "Invalid argument specified.");
            
            return;
        }
        
        
        // Adding the title and player's name to the players.yml file if the player does not have the title
        if (FileHandler.getInstance().getPlayers().getStringList("players." + player.getUniqueId().toString() + ".titles." + args[1]).contains(args[2])) {
            sender.sendMessage(ChatColor.RED + "The player already has title: " + args[2]);
            return;
        }
        
        
        FileHandler.getInstance().getPlayers().set("players." + player.getUniqueId().toString() + ".name", player.getName());
        
        List<String> tempList = FileHandler.getInstance().getPlayers().getStringList("players." + player.getUniqueId().toString() + ".titles." + args[1]);
        tempList.add(args[2]);
        
        FileHandler.getInstance().getPlayers().set("players." + player.getUniqueId().toString() + ".titles." + args[1], tempList);
            
        sender.sendMessage(ChatColor.GOLD + "Title: " + args[2] + " has been given to player: " + player.getName());
        
        DankTitles.getInstance().getDataHandler().save();
        
    }
    
}

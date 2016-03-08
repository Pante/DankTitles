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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
// Class resp
class RemoveSubcommand extends BaseSubcommand {
    
    @Override
    
    // Implementation of method inheritied from BaseSubcommand and CommandUtility
    // Used to remove a title from a player
    public void execute(CommandSender sender, String[] args) {

        // Methods inherited from BaseSubcommand, CommandUtility
        if (!checkArgument(sender, args, 4, 4)) return;
        if (!checkPlayer(sender, "danktitles.remove")) return;
        
        
        // Checks if the player specified is invalid and has the title
        Player player = Bukkit.getPlayer(args[3]);
        
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player specified.");
            return;
        }
        
        
        String path = ("players." + player.getUniqueId() + ".titles." + args[1]);
        
        
        if (!FileHandler.getInstance().getPlayers().getStringList(path).contains(args[2])) {
            sender.sendMessage(ChatColor.RED + "Player: " + player.getName() + "\n" + player.getUniqueId() 
                    + "\ndoes not have title: " + args[2]);
            return;
        }
        
        
        if (!player.getName().equalsIgnoreCase(FileHandler.getInstance().getPlayers()
                .getString("players." + player.getUniqueId().toString() + ".name"))) {
            
            FileHandler.getInstance().getPlayers().set("players." + player.getUniqueId().toString() + ".name", player.getName());
            
        }
        
        
        // Removing the title from players.yml
        FileHandler.getInstance().getPlayers().getStringList(path).remove(args[2]);
        sender.sendMessage(ChatColor.GOLD + "Title: " + args[2] + " has been removed from player: " + args[3]);
        DankTitles.getInstance().getDataHandler().save();
        
    }
    
}

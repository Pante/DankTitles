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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        Player player = Bukkit.getPlayer(args[3]);
        String path = ("categories" + args[1] + ".titles." + args[2]);
        
        if (player == null || (!FileHandler.getInstance().getTitles().contains(path))) {
            sender.sendMessage(ChatColor.RED + "Invalid argument specified.");
            return;
        }
        
        
        // Adding the title and player's name to the players.yml file
        FileHandler.getInstance().getPlayers().set("players." + player.getUniqueId().toString() + ".name", player.getName());
        FileHandler.getInstance().getPlayers().set("players." + player.getUniqueId().toString() + ".titles." + args[1], args[2]);
        
        DankTitles.getInstance().getDataHandler().save();
        
    }
    
}

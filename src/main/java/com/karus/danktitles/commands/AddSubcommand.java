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

import com.karus.danktitles.io.FileHandler;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class AddSubcommand implements Subcommand, CommandChecker {

    @Override
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from CommandChecker
        if (!checkLength(sender, args, 4, 4)) return;
        if (!checkSender(sender, "danktitles.add")) return;
        
        if (FileHandler.getTitles(args[1]) == null || !FileHandler.getTitles(args[1]).containsKey(args[2])) {
            sender.sendMessage(ChatColor.RED + "No such title exists!");
            return;
        }
        
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[3]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "No such player exists!");
            return;
        }
        
        if (FileHandler.getPlayers().get(player.getUniqueId()).get(args[1]) != null && 
                FileHandler.getPlayers().get(player.getUniqueId()).get(args[1]).contains(args[2])) {
            sender.sendMessage(ChatColor.RED + "Player already has the title!");
            return;
        }
        
        FileHandler.getPlayers().get(player.getUniqueId()).get(args[1]).add(args[2]);
        sender.sendMessage(ChatColor.GOLD + args[1] + " has been given the title: " + args[2]);
        
        if (player.isOnline()) {
            Player reciever = (Player) player;
            if (!sender.getName().equals(player.getName())) {
                reciever.sendMessage(ChatColor.GOLD + sender.getName() + " has given title: " + args[2] + " to you!");
            }
        } 
        
    }
    
}

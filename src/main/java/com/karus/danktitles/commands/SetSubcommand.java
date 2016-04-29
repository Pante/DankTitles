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
import com.karus.danktitles.io.FileHandler;
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
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from BaseSubcommand, CommandChecker
        if (!checkLength(sender, args, 2, 4)) return;
        if (!checkSender(sender, "danktitles.set")) return;
        
        
        Player player = Bukkit.getPlayer(args[3]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "No such player exists!");
            return;
        }
        
        if (args.length == 2) {
            DankTitles.chat.setPlayerPrefix(player, DankTitles.chat
                    .getGroupPrefix(player.getWorld(), DankTitles.permission.getPrimaryGroup(player)));
            
            sender.sendMessage(ChatColor.GOLD + "You have reset " + args[1] + "'s title.");
            
            if (!sender.getName().equals(player.getName())) {
                player.sendMessage(ChatColor.GOLD + sender.getName() + " has reset your title!");
            } 
        } else if (args.length == 4) {
            
            if (FileHandler.getTitles(args[1]) == null || !FileHandler.getTitles(args[1]).containsKey(args[2])) {
                sender.sendMessage(ChatColor.RED + "No such title exists!");
                return;
            }
            
            DankTitles.chat.setPlayerPrefix(player, FileHandler.getTitles(args[1]).get(args[2]).getItemMeta().getDisplayName());
            
            if (player.isOnline() && !sender.getName().equals(player.getName())) {
                player.sendMessage(ChatColor.GOLD + sender.getName() + "has set your title to: " + args[3]);
            }
        }
    }
    
}

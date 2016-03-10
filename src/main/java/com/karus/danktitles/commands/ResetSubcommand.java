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
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class ResetSubcommand extends BaseSubcommand {
    
    
    @Override
    
    // Implementaiton of method inheritied from BaseSubcommand, Subcommand
    // Used to reset a title back to the group default
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from BaseSubcommand, CommandUtility
        if (!checkArgument(sender, args, 1, 1)) return;
        if (!checkPlayer(sender, "danktitles.reset")) return;
        
        Player player = (Player) sender;
        DankTitles.chat.setPlayerPrefix(player, DankTitles.chat.getGroupPrefix(player.getWorld(), DankTitles.permission.getPrimaryGroup(player)));
        sender.sendMessage(ChatColor.GOLD + "Title reset.");
        
    }
    
    // Implementation of checkPlayer() method inheritied from BaseSubcommand, CommandUtility
    // Overriden so that the method will return false if the sender is not a player
    public boolean checkPlayer(CommandSender sender, String permission) {
        
        // Returns FALSE instead of TRUE
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This is a player only command!");
            return false;
        }
        
        Player player = (Player) sender;
        
        if (player.hasPermission(permission)) return true;
        else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }
        
    }
}

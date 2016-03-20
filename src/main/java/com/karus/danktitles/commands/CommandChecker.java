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

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public interface CommandChecker {
    
    // Method that checks if the sender is a player and if he has the needed permissions
    public default boolean checkPlayer(CommandSender sender, String permission) {

        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (player.hasPermission(permission)) return true;
        else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }

    }


    // Method that checks if the number of arguments specified are valid
    public default boolean checkArgumentNumber(CommandSender sender, String[] args, int min, int max) {

        if (args.length < min || args.length > max) {
            sender.sendMessage(ChatColor.RED + "Invalid number of arguments specified.");
            return false;
        }
        else return true;

    }
    
}

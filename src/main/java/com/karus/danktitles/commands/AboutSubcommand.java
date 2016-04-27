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

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class AboutSubcommand implements Subcommand, CommandChecker {
    
    @Override
    
    // Subcommand returns general information about the plugin
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from CommandChecker
        if (!checkLength(sender, args, 1, 1)) return;
        if (!checkSender(sender, "danktitles.about")) return;
        
        
        sender.sendMessage(ChatColor.GOLD 
                + "DankTitles version: " + DankTitles.instance.getDescription().getVersion() + "\n"
                + DankTitles.instance.getDescription().getDescription() + "\n"
                + "Author(s): PanteLegacy @ karusmc.com\n"
                + "Source code & development resources: https://github.com/Pante/DankTitles \n"
                + ChatColor.GOLD + "Type " + ChatColor.RED + "\"/dt help\"" + ChatColor.GOLD + " to view a list of commands.");
        
    }
    
}

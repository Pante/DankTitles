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
import com.karus.danktitles.io.Output;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class SaveSubcommand implements Subcommand, CommandChecker {

    @Override
    // Subcommand force-saves the changes to disk
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from CommandChecker
        if (!checkLength(sender, args, 1, 2)) return;
        if (!checkSender(sender, "danktitles.save")) return;
        
        Output<String, Exception> output = (out, exception) -> {
            if (exception == null) {
                sender.sendMessage(ChatColor.GOLD + out);
            } else {
                sender.sendMessage(ChatColor.RED + out);
            }
        };
        
        if (args.length == 1 || args[1].equals("all")) {
            FileHandler.saveConfig(output);
            FileHandler.saveTitles(output);
            FileHandler.savePlayers(output);
            return;
        }
        
        switch (args[1].toLowerCase()) {
            
            case "config":
                FileHandler.saveConfig(output);
                break;
            
            case "players":
                FileHandler.savePlayers(output);
                break;
                
            case "titles":
                FileHandler.saveTitles(output);
                break;
                
            default:
                sender.sendMessage(ChatColor.RED + "Invalid argument.");
                break;
        }
        
    }
    
}

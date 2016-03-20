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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class MainCommand implements CommandExecutor, CommandChecker {
    
    // <------ Fields ------>
    
    // Interface that all subcommands implement
    private Subcommand subcommand;
    
     @Override
    
    // Implementation of onCommand() inheritied from CommandExectuor
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (args.length == 0) {
            subcommand = new MenuSubcommand();
            subcommand.execute(sender, args);
            return true;
        }
        
        
        // Methods setSubcommand(), getSubcommand() and execute() inheritied from BaseCommand and Subcommand respectively
        // subcommand has a type of Subcommand (interface)
            
        switch(args[0].toLowerCase()) {
            
            case "about":
                subcommand = new AboutSubcommand();
                break;

            case "help":
                subcommand = new HelpSubcommand();
                break;

            case "reload":
                subcommand = new ReloadSubcommand();
                break;
            
            case "save":
                subcommand = new SaveSubcommand();
                break;
                
            case "set":
                subcommand = new SetSubcommand();
                break;

            case "add":
                subcommand = new AddSubcommand();
                break;

            case "remove":
                subcommand = new RemoveSubcommand();
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown argument: " + args[0] + " specified.");
                return true;
                
        }
        
        subcommand.execute(sender, args);
        
        return true;
        
    }
    
}

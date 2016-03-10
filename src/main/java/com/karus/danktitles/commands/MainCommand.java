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
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class MainCommand extends BaseCommand {
    
    @Override
    
    // Implementation of onCommand() inheritied from BaseCommand, CommandExectuor
    // getSubcommand and setSubcommand inheritie from BaseCommand
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (args.length == 0) {
            setSubcommand(new MenuSubcommand());
            getSubcommand().execute(sender, args);
            return true;
        }
        
        
        // Methods setSubcommand(), getSubcommand() and execute() inheritied from BaseCommand and Subcommand respectively
        // subcommand has a type of Subcommand (interface)
            
        switch(args[0].toLowerCase()) {
            
            case "about":
                setSubcommand(new AboutSubcommand());
                break;

            case "help":
                setSubcommand(new HelpSubcommand());
                break;

            case "reload":
                setSubcommand(new ReloadSubcommand());
                break;
            
            case "save":
                setSubcommand(new SaveSubcommand());
                break;
                
            case "reset":
                setSubcommand(new ResetSubcommand());
                break;

            case "add":
                setSubcommand(new AddSubcommand());
                break;

            case "remove":
                setSubcommand(new RemoveSubcommand());
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Invalid argument specified");
                return true;
                
        }
        
        getSubcommand().execute(sender, args);
        
        return true;
        
    }
}
    

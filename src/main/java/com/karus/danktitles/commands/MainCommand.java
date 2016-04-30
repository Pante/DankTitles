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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class MainCommand implements CommandExecutor {
    
    // Fields
    private HashMap<String, Subcommand> subcommands = new HashMap<>();
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || subcommands.get(args[0]) == null) {
            sender.sendMessage(ChatColor.RED + "Invalid argument. Type \"/dt help\" for a list of commands.");     
        } else {
            subcommands.get(args[0]).execute(sender, args);
        }
        return true;
    }
    
    
    public void registerSubcommand(String fullName, Subcommand subcommand) {
        new ArrayList<>((List<String>) DankTitles.instance.getDescription()
            .getCommands().get(fullName).get("aliases"))
            .stream().forEach(alias -> {
                subcommands.put(alias, subcommand);
            });
    }
    
}

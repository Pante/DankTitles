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
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class ReloadSubcommand extends BaseSubcommand {
    
    @Override
    
    // Implementation of method inheritied from BaseSubcommand, Subcommand
    // Subcommand used to reload the plugin
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from BaseSubcommand, CommandChecker
        if (!checkArgumentNumber(sender, args, 1, 1)) return;
        if (!checkPlayer(sender, "danktitles.reload")) return;
        
        try {
            DankTitles.instance.dataHandler.load();
            DankTitles.instance.dataHandler.save();
            sender.sendMessage(ChatColor.GOLD + "Successfully reloaded DankTitles");
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Failed to save changes to disk!");
        }
        
    }
    
}

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
public class SaveSubcommand extends BaseSubcommand {

    @Override
    // Implementaiton of method inheritied from BaseSubcommand, Subcommand
    // Used to force save the plugin
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from CommandUtility
        if (!checkArgument(sender, args, 1, 1)) return;
        if (!checkPlayer(sender, "danktitles.save")) return;
        
        DankTitles.getInstance().getDataHandler().save();
        sender.sendMessage(ChatColor.GOLD + "DankTitles saved...");
    }
    
}
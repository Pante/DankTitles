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
import org.bukkit.command.CommandSender;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
class ReloadSubcommand extends BaseSubcommand {
    
    @Override
    
    // Implementation of method inheritied from BaseSubcommand, Subcommand
    // Used to reload the plugin
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from BaseSubcommand, CommandUtility
        if (!checkArgument(sender, args, 1, 1)) return;
        if (!checkPlayer(sender, "danktitles.reload")) return;
        
        DankTitles.getInstance().getDataHandler().load();
        sender.sendMessage(ChatColor.GOLD + "DankTitles reloaded...");
        
    }
    
}

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

import com.karus.danktitles.menus.CategoryMenu;
import com.karus.danktitles.menus.Menu;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class MenuSubcommand implements Subcommand, CommandChecker {
    
    private Menu menu;
    
    @Override
    
    // Implementation of execute() method inheritied from BaseSubcommand, Subcommand
    // Subcommand used to open up the menu for title selection
    public void execute(CommandSender sender, String[] args) {
        
        
        // Methods inheritied from BaseSubcommand, CommandChecker
        if (!checkLength(sender, args, 0, 0)) return;
        if (!checkPlayer(sender, "danktitles.titles")) return;
        Player player = (Player) sender;
        
        setMenu(new CategoryMenu(player));
        
        menu.display(player);
        
    }
    
    @Override
    
    // Implementation of checkPlayer() method inheritied from BaseSubcommand, CommandChecker
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
    
    
    // Setter method for setting the menu
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    
}

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
package com.karus.danktitles.listeners;

import com.karus.danktitles.DankTitles;
import com.karus.danktitles.PreconditionChecker;
import com.karus.danktitles.menus.CategoryMenu;
import com.karus.danktitles.menus.TitlesMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class TitlesMenuListener implements Listener, PreconditionChecker {
    
    TitlesMenu menu;
    
    @EventHandler
    
    // Method handles clicking
    public void onClick(InventoryClickEvent event) {
        
        Player player = (Player) event.getWhoClicked();
        
        if (event.getInventory().getTitle().contains("Titles - Category") && TitlesMenu.getMenu().containsKey(player.getUniqueId())) {
            menu = TitlesMenu.getMenu().get(player.getUniqueId());
        }
        else return;
       
        
        event.setCancelled(true);
        
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null ||  clicked.getType() == Material.AIR) return;
        
        String name = clicked.getItemMeta().getDisplayName();
        // Blame java for not allowing variables in a switch statment >:(
        // Checks if the item matches
        
        if (name.equals(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.icons.back.display")))) {
            
            if ((menu.page - 1) > 0) {
                
                TitlesMenu previousMenu = new TitlesMenu(menu.page + 1, menu.pageTotal, 
                        menu.pageSize, menu.dynamicSize, menu.titles, menu.category);
                
                previousMenu.display((Player) event.getWhoClicked());     
            }
            
            else if (menu.config.getString("menus.messages.first-page") == null) {
                player.sendMessage(ChatColor.RED + "You're already on the first page!");
            }
            
            else {
                player.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.messages.first-page"))));
            }
            
        }
        
        else if (name.equals(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.icons.reset.display")))) {
            DankTitles.chat.setPlayerPrefix(player, DankTitles.chat.getGroupPrefix(player.getWorld(), DankTitles.permission.getPrimaryGroup(player)));
            player.sendMessage(ChatColor.GOLD + "Your title has been reset to your group default!");
            player.closeInventory();
        }
        
        else if (name.equals(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.icons.menu.display")))) {
            CategoryMenu mainMenu = new CategoryMenu(player);
            mainMenu.display(player);
            
        }
        
        else if (name.equals(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.icons.next.display")))) {
            
            if ((menu.page + 1) <= menu.pageTotal) {
                
                TitlesMenu nextMenu = new TitlesMenu(menu.page + 1, menu.pageTotal,
                menu.pageSize, menu.dynamicSize, menu.titles, menu.category);
                
                nextMenu.display(player);
            }
            else if (menu.config.getString("menus.messages.last-page") == null ) {
                player.sendMessage(ChatColor.RED + "You are already on the last page!");                
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.messages.last-page")));
            }
            
        }
    
        else if (name.equals(ChatColor.RED + "Invalid display")) {
            if (menu.config.getString("menus.messages.invalid-title") == null) {
                player.sendMessage(ChatColor.RED + "Welp! This is embarrassing! You should not be seeing this, please contact the server administrator(s)!");
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.messages.invalid-title")));
            }
        }
                
        else {
            DankTitles.chat.setPlayerPrefix(player, clicked.getItemMeta().getDisplayName() + " ");
            player.sendMessage(ChatColor.GOLD + "Your title has been changed to: " + ChatColor.RESET + clicked.getItemMeta().getDisplayName());
            player.closeInventory();
        }
      
    }

}

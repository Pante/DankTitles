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
public class CategoryMenuListener implements Listener, PreconditionChecker {
    
    CategoryMenu menu;
    
    @EventHandler
    
    // Method handle onClick events
    public void onClick(InventoryClickEvent event) {
        
        Player player = (Player) event.getWhoClicked();
        
        if (event.getInventory().getTitle().contains("Titles - Categories") && CategoryMenu.getMenu().containsKey(player.getUniqueId())) {
            menu = CategoryMenu.getMenu().get(player.getUniqueId());
        }
        else return;
        
        event.setCancelled(true);
        
        ItemStack clicked = event.getCurrentItem();
        
        if (checkNull(clicked) ||  clicked.getType() == Material.AIR) return;
        
        String name = clicked.getItemMeta().getDisplayName();
        
        // Blame java for not allowing variables in a switch statment >:(
        // Checks if the item matches

        if (name.equals(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.icons.back.display")))) {
            
            if ((menu.page - 1) > 0) {
                
                CategoryMenu previousMenu = new CategoryMenu(menu.page + 1, menu.pageTotal, 
                        menu.pageSize, menu.dynamicSize, menu.categories);
                
                previousMenu.display((Player) event.getWhoClicked());
            }
            else if (checkNull(menu.config.getString("menus.messages.first-page"))) {
                player.sendMessage(ChatColor.RED + "You're already on the first page!");
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.messages.first-page")));
            }
            
        }
        
        else if (name.equals(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.icons.next.display")))) {
            
            if ((menu.page + 1) <= menu.pageTotal) {
                
                CategoryMenu nextMenu = new CategoryMenu(menu.page + 1, menu.pageTotal,
                menu.pageSize, menu.dynamicSize, menu.categories);
                
                nextMenu.display((Player) event.getWhoClicked());
            }
            else if (checkNull(menu.config.getString("menus.messages.last-page"))) {
                player.sendMessage(ChatColor.RED + "You are already on the last page!");                
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.messages.last-page")));
            }
            
        }
        
        else if (name.equals(ChatColor.RED + "Invalid display")) {
            if (checkNull(menu.config.getString("menus.messages.invalid-title"))) {
                player.sendMessage(ChatColor.RED + "Welp! This is embarrassing! You should not be seeing this, please contact the server administrator(s)!");
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', menu.config.getString("menus.messages.invalid-title")));
            }
        }
        else {

            for (String category : menu.categories.subList(menu.generateFirstIndex(menu.page, menu.pageSize), 
                    menu.generateLastIndex(menu.page, menu.pageSize, menu.categories.size()))) {

                if (ChatColor.translateAlternateColorCodes('&', menu.fileHandler.getTitles().getString("categories." + category + ".display")).contains(name)) {
                    TitlesMenu titlesMenu = new TitlesMenu(player, category);
                    titlesMenu.display((Player) event.getWhoClicked());
                    break;
                }

            }       
            
        }
        
    }
   
}

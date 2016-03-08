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
package com.karus.danktitles.menus;

import com.karus.danktitles.DankTitles;
import com.karus.danktitles.backend.FileHandler;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class CategoryMenu extends BaseMenu {
    
    // Inherits int page, pageSize & pageTotal from BaseMenu
    // Inherits Inventory menu, ItemStack item & ItemMeta itemMeta
    
    // Fields
    private FileHandler fileHandler;
    private List<String> categories;
    
    
    public CategoryMenu() {
        this.page = 1;
    }
    
    public CategoryMenu(int page, int pageTotal) {
        
        this.pageTotal = pageTotal;
        
        if (page > 0 && page <= pageTotal) {
            this.page = page;
        }
        
    }
    
    
    @Override
    
    // Implementation of method inheritied from BaseMenu, Menu
    public void display(Player player) {
        
        fileHandler = FileHandler.getInstance();
        
        categories = new ArrayList();
        categories.addAll(fileHandler.getTitles().getConfigurationSection("categories").getKeys(false));
        
        // Inherits generatePageSize and generatePageTotal from BaseMenu
        if (categories.isEmpty()) {
            
            pageSize = 9;
            pageTotal = 1;
            menu = Bukkit.createInventory(null, pageSize, "§l§2Titles - Categories§r");
            player.openInventory(menu);
            
            return;
            
        }
        else {
            
            pageSize = generatePageSize(categories.size());
            pageTotal = generatePageTotal(categories.size(), pageSize);
            
        }
        
        if (pageTotal == 1) {
            
            menu = Bukkit.createInventory(null, pageSize, "§l§2Titles - Categories§r");
            
        }
        else {
            
            menu = Bukkit.createInventory(null, 54, "§1§2Titles - Categories - Page " + page + "§r");
            
            
            // Create and place the Previous Page button
            item = new ItemStack(Material.STAINED_CLAY, 1, (short) DyeColor.LIME.getDyeData());
            item = generateItemMeta(item, "&l&2Previous Page&r", null);
            
            menu.setItem(pageSize - 8, item);
            
            
            // Create and place the Next Page button
            item = new ItemStack(Material.STAINED_CLAY, 1, (short) DyeColor.LIME.getDyeData());
            item = generateItemMeta(item, "&l&2Next Page&r", null);
            
            menu.setItem(pageSize, item);
            
        }
        
        
        // Generates the items to be placed

        for (String category : categories.subList(generateFirstIndex(page, pageSize), generateLastIndex(page, pageSize, categories.size()))) {
            String path = ("categories." + category + ".");
            
            // Inherited method parseColour from BaseMenu, MenuUtility
            
            // Checks if the item is valid and if it isn't default to Stone block
            if (!fileHandler.getTitles().contains(path + "item") || Material.getMaterial(fileHandler.getTitles().getString(path + "item")) == null) {
                item = new ItemStack(Material.STONE);
            }
            
            // Checks if there is a colour specified, generates an item without a colour if colour is null/invalid
            else if (!fileHandler.getTitles().contains(path + "colour") || fileHandler.getTitles().getString(path + "colour").equalsIgnoreCase(null)) {
                item = new ItemStack(Material.getMaterial(fileHandler.getTitles().getString(path + "item")));    
            }
            
            else {
                
                item = new ItemStack(Material.getMaterial(fileHandler.getTitles().getString(path + "item")), 1, 
                    (short) fileHandler.getTitles().getInt(path + "colour"));
                
            }
            
            // generateItemMeta inheritied from BaseMenu, generates the item's lore
            item = generateItemMeta(item, fileHandler.getTitles().getString(path 
                    + "display"), fileHandler.getTitles().getStringList(path + "lore"));
            
            menu.addItem(item);
                
        }
        
        player.openInventory(menu);
        
    }
    
    
    @EventHandler
    @Override
    
    // Method inherited from EventListener
    public void handleEvent(InventoryClickEvent event) {
        
        
        Inventory eventMenu = event.getInventory();
        if (!eventMenu.getTitle().contains("§l§2Titles - Categories")) return;
        
        event.setCancelled(true);
        
        // Initialisation
        fileHandler = FileHandler.getInstance();
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        String parsedName = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());

        categories = new ArrayList();
        categories.addAll(fileHandler.getTitles().getConfigurationSection("categories").getKeys(false));

        pageSize = generatePageSize(categories.size());
        pageTotal = generatePageTotal(categories.size(), pageSize);
        // Switch statement for determining which item was clicked and their corresponding behaviour
        switch(parsedName) {
            
            case "Previous Page":
                if ((page - 1) > 0) {
                    CategoryMenu previousMenu = new CategoryMenu(page + 1, pageTotal);
                    previousMenu.display((Player) event.getWhoClicked());
                }
                else {
                    player.sendMessage(ChatColor.RED + "You're already on the first page!");
                }
                break;
            
            case "Next Page":
                if ((page + 1) <= pageTotal) {
                    CategoryMenu nextMenu = new CategoryMenu(page - 1, pageTotal);
                    nextMenu.display((Player) event.getWhoClicked());
                }
                else {
                    player.sendMessage(ChatColor.RED + "There's no more categories!");
                }
                break;
                
            case "Invalid Title":
                player.sendMessage(ChatColor.RED + "Welp, this is embarrassing! You should not be seeing this title.");
                break;
                
            default:
                
                for (String category : categories.subList(generateFirstIndex(page, pageSize), generateLastIndex(page, pageSize, categories.size()))) {
                    
                    if (fileHandler.getTitles().getString("categories." + category + ".display").contains(parsedName)) {
                        TitlesMenu titlesMenu = new TitlesMenu(category);
                        titlesMenu.display((Player) event.getWhoClicked());
                        break;
                    }
                    
                }       
                break;
            
        }
        
    }
}

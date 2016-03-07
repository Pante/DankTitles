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

import com.karus.danktitles.backend.FileHandler;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
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
    
    private FileHandler fileHandler;
    
    
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
        
        
        List<String> categories = new ArrayList();
        categories.addAll(FileHandler.getInstance().getTitles().getConfigurationSection("categories").getKeys(false));
        
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
            if (!fileHandler.getTitles().contains(path + "item") || Material.getMaterial(fileHandler.getTitles().getString(path + "item")) == null) {
                item = new ItemStack(Material.STONE);
            }
            
            else if (!fileHandler.getTitles().contains(path + "colour") || fileHandler.getTitles().getString(path + "colour").equalsIgnoreCase(null)) {
                item = new ItemStack(Material.getMaterial(fileHandler.getTitles().getString(path + "item")));    
            }
            
            else {

                item = new ItemStack(Material.getMaterial(fileHandler.getTitles().getString(path + "item")), 1, 
                    (short) fileHandler.getTitles().getInt(path + "colour"));
                
            }
            
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
        if (!eventMenu.equals(menu)) return;
        
        ItemStack clicked = event.getCurrentItem();
        
        
        // Switch statement for determining which item was clicked and their corresponding behaviour
        switch(clicked.getItemMeta().getDisplayName()) {
            
            case "§l§2Previous Page§r":
                CategoryMenu previousMenu = new CategoryMenu(page + 1, pageTotal);
                previousMenu.display((Player) event.getWhoClicked());
                break;
            
                
            case "§l§2Next Page§r":
                CategoryMenu nextMenu = new CategoryMenu(page - 1, pageTotal);
                nextMenu.display((Player) event.getWhoClicked());
                break;
                
            case "§l§cInvalid Title§r":
                break;
                
            default:
                TitlesMenu titlesMenu = new TitlesMenu(clicked.getItemMeta().getDisplayName().replaceAll("/§?/", ""));
                titlesMenu.display((Player) event.getWhoClicked());
                break;
            
        }
        Player eventPlayer = (Player) event.getWhoClicked();
        eventPlayer.getInventory().InventoryInteract().cancel().all();
        event.setCancelled(true);
        
    }
}

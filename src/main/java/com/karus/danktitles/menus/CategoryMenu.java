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
package com.karus.danktitles.menus;

import com.karus.danktitles.DankTitles;
import com.karus.danktitles.io.FileHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class CategoryMenu implements Menu, MenuChecker {

    // Inherits int page, pageSize, pageTotal & dynamicSize from BaseMenu
    // Inherits Inventory menu, ItemStack item & ItemMeta itemMeta
    
    // Fields
    private FileConfiguration config;
    private boolean dynamicSize;
    private int page = 1;
    private int pageSize;
    private int pageTotal;
    
    public CategoryMenu(Player player) {
        
        config = DankTitles.instance.getConfig();
        
        dynamicSize = config.getBoolean("menus.category.dynamic-page", true);
        if (dynamicSize == true) {
            pageSize = generatePageSize(FileHandler.getTitles().size());
        } else {
            pageSize = config.getInt("menus.category.page-size");
            if (pageSize <= 0 || pageSize > 54) {
                pageSize = 54;
            }
        }    
        pageTotal = generatePageTotal(FileHandler.getTitles().size(), pageSize);
        
    }
    
    public CategoryMenu(int page, int pageSize, int pageTotal, boolean dynamicSize) {
        
        this.page = page;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.dynamicSize = dynamicSize;
        
    }
    
    @Override
    
    // Implementation of method inheritied from Menu
    public void display(Player player) { 
        
        if (FileHandler.getCategories().isEmpty()) {
            player.sendMessage(parseColouredString(config.getString("danktitles.message.no-categories",
                    "There are currently no categories available!")));
            return;   
        }
        
        getMenu().put(player.getUniqueId(), this);
        Inventory menu;
        
        if (pageTotal == 1) {   
            menu = Bukkit.createInventory(null, pageSize, "§l§2Titles - Categories§r");   
        } else {
            
            menu = Bukkit.createInventory(null, pageSize, "§1§2Titles - Categories - Page " + page + "§r");
            
            // Create and place the Previous Page button
            ItemStack item = config.getItemStack("menus.icons.back");
            menu.setItem(pageSize - 8, item);
            
            // Create and place the Next Page button
            item = config.getItemStack("menus.icons.next");
            menu.setItem(pageSize, item);
            
        }
        
        // Generates the items to be placed

        new ArrayList<>(FileHandler.getCategories().keySet())
                .subList(generateFirstIndex(page, pageSize),
                        generateLastIndex(page, pageSize, FileHandler.getCategories().size())).stream().forEach((name) -> {
                            
            String path = ("categories." + category);
            
            item = generateItem(fileHandler.getTitles(), path);
            item = generateItemMeta(fileHandler.getTitles(), path, item);
            
            menu.addItem(item);
                
        });
        
        player.openInventory(menu);
        
    }
    
    // <------ Getter methods ------>
    public static HashMap<UUID, CategoryMenu> getMenu() {
        if (inMenu == null) {
            inMenu = new HashMap();
        }
        return inMenu;
    }
    
}

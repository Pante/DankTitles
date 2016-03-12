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
import com.karus.danktitles.backend.FileHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class CategoryMenu extends BaseMenu {

    // Inherits int page, pageSize, pageTotal & dynamicSize from BaseMenu
    // Inherits Inventory menu, ItemStack item & ItemMeta itemMeta
    
    // Static Fields
    private static HashMap<UUID, CategoryMenu> inMenu;
    
    // Fields
    public FileHandler fileHandler;
    public List<String> categories;
    public FileConfiguration config;
    
    public CategoryMenu() {}
    
    public CategoryMenu(Player player) {
        
        fileHandler = FileHandler.getInstance();
        
        categories = new ArrayList();
        categories.addAll(fileHandler.getTitles().getConfigurationSection("categories").getKeys(false));
        
        config = DankTitles.instance.getConfig();
        
        if (checkCollection(categories)) return;
           
        
        if (checkNull(config) || config.contains("menus.category.dynamic-page")) {
            dynamicSize = config.getBoolean("menus.category.dynamic-page");
        }
        
        if (dynamicSize == false && config.contains("menus.category.page-size")) {
            pageSize = config.getInt("menus.category.page-size");
            if (pageSize <= 0 || pageSize > 54) {
                pageSize = 54;
            }
            
        }
        
        if (dynamicSize == true) {
            pageSize = generatePageSize(categories.size());
        }
        
        pageTotal = generatePageTotal(categories.size(), pageSize);
        
        this.page = 1;
        
    }
    
    public CategoryMenu(int page, int pageSize, int pageTotal, boolean dynamicSize, List<String> categories) {
        
        fileHandler = FileHandler.getInstance();
        
        this.page = page;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.dynamicSize = dynamicSize;
        this.categories = categories;
        
    }
    
    @Override
    
    // Implementation of method inheritied from BaseMenu, Menu
    public void display(Player player) { 
        
        if (checkCollection(categories)) {
            
            if (checkNull(config.getString("danktitles.message.no-categories"))) {
                player.sendMessage(ChatColor.RED + "There are currently no categories available!");
            }
            else {
                player.sendMessage(parseColouredString(config.getString("danktitles.message.no-categories")));
            }
            
            return;
            
        }
        
        getMenu().put(player.getUniqueId(), this);
        if (pageTotal == 1) {
            
            menu = Bukkit.createInventory(null, pageSize, "§l§2Titles - Categories§r");
            
        }
        else {
            
            menu = Bukkit.createInventory(null, pageSize, "§1§2Titles - Categories - Page " + page + "§r");
            
            
            // Create and place the Previous Page button
            item = generateItem(config, "menus.icons.back");
            item = generateItemMeta(config, "menus.icons.back", item);
            
            menu.setItem(pageSize - 8, item);
            
            
            // Create and place the Next Page button
            item = generateItem(config, "menus.icons.next");
            item = generateItemMeta(config, "menus.icons.next", item);
            
            menu.setItem(pageSize, item);
            
        }
        
        
        // Generates the items to be placed

        for (String category : categories.subList(generateFirstIndex(page, pageSize), generateLastIndex(page, pageSize, categories.size()))) {
            String path = ("categories." + category);
            
            item = generateItem(fileHandler.getTitles(), path);
            item = generateItemMeta(fileHandler.getTitles(), path, item);
            
            menu.addItem(item);
                
        }
        
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

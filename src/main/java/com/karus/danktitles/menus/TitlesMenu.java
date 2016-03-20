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
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class TitlesMenu extends BaseMenu implements Listener {
    
    // Inherits int page, pageSize, pageTotal & dynamicSize from BaseMenu
    // Inherits Inventory menu, ItemStack item & ItemMeta itemMeta
    
    // Static fields
    private static HashMap<UUID, TitlesMenu> inMenu;
    
    // Fields
    public FileHandler fileHandler;
    public List<String> titles;
    public FileConfiguration config;
    public String category;
    
    public TitlesMenu() {}
    
    public TitlesMenu(Player player, String category) {
        
        fileHandler = FileHandler.getInstance();
        
        titles = new ArrayList();
        titles.addAll(fileHandler.getPlayers().getStringList("players." + player.getUniqueId() + ".titles." + category));
        
        config = DankTitles.instance.getConfig();
        
        if (checkCollection(titles)) return;
        
        if (config == null || config.contains("menus.titles.dynamic-page")) {
            dynamicSize = config.getBoolean("menus.titles.dynamic-page");
        }
        
        if (dynamicSize == false && config.contains("menus.titles.page-size")) {
            pageSize = config.getInt("menus.titles.page-size");
            if (pageSize <= 0 || pageSize > 54) {
                pageSize = 54;
            }
            
        }
        
        if (dynamicSize == true) {
            pageSize = generatePageSize(titles.size());
        }
        
        pageTotal = generatePageTotal(titles.size(), pageSize);
        
        this.page = 1;
        this.category = category;
        
    }
    
    public TitlesMenu(int page, int pageSize, int pageTotal, boolean dynamicSize, List<String> categories, String category) {
        
        fileHandler = FileHandler.getInstance();
        
        this.page = page;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.dynamicSize = dynamicSize;
        this.category = category;
        
    }
    
    
    @Override
    
    // Implementation of method inheritied from BaseMenu, Menu
    public void display(Player player) {
        
        if (checkCollection(titles)) {
            
            if (config.getString("danktitles.message.no-titles") == null) {
                player.sendMessage(ChatColor.RED + "There are currently no titles available!");
            }
            else {
                player.sendMessage(parseColouredString(config.getString("danktitles.message.no-titles")));
            }
            
            return;
            
        }
        
        getMenu().put(player.getUniqueId(), this);
        
        if (pageTotal == 1) {
            
            menu = Bukkit.createInventory(null, pageSize, "§l§2Titles - Category - " + category + "§r");
            
        }
        else {
            
            menu = Bukkit.createInventory(null, pageSize, "§1§2Titles - Category - " + category + "Page - " + page + "§r");
            
            
            // Create and place the Previous Page button
            item = generateItem(config, "menus.icons.back");
            item = generateItemMeta(config, "menus.icons.back", item);
            
            menu.setItem(pageSize - 8, item);
            
            // Create and place the Next Page button
            item = generateItem(config, "menus.icons.next");
            item = generateItemMeta(config, "menus.icons.next", item);
            
            
            menu.setItem(pageSize, item);
            
        }
        
        // Create and place the Reset Title button
        item = generateItem(config, "menus.icons.reset");
        item = generateItemMeta(config, "menus.icons.reset", item);

        menu.setItem(pageSize - 6, item);

        // Create and place the Main Menu Button
        item = generateItem(config, "menus.icons.menu");
        item = generateItemMeta(config, "menus.icons.menu", item);

        menu.setItem(pageSize - 5, item);
        
        
        // Generates the items to be placed

        for (String title : titles.subList(generateFirstIndex(page, pageSize), generateLastIndex(page, pageSize, titles.size()))) {
            String path = ("categories." + category + ".titles." + title);
            
            item = generateItem(fileHandler.getTitles(), path);
            item = generateItemMeta(fileHandler.getTitles(), path, item);
            
            menu.addItem(item);
                
        }
        
        player.openInventory(menu);
        
    }
    
    // <------ Getter & Setter methods ------>
    public static HashMap<UUID, TitlesMenu> getMenu() {
        if (inMenu == null) {
            inMenu = new HashMap();
        }
        return inMenu;
    }
    
}

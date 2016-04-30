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
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class CategoryMenu implements Menu, Listener {
    
    // Fields
    private String name;
    private FileConfiguration config;
    
    private int page;
    private int pageSize;
    private int pageTotal;
    
    public CategoryMenu() {
        
        config = DankTitles.instance.getConfig();
        page = 1;
        
        if (MenuUtility.DynamicSizeEnabled()) {
            pageSize = MenuUtility.generateSize(FileHandler.getCategories().size(), page);
        } else {
            pageSize = MenuUtility.getStaticSize();
        }
        
        pageTotal = MenuUtility.generatePages(FileHandler.getCategories().size(), pageSize);
        
    }
    
    public CategoryMenu(int page, int pageTotal) {
        
        config = DankTitles.instance.getConfig();
        
        this.page = page;
        this.pageTotal = pageTotal;
        
        if (MenuUtility.DynamicSizeEnabled()) {
            pageSize = MenuUtility.generateSize(FileHandler.getCategories().size(), page);
        } else {
            pageSize = MenuUtility.getStaticSize();
        }
        
    }
    
     
    @Override
    public void display(Player player) {
        
        DankTitles.instance.getServer().getPluginManager().registerEvents(this, DankTitles.instance);
        
        if (FileHandler.getCategories().isEmpty()) {    
            player.sendMessage(config.getString("message.no-categories", ChatColor.RED + "There are currently no categories available!"));
        }
        
        Inventory menu;
        
        if (pageTotal == 1) {
            
            name = "§l§2Titles - Categories§r";
            menu = Bukkit.createInventory(null, pageSize, name);
            
        } else {
            
            name = "§1§2Titles - Categories - Page " + page + "§r";
            menu = Bukkit.createInventory(null, pageSize, name);

            menu.setItem(pageSize - 8, MenuUtility.getIcon("back")); 
            menu.setItem(pageSize, MenuUtility.getIcon("next"));
            
        }
        
        // Generates the items to be placed
        ArrayList<String> list = new ArrayList<>(FileHandler.getCategories().keySet()); 
        
        IntStream.range(MenuUtility.generateFirst(page, pageSize), 
                MenuUtility.generateLast(page, pageSize, FileHandler.getCategories().size()))
                .forEach(n -> menu.addItem(FileHandler.getCategories().get(list.get(n))));
        
        player.openInventory(menu);
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        
        if (!e.getInventory().getTitle().equals(name)) {
            return;
        }
        e.setCancelled(true);
        
        ItemStack clicked = e.getCurrentItem();
        if (clicked.getType() == Material.AIR) return;
        
        Player player = (Player) e.getWhoClicked();
        
        if (clicked.equals(MenuUtility.getIcon("back"))) {
            if ((page - 1) > 0) {
                new CategoryMenu(page - 1, pageTotal).display(player);
            } else {
                player.sendMessage(ChatColor.RED + "You're already on the first page!");
            }
        }
        
        else if (clicked.equals(MenuUtility.getIcon("next"))) {
            if ((page + 1) <= pageTotal) {
                new CategoryMenu(page + 1, pageTotal).display(player);
            } else {
                player.sendMessage(ChatColor.RED + "You're already on the last page!");
            }
        } 
        
        else {
            try {
                
                String category = FileHandler.getCategories().entrySet()
                        .stream().filter(entry -> clicked.equals(entry.getValue())).findFirst().get().getKey();
                new TitlesMenu(category).display(player);
                
            } catch (NoSuchElementException ex) {
                player.sendMessage(ChatColor.RED + "Welp, this is embarrassing! Please report this to the server administration.");
            } 
        }
        
    }
    
}

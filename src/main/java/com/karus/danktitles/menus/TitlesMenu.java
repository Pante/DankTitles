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
public class TitlesMenu extends BaseMenu {
    
    // Inherits int page, pageSize & pageTotal from BaseMenu
    // Inherits Inventory menu, ItemStack item & ItemMeta itemMeta
    
    private FileHandler fileHandler;
    public String category;
    
    // Used only to register the listener
    public TitlesMenu(){}
    
    public TitlesMenu(String category) {
        this.page = 1;
        this.category = category;
    }
    
    public TitlesMenu(int page, int pageTotal, String category) {
        
        if (page > 0 && page <= pageTotal) {
            this.page = page;
        }
        
        this.category = category;
        
    }
    
    
    @Override
    
    // Implementation of method inheritied from BaseMenu, Menu
    public void display(Player player) {
        
        fileHandler = FileHandler.getInstance();
        
        
        List<String> titles = new ArrayList();
        if (fileHandler.getPlayers().getStringList("players." + player.getUniqueId() + ".titles." + category) == null) {
            player.sendMessage("It seems like you do not have any titles in this category!");
            return;
        }
        titles.addAll(fileHandler.getPlayers().getStringList("players." + player.getUniqueId() + ".titles." + category));
        
        // Inherits generatePageSize and generatePageTotal from BaseMenu
        pageSize = generatePageSize(titles.size());
        pageTotal = generatePageTotal(titles.size(), pageSize);
        
        
        if (pageTotal == 1) {
            
            menu = Bukkit.createInventory(null, pageSize, "§l§2Titles - Categories§r");
            
        }
        else {
            
            menu = Bukkit.createInventory(null, 54, "§1§2Titles - Categories - Page " + page + "§r");
            
            
            // Create and place the Previous Page button
            item = new ItemStack(Material.STAINED_CLAY, 1, DyeColor.LIME.getDyeData());
            item = generateItemMeta(item, "§l§2Previous Page§r", null);
            
            menu.setItem(pageSize - 8, item);
            
            
            // Create and place the Reset title button
            item = new ItemStack(Material.ANVIL);
            item = generateItemMeta(item, "§l§4Reset Title§r", null);
            
            
            menu.setItem(pageSize - 5, item);
            
            
            // Create and place the Back to main menu button
            item = new ItemStack(Material.EYE_OF_ENDER);
            item = generateItemMeta(item, "§l§2Main Menu§r", null);
            
            menu.setItem(pageSize - 4, item);
            
            
            // Create and place the Next Page button
            item = new ItemStack(Material.STAINED_CLAY, 1, DyeColor.LIME.getDyeData());
            item = generateItemMeta(item, "§l§2Next Page§r", null);
            
            menu.setItem(pageSize, item);
            
        }
        
        
        for (String title : titles.subList(generateFirstIndex(page, pageSize), generateLastIndex(page, pageSize, titles.size()))) {
            
            String path = ("categories." + category + ".titles." + title + ".");
            
            // Inherited method parseColour from BaseMenu, MenuUtility
            if (Material.getMaterial(fileHandler.getTitles().getString(path + "item")) == null) {
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
                TitlesMenu previousMenu = new TitlesMenu(page + 1, pageTotal, category);
                previousMenu.display((Player) event.getWhoClicked());
                break;
            
                
            case "§l§4Reset Title§r":
                Player player = (Player) event.getWhoClicked();
                DankTitles.chat.setPlayerPrefix(player, DankTitles.chat.getGroupPrefix(player.getWorld(), DankTitles.permission.getPrimaryGroup(player)));
                break;
                
                
            case "§l§2Main Menu§r":
                CategoryMenu mainMenu = new CategoryMenu();
                mainMenu.display((Player) event.getWhoClicked());
                break;
                
                
            case "§l§2Next Page§r":
                TitlesMenu nextMenu = new TitlesMenu(page - 1, pageTotal, category);
                nextMenu.display((Player) event.getWhoClicked());
                break;
            
            case "§l§cInvalid Title§r":
                break;
                
            default:
                DankTitles.chat.setPlayerPrefix((Player) event.getWhoClicked(), clicked.getItemMeta().getDisplayName());
                break;
            
        }
    
        event.setCancelled(true);
        
    }
}

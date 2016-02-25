/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles.menus;

import com.karusmc.danktitles.utilities.FileHandler;
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
 * @author Pante
 */
public class CategoryMenu extends BaseMenu {
    
    //Inherits int pageSize, int currentPage & int totalPages from BaseMenu
    
    public CategoryMenu(int page) {
        
        if (page > 0 && page <= totalPages) {
        this.currentPage = page;
        }
        
    }
    
    @Override
    
    // Implementation of abstract method found in BaseMenu
    public void display(Player player) {
        
        this.player = player;
        
        List<String> categories = new ArrayList();
        categories.addAll(FileHandler.titles.getConfigurationSection("categories").getKeys(false));
        
        // Inherits generatePageSize and generateTotalPages from BaseMenu
        pageSize = generatePageSize(categories.size());
        totalPages = generateTotalPages(categories.size());
        
        
        if (totalPages == 1) {
            
            menu = Bukkit.createInventory(null, pageSize, "§l§2Titles - Categories§r");
            
        }
        else {
            
            menu = Bukkit.createInventory(null, 54, "§1§2Titles - Categories - Page " + currentPage + "§r");
            
            // Create the buttons
            previousButton= new ItemStack(Material.STAINED_CLAY, 1, (byte)DyeColor.LIME.getDyeData());
            nextButton= new ItemStack(Material.STAINED_CLAY, 1, (byte)DyeColor.LIME.getDyeData());
            
            // Generates the item meta for the buttons, inheririts generateItemMeta from BaseMenu
            previousButton = generateItemMeta(previousButton, "§l§2Previous Page§r");
            nextButton = generateItemMeta(nextButton, "§l§2Next Page§r");
            
            menu.setItem(46, previousButton);
            menu.setItem(54, nextButton);
            
        }
        
        
        for (String category : categories.subList(generateFirstIndex(pageSize, currentPage), generateLastIndex(pageSize, currentPage, categories.size()))) {
            
            ItemStack item;
            
            // Inherits parseColour from BaseMenu
            if (FileHandler.titles.getString("categories." + category + ".colour").equalsIgnoreCase(null)) {
                
                item = new ItemStack(Material.getMaterial(FileHandler.titles.getString("categories." + category + ".item")), 1, 
                        parseColour("categories." + category + ".colour"));
                        
            }
            else {
                
                item = new ItemStack(Material.getMaterial(FileHandler.titles.getString("categories." + category + ".item")));
                
            }
            
            item = generateItemMeta(item, FileHandler.titles.getString("categories." + category + ".display"));
            
            menu.addItem(item);
                
        }
        
        player.openInventory(menu);
        
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        
        ItemStack clicked = event.getCurrentItem(); // The item that was clicked
        Inventory inventory = event.getInventory(); // The inventory that was clicked
        
        if (inventory.getTitle().equals(menu.getTitle())) {
            
            if (clicked.equals(previousButton)) {
                if (currentPage > 1) {
                    CategoryMenu newMenu = new CategoryMenu(currentPage - 1);
                    newMenu.display(player);
                }
            }
            else if (clicked.equals(nextButton)) {
                if (currentPage < totalPages) {
                    CategoryMenu newMenu = new CategoryMenu(currentPage + 1);
                    newMenu.display(player);
                }
            }
            else if (menu.contains(clicked)) {
                TitlesMenu newMenu = new TitlesMenu(1, clicked.getItemMeta().getDisplayName().replaceAll("/§?/", ""));
            }
            
            event.setCancelled(true);
        }
        
    }
    
}

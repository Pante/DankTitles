/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles.menus;

import com.karusmc.danktitles.utilities.FileHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Pante
 */
public class TitlesMenu extends BaseMenu {
    
    //Inherits Inventory menu, int pageSize, int currentPage & int totalPages from BaseMenu
    String category;
    
    public TitlesMenu(int page, String category) {
        
        if (page > 0 && page <= totalPages) {
        this.currentPage = page;
        }
        this.category = category;
        
    }
    
    @Override
    
    // Implementation of abstract method found in BaseMenu
    public void display(Player player) {
        
        List<String> titles = new ArrayList();
        titles.addAll((Collection<? extends String>) FileHandler.players.getList("players." + player.getUniqueId() + ".titles." + category));
        
        // Inherits generateTotalPages from BaseMenu
        totalPages = generateTotalPages(titles.size());
        
        if (totalPages == 1) {
            
            menu = Bukkit.createInventory(null, 54, "§l§2Titles - " + category + "§r");
            
        }
        else {
            
            menu = Bukkit.createInventory(null, 54, "§1§2Titles - " + category + " - Page " + currentPage + "§r");
            
        }
        
        // Create the buttons
        ItemStack previousButton= new ItemStack(Material.STAINED_CLAY, 1, (byte)DyeColor.LIME.getDyeData());
        ItemStack resetButton = new ItemStack(Material.ANVIL);
        ItemStack menuButton = new ItemStack(Material.EYE_OF_ENDER);
        ItemStack nextButton= new ItemStack(Material.STAINED_CLAY, 1, (byte)DyeColor.LIME.getDyeData());

        // Generates the item meta for the buttons, inheririts generateItemMeta from BaseMenu
        previousButton = generateItemMeta(previousButton, "§l§2Previous Page§r");
        resetButton = generateItemMeta(resetButton, "§l§4Reset Title§r");
        menuButton = generateItemMeta(menuButton, "§l§2Main Menu§r");
        nextButton = generateItemMeta(nextButton, "§l§2Next Page§r");

        menu.setItem(46, previousButton);
        menu.setItem(49, resetButton);
        menu.setItem(50, menuButton);
        menu.setItem(54, nextButton);
        
        
        for (String title : titles.subList(generateFirstIndex(54, currentPage), generateLastIndex(54, currentPage, titles.size()))) {
            
            ItemStack item;
            String basePath = "categories." + category + ".titles." + title;
            
            // Inherits parseColour from BaseMenu
            if (FileHandler.titles.getString(basePath + ".colour").equalsIgnoreCase(null)) {
                
                item = new ItemStack(Material.getMaterial(FileHandler.titles.getString(basePath + ".item")), 1, 
                        parseColour(basePath + ".colour"));
                        
            }
            else {
                
                item = new ItemStack(Material.getMaterial(FileHandler.titles.getString(basePath + ".item")));
                
            }
            
            item = generateItemMeta(item, FileHandler.titles.getString(basePath + ".display"));
            
            menu.addItem(item);
                
        }
        
        player.openInventory(menu);
    }
}

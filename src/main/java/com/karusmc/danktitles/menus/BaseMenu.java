/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles.menus;

import com.karusmc.danktitles.utilities.FileHandler;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Pante
 */
public abstract class BaseMenu implements Listener {
    
    // Variables that keep track of the page size, current page and total pages respectively
    int pageSize;
    int currentPage;
    int totalPages;
    
    // Variable to represent the inventory and buttons
    Inventory menu;
    ItemStack previousButton;
    ItemStack resetButton;
    ItemStack menuButton;
    ItemStack nextButton;
    Player player;
    
    // Abstract method which will contain the menu's logic
    public abstract void display(Player player);
    
    
    // Helper method which dynamically generates the number of pages needed
    public int generateTotalPages(int size) {
        
        if (size <= 54) return 1;
        
        return (int) (Math.ceil((double) size / 45.0));
        
    }
    
    // Helper method which dynamically generates the inventory size that can be used to store titles/categories
    public int generatePageSize(int size) {
        
        // Checks if too many items were specified
        if (size > 54) return 45;
        
        double number = size;
        return (int) (Math.ceil((number / 9.0 )) * 9.0);
        
    }
    
    
    // Helper method which generates the first index for creating a sublist
    public int generateFirstIndex(int pageSize, int currentPage) {
        
        return ((pageSize * currentPage) - pageSize);
        
    }
    
    //Helper method which generates the last index for creating a sublist
    public int generateLastIndex(int pageSize, int currentPage, int parentListSize) {
        
        if (((pageSize * currentPage) - 1) > parentListSize) return (parentListSize - 1);
        
        else return ((pageSize * currentPage) - 1);
        
    }
    
    
    // Helper method which converts a string under titles file to a DyeColor enum then to bytes
    public byte parseColour(String path) {
        
        return DyeColor.valueOf(FileHandler.titles.getString(path)).getDyeData();
        
    }
    
    // Helper method which generates the itemstack's meta
    public ItemStack generateItemMeta(ItemStack item, String name) {
        
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        
        return item;
        
    }
    
}

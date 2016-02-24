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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Pante
 */
public class CategoryMenu extends MenuBase {
    //Implementation of single design pattern
    public static CategoryMenu instance;
    private CategoryMenu(){}
    

    // Method that opens up the menu
    public void openMenu(Player player) {
        
        List<String> categories = new ArrayList();
        categories.addAll(FileHandler.titles.getConfigurationSection("categories").getKeys(false));
        
        
        //Generates the inventory, generatedPageSize implemented in MenuBase
        Inventory menu = Bukkit.createInventory(null, generatePageSize(categories.size()), "§l§2Titles - Categories§r");
        
        
        for (String category : categories) {
            
            ItemStack item;
            
            
            // Checks if the item is coloured and creates the item accordingly
            if (FileHandler.titles.contains("categories." + category + ".colour")) {
                item = new ItemStack(Material.getMaterial(FileHandler.titles.getString("categories." + category + ".item")), 1, DyeColor.SILVER.getDyeData());
            }
            else {
                item = new ItemStack(Material.getMaterial(FileHandler.titles.getString("categories." + category + ".item")));
            }
            
            // Sets the display name for the item
            ItemMeta itemMeta =item.getItemMeta();
            itemMeta.setDisplayName(FileHandler.titles.getString("categories." + category + ".display"));
            item.setItemMeta(itemMeta);
            
            
            // adds the item to the arraylist
            menu.addItem(item);
            
        }
       
        player.openInventory(menu);
    }
    
}

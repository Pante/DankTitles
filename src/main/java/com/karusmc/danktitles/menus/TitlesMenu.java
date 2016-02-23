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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Pante
 */
public class TitlesMenu extends MenuBase {
    
    
    // Method that opens up the menu
    public static void openMenu(Player player, String category, Inventory menu) {
        int pageNumber;
        int totalPages;
        
        List<String> titles = new ArrayList();
        titles.addAll((Collection<? extends String>) FileHandler.players.getList("players." + player.getUniqueId() + ".titles." + category));
        
        // checks if menu created from a previous page
        if (menu == null) {
            
            pageNumber = 1;
            totalPages = (int) (Math.ceil((double)titles.size() / 48.0));

        }
        else {
            
            pageNumber = (int) menu.getTitle().charAt(menu.getTitle().length() - 2) + 1;
            totalPages = (int) menu.getTitle().charAt(menu.getTitle().length());
        }
        
        menu = Bukkit.createInventory(null, 54, "§l§2Titles - " + category + "§r" + pageNumber + "/" + totalPages);
        
        // Add checker to see if number is out of bounds
        List<String> titlesDisplayed = titles.subList((pageNumber * 48 - 47), (pageNumber * 48));
        
        for (String title : titlesDisplayed) {
            
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
        
        // Using helper method to create menu items
        menu.setItem(49, TitlesMenu.createMenuItem("§l§2Previous Page§r", Material.STAINED_CLAY, (byte)DyeColor.LIME.getDyeData()));
        menu.setItem(52, TitlesMenu.createMenuItem("§l§4Reset title§r", Material.ANVIL, null));
        menu.setItem(53, TitlesMenu.createMenuItem("§l§4Menu§r", Material.EYE_OF_ENDER, null));
        menu.setItem(54, TitlesMenu.createMenuItem("§1§2Next Page§r", Material.WATER, (byte)DyeColor.LIME.getDyeData()));
        
        player.openInventory(menu);
    }
    
    
    // Helper method that creates a menu button
    public static ItemStack createMenuItem(String display, Material material, Byte colour) {
        ItemStack item = new ItemStack(material, 1, colour);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(display);
        item.setItemMeta(itemMeta);
        return item;
    }
}

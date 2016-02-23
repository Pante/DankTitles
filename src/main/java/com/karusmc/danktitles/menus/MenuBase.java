/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles.menus;

import com.karusmc.danktitles.DankTitles;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author Pante
 */
public abstract class MenuBase {
    
    // Method that dynamically generates the inventory size
    public int generatePageSize(int numberOfItems) {
        
        // Checks if too many items were specified
        if (numberOfItems > 54) {
            DankTitles.instance.getLogger().severe(ChatColor.RED + "Too many categories specified!");
            return 56;
        }
        float number = numberOfItems;
        return (int) (Math.ceil((number / 9.0 )) * 9.0);
    }
    
}

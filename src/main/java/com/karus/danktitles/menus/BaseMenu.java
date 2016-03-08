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

import com.karus.danktitles.listeners.EventListener;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public abstract class BaseMenu implements Menu, MenuUtility, EventListener<InventoryClickEvent>, Listener {
    
    // Page related variables
    // NOTE: pageSize refers to the fillable slots by the titles AND the navigation buttons.
    public int page, pageSize, pageTotal;
    
    // Menu & Item related variables
    public Inventory menu;
    public ItemStack item;
    public ItemMeta itemMeta;
    
    
    @Override
    public int generatePageTotal(int total, int pageSize) {
        
        if (total <= pageSize ) return 1;
        
        return (int) (Math.ceil((double) total / (pageSize - 9)));
        
    }
    
    @Override
    public int generatePageSize(int total) {
        
        // Checks if too many items were specified
        return Math.min((int) (Math.ceil(((double) total / 9.0 )) * 9.0), 54);
        
    }
    
    
    @Override
    public int generateFirstIndex(int page, int pageSize) {
        return ((page * pageSize) - (pageSize));
    }
    
    @Override
    public int generateLastIndex(int page, int pageSize, int rootListSize) {
        
        return Math.min((page * pageSize), rootListSize );
        
    }
    
    public ItemStack generateItemMeta(ItemStack item, String name, List<String> lore) {
        
        itemMeta = item.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        else {
            itemMeta.setDisplayName(ChatColor.RED + "Invalid name");
        }
        if (lore != null) {
            List<String> parsedLore = new ArrayList();
            for (String line: lore) {
                line = ChatColor.translateAlternateColorCodes('&', line);
                parsedLore.add(line);
            }
            itemMeta.setLore(parsedLore);
        }
        
        
        
        
        item.setItemMeta(itemMeta);
        
        return item;
        
    }
    
}

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

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public abstract class BaseMenu implements Menu, MenuChecker, PreconditionChecker {
    // Page related variables
    // NOTE: pageSize refers to the fillable slots by the titles AND the navigation buttons.
    public int page, pageSize, pageTotal;
    public boolean dynamicSize;
    
    // Menu & Item related variables
    public Inventory menu;
    public ItemStack item;
    public ItemMeta itemMeta;
    
    
    // Helper method that generates an item
    public ItemStack generateItem(FileConfiguration config, String path) {
        
        Material material = Material.STONE;
        
        if (config.getString(path + ".item") == null) {
            material = Material.getMaterial(config.getString(path + ".item"));
        }
        
        return new ItemStack(material, 1, (short) config.getInt(path + ".colour"));
    }
    
    
    // Generates the item's meta
    public ItemStack generateItemMeta(FileConfiguration config, String path, ItemStack item) {
        
        itemMeta = item.getItemMeta();
        if (config.getString(path + ".display") == null) {
            itemMeta.setDisplayName(ChatColor.RED + "Invalid display");
        }
        else {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(path + ".display")));
        }
        
        if (!checkCollection(config.getStringList(path + ".lore"))) {
            
            List<String> parsedLore = new ArrayList();
            for (String line: config.getStringList(path + ".lore")) {
                parsedLore.add(parseColouredString(line));
            }
            
            itemMeta.setLore(parsedLore);
            
        }
        
        item.setItemMeta(itemMeta);
        
        return item;
        
    }
    
}

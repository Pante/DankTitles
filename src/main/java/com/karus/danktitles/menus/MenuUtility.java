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

import org.bukkit.ChatColor;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public interface MenuUtility {
    
    // Dynamically generates the page size
    public default int generatePageSize(int total) {
        
        // Checks if too many items were specified
        return Math.min((int) (Math.ceil(((double) total / 9.0 )) * 9.0), 54);
        
    }
    
    // Generates the total number of pages
    public default int generatePageTotal(int total, int pageSize) {
        
        if (total <= pageSize ) return 1;
        
        return (int) (Math.ceil((double) total / (pageSize - 9)));
        
    }
 
    
    // Generates the first index of a sublist
    public default int generateFirstIndex(int page, int pageSize) {
        return ((page * pageSize) - (pageSize));
    }
    
    // Generates the last index of a sublist
    public default int generateLastIndex(int page, int pageSize, int rootListSize) {
        
        return Math.min((page * pageSize), rootListSize);
        
    }
    
    
    // Parses a coloured string and returns the coloured string
    public default String parseColouredString(String line) {
        return ChatColor.translateAlternateColorCodes('&', line);
    }
        
}

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
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class MenuUtility {
    
    // Private constructor
    private MenuUtility() {}
    
    // Static fields
    private static boolean dynamicSize;
    private static int staticSize;
    
    private static HashMap<UUID, Menu> menus = new HashMap<>();
    private static HashMap<UUID, HashMap<String, ArrayList<String>>> playerTitles = new HashMap<>();
    
    // <--- Utility methods ------>
    
    public static int generateSize(int total) {
        return Math.min((int) (Math.ceil(((double) total / 9.0 )) * 9.0), 54);
    }
    
    
    public static int generatePages(int total, int pageSize) {
        
        if (total <= pageSize ) return 1;
        
        return (int) (Math.ceil((double) total / (pageSize - 9)));
        
    }
    
    
    public static int generateFirst(int page, int pageSize) {
        return ((page * pageSize) - (pageSize));
    }
    
    public static int generateLast(int page, int pageSize, int rootListSize) {
        return Math.min((page * pageSize), rootListSize);   
    }
    
    
    // <------- Getter & Setter methods ------>
    
    public static boolean DynamicSizeEnabled() {
        return dynamicSize;
    }
    
    public static void setDynamicSize(boolean enabled) {
        dynamicSize = enabled;
    }
    
    
    public static int getStaticSize() {
        return staticSize;
    }
    
    public static void setStaticSize(int size) {
        staticSize = size;
    }
    
    
    public static HashMap<UUID, Menu> getMenus() {
        return menus;
    }
    
    public static HashMap<UUID, HashMap<String, ArrayList<String>>> getPlayerTitles() {
        return playerTitles;
    }
    
}

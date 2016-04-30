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

import com.karus.danktitles.DankTitles;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
    private static HashMap<String, ItemStack> icons = new HashMap<>();
    
    // <------ Loading methods ------->
    public static void load() {
        
        dynamicSize = DankTitles.instance.getConfig().getBoolean("dynamic-size", false);
        staticSize = DankTitles.instance.getConfig().getInt("static-size", 54);
        
        DankTitles.instance.getConfig().getConfigurationSection("icons").getKeys(false).stream().forEach(label -> 
            icons.put(label, DankTitles.instance.getConfig().getItemStack("icons." + label + ".item", new ItemStack(Material.STONE)))
        );
        
    }
    
    
    // <--- Utility methods ------>
    
    public static int generateSize(int total, int page) {
        
        int t = total - (page * 54);
        if (t <= 0) return 54;
        
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
    
    public static ItemStack getIcon(String label) {
        return icons.get(label);
    }
    
}

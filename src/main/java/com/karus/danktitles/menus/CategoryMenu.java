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

import com.karus.danktitles.io.FileHandler;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class CategoryMenu implements Menu {
    
    // Fields
    private int page;
    private int pageSize;
    private int pageTotal;
    
    public CategoryMenu() {
        page = 1;
        
        if (MenuUtility.DynamicSizeEnabled()) {
            pageSize = MenuUtility.generateSize(FileHandler.getCategories().size());
        } else {
            pageSize = MenuUtility.getStaticSize();
        }
        
        pageTotal = MenuUtility.generatePages(FileHandler.getCategories().size(), pageSize);
        
    }
    
    public CategoryMenu(int page, int pageTotal) {
        this.page = page;
        this.pageTotal = pageTotal;
    }
    
    
    
    @Override
    public void display(Player player) {
        
    }
    
}

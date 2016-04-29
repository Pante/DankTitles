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
public class TitlesMenu implements Menu {
    
    
    // Fields
    private int page;
    private int pageTotal;
    
    public TitlesMenu() {
        page = 1;
        int t = FileHandler.getCategories().size();
        if (!MenuUtility.DynamicSizeEnabled()) {
            pageTotal = MenuUtility.generatePages(t, MenuUtility.generateSize(t));
        } else {
            pageTotal = MenuUtility.generatePages(t, MenuUtility.getStaticSize());
        }
    }
    
    public TitlesMenu(int page, int pageTotal) {
        this.page = page;
        this.pageTotal = pageTotal;
    }
    
    
    @Override
    public void display(Player player) {
        
    }
    
}

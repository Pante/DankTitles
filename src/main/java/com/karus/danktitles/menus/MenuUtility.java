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

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public interface MenuUtility {
    
    // Method which generates the total number of pages
    public int generatePageTotal(int total, int pageSize);
    
    // Method which dynimcally generates the total number of pages
    public int generatePageSize(int total);
    
    
    // Method which generates the first index for creating a sublist
    public int generateFirstIndex(int page, int pageSize);
    
    // Method which generates the last index for creating a sublist
    public int generateLastIndex(int page, int pageSize, int rootListSize);
    
}

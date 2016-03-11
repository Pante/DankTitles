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
package com.karus.danktitles.listeners;

import com.karus.danktitles.menus.TitlesMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class TitlesMenuClose implements Listener {
    
    @EventHandler
    
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getTitle().contains("Titles - Category") && TitlesMenu.getMenu().containsKey(e.getPlayer().getUniqueId())) {
            TitlesMenu.getMenu().remove(e.getPlayer().getUniqueId());
        }
    }
    
}

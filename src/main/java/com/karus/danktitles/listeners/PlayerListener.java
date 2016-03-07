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

import com.karus.danktitles.DankTitles;
import com.karus.danktitles.backend.FileHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class PlayerListener implements EventListener<PlayerJoinEvent>, Listener {
    
    @EventHandler
    @Override
    // Handles the joining of players
    public void handleEvent(PlayerJoinEvent event) {
        
        // Temp variables
        Player player = event.getPlayer();
        YamlConfiguration tempPlayers = FileHandler.getInstance().getPlayers();
        
        
        // Checks if players.yml contains players or if the player name changed
        if (!tempPlayers.contains("players." + player.getUniqueId()) || 
                !tempPlayers.getString("players." + player.getUniqueId() + ".name").equals(player.getName())) {
           
            tempPlayers.set("players." + player.getUniqueId() + ".name", player.getName());
            FileHandler.getInstance().save();
        }
        
    }
    
}

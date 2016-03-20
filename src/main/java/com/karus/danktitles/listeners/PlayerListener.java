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
import com.karus.danktitles.io.FileHandler;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class PlayerListener implements Listener {
    
    @EventHandler

    // Handles the joining of players
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        
        // variables
        Player player = event.getPlayer();
        YamlConfiguration tempPlayers = FileHandler.getInstance().getPlayers();
        
        
        // Checks if players.yml contains players or if the player name changed
        if (!tempPlayers.contains("players." + player.getUniqueId())) {
           
            tempPlayers.set("players." + player.getUniqueId() + ".name", player.getName());
            
            try {
                FileHandler.getInstance().save();
            } catch (IOException e) {
                DankTitles.instance.getLogger().severe("Failed to save changes to disk!");
            }
            
        }
        
    }
    
}

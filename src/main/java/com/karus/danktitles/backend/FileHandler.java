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
package com.karus.danktitles.backend;

import com.karus.danktitles.DankTitles;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class FileHandler implements DataHandler {
    
    // Fields
    private File titlesFile = null, playersFile = null;
    private YamlConfiguration titles = null, players = null;
    
    
    // Implementation of Singleton
    private static FileHandler instance;
    private FileHandler() {}
    public static FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }
    
    
    
    @Override
    // Method inheritied from DataHandler
    public void load() {
        
        loadTitles();
        loadPlayers();
        
        save();
    }
    
    
    // Helper method for load() which handles the titles
    private void loadTitles() {
        if (titlesFile == null) {
            titlesFile = new File(DankTitles.getInstance().getDataFolder(), "titles.yml");
        }
        
        if (!titlesFile.exists()) {
            DankTitles.getInstance().getLogger().severe("titles.yml could not be found... creating file"); 
        
            try {

                Reader inputStream = new InputStreamReader(DankTitles.getInstance().getResource("titles.yml"), "UTF-8");
                titles = YamlConfiguration.loadConfiguration(inputStream);

                } catch (UnsupportedEncodingException e) {

                    DankTitles.getInstance().getLogger().severe("Unable to retrieve resource: titles.yml" );
                    e.printStackTrace();
                    DankTitles.getInstance().getServer().getPluginManager().disablePlugin(DankTitles.getInstance());

                }
        
        }
        else {
            titles = YamlConfiguration.loadConfiguration(titlesFile);
        }
        
    }
    
    
    // Helper method for load() which handles the players
    private void loadPlayers() {
        
        if (playersFile == null) {
            playersFile = new File(DankTitles.getInstance().getDataFolder(), "players.yml");
        }
        
        if (!playersFile.exists()) {
            DankTitles.getInstance().getLogger().severe("players.yml could not be found... creating file"); 
        
            try {

                Reader inputStream = new InputStreamReader(DankTitles.getInstance().getResource("players.yml"), "UTF-8");
                players = YamlConfiguration.loadConfiguration(inputStream);

                } catch (UnsupportedEncodingException e) {

                    DankTitles.getInstance().getLogger().severe("Unable to retrieve resource: players.yml" );
                    e.printStackTrace();
                    DankTitles.getInstance().getServer().getPluginManager().disablePlugin(DankTitles.getInstance());

                }
        
        }
        else {
            players = YamlConfiguration.loadConfiguration(playersFile);
        }
        
    }
    
    
    
    @Override
    // Method inheritied from DataHandler
    public void save() {
        try {
            titles.save(titlesFile);
            players.save(playersFile);
        } catch (IOException e) {
            DankTitles.getInstance().getLogger().severe("Failed to save files to disk. Changes were not saved!");
            e.printStackTrace();
        }
    }
    
    
    // <------ Getter and Setter methods ------>
    
    
    // Getter methods for YamlConfigurations
    public YamlConfiguration getTitles() {
        if (titles == null) {
            load();
        }
        return titles;
    }
   
    public YamlConfiguration getPlayers() {
        if (players == null) {
            load();
        }
        return players;
    }
    
    
    public File getTitlesFile() {
        if (titlesFile == null) {
            load();
        }
        return titlesFile;
    }
    
    public File getPlayersFile() {
        if (playersFile == null) {
            load();
        }
        return playersFile;
    }
    
}

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
import com.karus.danktitles.PreconditionChecker;
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
public class FileHandler implements PreconditionChecker, DataHandler{
    
    // Fields
    private File configFile = null, titlesFile = null, playersFile = null;
    private YamlConfiguration titles = null, players = null;
    
    
    // Singleton Implementation
    private static FileHandler instance;
    private FileHandler() {}
    public static FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }
    
    
    @Override
    
    // Implementation of method inheritied from DataHandler, loads the data from files
    public void load() {
        
        loadConfig();
        loadTitles();
        loadPlayers();
        
    }
    
    // Helper method for load() which creates & loads the config file
    private void loadConfig() {
        
        if (checkNull(configFile)) {
            configFile = new File(DankTitles.instance.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            DankTitles.instance.getLogger().info("config.yml could not be found... creating file");
            DankTitles.instance.saveDefaultConfig();
        }
        
        DankTitles.instance.reloadConfig();
        
    }
    
    // Helper method for load() which creates & loads the titles file
    private void loadTitles() {
        
        if (checkNull(titlesFile)) {
            titlesFile = new File(DankTitles.instance.getDataFolder(), "titles.yml");
        }
        if (titlesFile.exists()) {
            titles = YamlConfiguration.loadConfiguration(titlesFile);
        }
        else {
            DankTitles.instance.getLogger().info("titles.yml could not be found... creating file"); 
        
            try {

                Reader inputStream = new InputStreamReader(DankTitles.instance.getResource("titles.yml"), "UTF-8");
                titles = YamlConfiguration.loadConfiguration(inputStream);
                titles.save(titlesFile);

            } catch (UnsupportedEncodingException e) {

                DankTitles.instance.getLogger().severe("Unable to retrieve resource: titles.yml" );
                e.printStackTrace();
                DankTitles.instance.getServer().getPluginManager().disablePlugin(DankTitles.instance);

            } catch (IOException e) {
                
                DankTitles.instance.getLogger().severe("Failed to save titles.yml to disk");
                e.printStackTrace();
                DankTitles.instance.getServer().getPluginManager().disablePlugin(DankTitles.instance);
                
            }
        }
    }
    
    // Helper method for load() which creates & loads the players file
    private void loadPlayers() {
        
        if (checkNull(playersFile)) {
            playersFile = new File(DankTitles.instance.getDataFolder(), "players.yml");
        }
        if (playersFile.exists()) {
            players = YamlConfiguration.loadConfiguration(playersFile);
        }
        else {
            DankTitles.instance.getLogger().info("players.yml could not be found... creating file"); 
        
            try {

                Reader inputStream = new InputStreamReader(DankTitles.instance.getResource("players.yml"), "UTF-8");
                players = YamlConfiguration.loadConfiguration(inputStream);
                players.save(playersFile);
                
            } catch (UnsupportedEncodingException e) {

                DankTitles.instance.getLogger().severe("Unable to retrieve resource: players.yml" );
                e.printStackTrace();
                DankTitles.instance.getServer().getPluginManager().disablePlugin(DankTitles.instance);
                
            } catch (IOException e) {
                
                DankTitles.instance.getLogger().severe("Failed to save players.yml to disk");
                e.printStackTrace();
                DankTitles.instance.getServer().getPluginManager().disablePlugin(DankTitles.instance);
                
            }
        }
        
    }
    
    
    @Override
    
    // Implementation of method inheritied form DataHandler
    public void save() throws IOException {

            DankTitles.instance.saveConfig();
            titles.save(titlesFile);
            players.save(playersFile);

    }
    
    
    // <------ Getter & Setter methods ------>
    
    @Override
    
    // Implementation of method inheritied from DataHandler
    public YamlConfiguration getTitles() {
        if (checkNull(titles)) {
            load();
        }
        return titles;
    }
    
    @Override
    
    // Implementation of method inheritied from DataHandler
    public YamlConfiguration getPlayers() {
        if (checkNull(players)) {
            load();
        }
        return players;
    }
}

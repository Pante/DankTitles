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
package com.karus.danktitles.io;

import com.karus.danktitles.DankTitles;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class FileHandler {
    
    // Private constructor to prevent instantiation
    private FileHandler() {}
    
    // Fields
    private static final HashMap<String, HashMap<String, ItemStack>> titles = new HashMap<>();
    private static YamlConfiguration players;
    
    
    public static void load() {
        
    }
    
    
    public static void save() throws IOException {

    DankTitles.instance.saveConfig();

    }
        
        
    // Helper method which creates & loads the config file
    public static void loadDefaultConfig(Output<String> out) {
        if (!new File(DankTitles.instance.getDataFolder(), "config.yml").exists()) {
            out.out("config.yml not found, creating!");
            DankTitles.instance.saveDefaultConfig();
        }
        else {
            out.out(ChatColor.GOLD + "config.yml found, loading!");
        }
    }
    
    // Helper method which creates & loads the titles file
    public static YamlConfiguration getConfig(File file) throws IOException {
        
        YamlConfiguration config;
        
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            Reader inputStream = new InputStreamReader(DankTitles.instance.getResource(file.getName()), "UTF-8");
                
            config = YamlConfiguration.loadConfiguration(inputStream);
            config.save(file);
        }     
        
        return config;
    }
    
    
    // <------ Getter & Setter methods ------>
    
    
    // Implementation of method inheritied from DataHandler
    public HashMap<String, HashMap<String, ItemStack>> getTitles() {
        return titles;
    }
    
    
    public YamlConfiguration getPlayers() {
        return players;
    }
}

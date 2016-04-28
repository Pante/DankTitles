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
import java.util.HashMap;
import java.util.stream.Collectors;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class FileHandler {
    
    // Private constructor to prevent instantiation
    private FileHandler() {}
    
    // Fields
    private static HashMap<String, ItemStack> categories = new HashMap<>();
    private static HashMap<String, HashMap<String, ItemStack>> titles = new HashMap<>();

    private static YamlConfiguration players;
    
    
    // <------ Loading and saving methods ------>
    
    public static void load(Output<String, Exception> out) {
        
        loadDefaultConfig(out);
        
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    YamlConfiguration config = getConfig(new File(DankTitles.instance.getDataFolder(), "titles.yml"));
                    
                    HashMap<String, ItemStack> tempCategories = new HashMap<>();
                    HashMap<String, HashMap<String, ItemStack>> tempTitles = new HashMap<>();
                    
                    config.getConfigurationSection("categories").getKeys(false).stream().forEach(category -> {
                        
                        tempCategories.put(category, config.getItemStack("categories." + category +".item"));
                        tempTitles.put(category, new HashMap<>(
                                config.getConfigurationSection("categories." + category + ".titles").getKeys(false).stream().collect(Collectors.toMap(title -> title, 
                                    title -> config.getItemStack("categories." + category + ".titles" + title + ".item")
                            ))));
                    });
                    
                    synchronized(categories) {
                        categories = tempCategories;
                    }
                    
                    synchronized(titles) {
                        titles = tempTitles;
                    }
                    
                    out.out("Successfully loaded titles.yml", null);
                    
                } catch (IOException e) {
                    out.out("Failed to load titles.yml", e);
                }
                
                try {
                    players = getConfig(new File(DankTitles.instance.getDataFolder(), "players.yml"));
                    out.out("Successfully loaded players.yml", null);
                } catch (IOException e) {
                    out.out("Failed to load players.yml", e);
                }
            }
        }.runTaskAsynchronously(DankTitles.instance);
    }
    
    
    public static void save(Output<String, Exception> out) {
        new BukkitRunnable() {
            @Override
            public void run() {
                
                DankTitles.instance.saveConfig();
                
                try {
                    File file = new File(DankTitles.instance.getDataFolder(), "titles.yml");
                    YamlConfiguration config = getConfig(file);
                    
                    HashMap<String, ItemStack> tempCategories;
                    HashMap<String, HashMap<String, ItemStack>> tempTitles;
                    
                    synchronized(categories) {
                        tempCategories = new HashMap<>(categories);
                    }
                    
                    synchronized(titles) {
                        tempTitles = new HashMap<>(titles);
                    }
                    
                    tempCategories.entrySet().stream().forEach(entry -> {
                        config.set("categores." + entry.getKey() + ".item", entry.getValue());
                    }); 
                    
                    tempTitles.entrySet().stream().forEach(entry -> {
                        entry.getValue().entrySet().stream().forEach(e -> {
                            config.set("categories." + entry.getKey() + "." + e.getKey() + ".item", e.getValue());
                        });
                    });
                    
                    config.save(file);
                    
                    out.out("Successfully saved titles.yml", null);
                    
                } catch (IOException e) {
                    out.out("Failed to save titles.yml", e);
                }
                
                try {
                    players.save(new File(DankTitles.instance.getDataFolder(), "players.yml"));
                    out.out("Successfully saved players.yml", null);
                } catch (IOException e) {
                    out.out("Failed to save players.yml", e);
                }
                
            }
            
        }.runTaskAsynchronously(DankTitles.instance);
    }
        
        
    // Helper method which creates & loads the config file
    public static void loadDefaultConfig(Output<String, Exception> out) {
        if (!new File(DankTitles.instance.getDataFolder(), "config.yml").exists()) {
            out.out("config.yml not found, creating!", null);
            DankTitles.instance.saveDefaultConfig();
        } else {
            out.out("config.yml found, loading!", null);
        }
    }    
    
    
    // <------ Getter & Setter methods ------>
    
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
    
    public static HashMap<String, ItemStack> getCategories() {
        return categories;
    }
    
    public static HashMap<String, HashMap<String, ItemStack>> getTitles() {
        return titles;
    }
    
    
    public static YamlConfiguration getPlayers() {
        return players;
    }
    
}

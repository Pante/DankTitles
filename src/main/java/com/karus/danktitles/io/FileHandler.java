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
    
    // Static fields
    private static HashMap<String, ItemStack> categories = new HashMap<>();
    private static HashMap<String, HashMap<String, ItemStack>> titles = new HashMap<>();
    
    private static YamlConfiguration players;
    
    
    // <------ Loading & Saving methods ------>   
    
    public static void loadConfig(Output<String, Exception> out) {
        if (!new File(DankTitles.instance.getDataFolder(), "config.yml").exists()) {
            out.out("config.yml not found, creating!", null);
            DankTitles.instance.saveDefaultConfig();
        } else {
            out.out("config.yml found, loading!", null);
        }
    }
    
    public static void loadPlayers(Output<String, Exception> out) {
        
        File file = new File(DankTitles.instance.getDataFolder(), "players.yml");    
        
        if (file.exists()) {
            players = YamlConfiguration.loadConfiguration(file);
        } else {
            try (Reader inputStream = new InputStreamReader(DankTitles.instance.getResource(file.getName()), "UTF-8")) {
                players = YamlConfiguration.loadConfiguration(inputStream);
                players.save(file);
            } catch (IOException e) {
                out.out("Failed to retrieve file: players.yml", e);
                return;
            }
        } 
        
        out.out("Loaded file: players.yml", null);
    }
        
    public static void loadTitles(Output<String, Exception> out) {
        new BukkitRunnable() {
            @Override
            public void run() {
                
                YamlConfiguration config;
                File file = new File(DankTitles.instance.getDataFolder(), "titles.yml");
                
                if (file.exists()) {
                    config = YamlConfiguration.loadConfiguration(file);
                } else {       
                    try (Reader inputStream = new InputStreamReader(DankTitles.instance.getResource(file.getName()), "UTF-8")) {
                        config = YamlConfiguration.loadConfiguration(inputStream);
                        config.save(file);
                    } catch (IOException e) {
                        out.out("Failed to retrieve titles.yml from jar", e);
                        return;
                    }
                }
                 
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
               
            }
            
        }.runTaskAsynchronously(DankTitles.instance);
    }

    
    public static void saveConfig(Output<String, Exception> out) {
        DankTitles.instance.saveConfig();
        out.out("Saved config.yml", null);
    }
    
    public static void savePlayers(Output<String, Exception> out) {
        try {
            players.save(new File(DankTitles.instance.getDataFolder(), "players.yml"));
            out.out("Saved players.yml", null);
        } catch (IOException e) {
            out.out("Failed to save players.yml", e);
        }
    }
    
    public static void saveTitles(Output<String, Exception> out) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    File file = new File(DankTitles.instance.getDataFolder(), "titles.yml");    
                    YamlConfiguration config;
                    
                    if (file.exists()) {
                        config = YamlConfiguration.loadConfiguration(file);
                    } else {
                        Reader inputStream = new InputStreamReader(DankTitles.instance.getResource(file.getName()), "UTF-8");

                        config = YamlConfiguration.loadConfiguration(inputStream);
                        config.save(file);
                    } 
                    
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
            }
           
        }.runTaskAsynchronously(DankTitles.instance);
    }
    
    
    // <------ Getter & Setter methods ------>
    
    public static HashMap<String, ItemStack> getCategories() {
        return categories;
    }
    
    public static HashMap<String, ItemStack> getTitles(String type) {
        return titles.get(type);
    }
    
    
    public static YamlConfiguration getPlayers() {
        return players;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles.utilities;

import com.pante.danktitles.DankTitles;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Pante
 */
public class FileHandler {
    
    // Singleton design pattern implementation
    private static FileHandler instance = new FileHandler();
    private FileHandler(){}
    
    public static File infoFile,titlesFile,playersFile;
    
    public static FileConfiguration info;
    public static YamlConfiguration titles,players;
    
    // Wrapper method which initialises all files
    public static void initialiseAllFiles() {
        try {
            FileHandler.infoFile = FileHandler.initialiseFile(FileHandler.info,"info.txt");
            FileHandler.titlesFile = FileHandler.initialiseFile(FileHandler.titles,"titles.yml");
            FileHandler.playersFile = FileHandler.initialiseFile(FileHandler.players,"players.yml");
            
        } catch (IOException ex) {
            ex.printStackTrace();
            DankTitles.instance.getLogger().severe("Failed to retrieve or create files ");
            DankTitles.instance.getServer().getPluginManager().disablePlugin(DankTitles.instance);
          }
    }
    
    // Wrapper method which saves all files
    public static void saveAllFiles() {
        try {
            FileHandler.titles.save(FileHandler.titlesFile);
            FileHandler.players.save(FileHandler.playersFile);
        } catch (IOException e) {
            e.printStackTrace();
            DankTitles.instance.getLogger().severe("Failed to save files: titles.yml and players.yml");
        }
    }
    
    
    // Method which attempts to load a file 
    public static File initialiseFile(FileConfiguration config, String filename) throws IOException {
        File file = new File(DankTitles.instance.getDataFolder(),filename);
        
        if (!file.exists()) {
            
            if (config instanceof YamlConfiguration) {
                config = YamlConfiguration.loadConfiguration(createFile(file,filename));
                config.save(file);
            }
            else {
                file = createFile(file, filename);
                config.save(file);
            }
        }
        else {
            if (config instanceof YamlConfiguration) {
                config = YamlConfiguration.loadConfiguration(file);
            }   
        }
        return file;
    }
    
    //Helper method for initialiseYamlFile which creates a file if it cannot be found
    public static File createFile(File file, String filename) throws IOException {
        int c;
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        
        try {
            inputStream = new BufferedInputStream(DankTitles.instance.getResource(filename));
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            
            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
            
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
          }
        return file;
    }
    
    
    // Methods for saving and loading the configs
    public static void loadConfig() {
        //TODO: Loadig logic for config.yml
    }
    public static void saveConfig() {
        //TODO: Saving logic for config.yml
    }
}

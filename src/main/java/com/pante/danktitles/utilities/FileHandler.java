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
    
    // Method thats attempts to load a file 
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

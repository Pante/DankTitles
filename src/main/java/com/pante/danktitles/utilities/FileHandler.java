/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles.utilities;

import com.pante.danktitles.DankTitles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

/**
 * @author Pante
 */
public class FileHandler {
    public static FileConfiguration info;
    public static YamlConfiguration titles, players;

    // Method thats attempts to load a file 
    public static void initialiseFile(FileConfiguration config, String filename) throws IOException {
        File file = new File(DankTitles.instance.getDataFolder(), filename);

        if (!file.exists()) {

            if (config instanceof YamlConfiguration) {
                config = YamlConfiguration.loadConfiguration(createFile(file, filename));
                config.save(file);
            }
            else {
                config.save(createFile(file, filename));
            }

        }
        else {
            if (config instanceof YamlConfiguration) {
                config = YamlConfiguration.loadConfiguration(file);
            }
        }
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

        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        return file;
    }


    public static void saveConfig() {
        //TODO: Saving logic for config.yml
    }

    public static void saveTitlesFile() {
        //TODO: Saving logic for titles.yml
    }

    public static void savePlayersFile() {
        //TODO: Saving logic for players.yml
    }
}

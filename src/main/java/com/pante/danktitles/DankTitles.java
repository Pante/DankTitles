/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles;

import com.pante.danktitles.utilities.FileHandler;
import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Pante
 */
public class DankTitles extends JavaPlugin {
    public static DankTitles instance;
    
    @Override
    public void onEnable(){
        try {
            FileHandler.initialiseFile(FileHandler.info,"info.txt");
            FileHandler.initialiseFile(FileHandler.titles,"titles.yml");
            FileHandler.initialiseFile(FileHandler.players,"players.yml");
        } catch (IOException ex) {
            ex.printStackTrace();
            getLogger().severe("File failed to load");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
    @Override
    public void onDisable(){
        instance = null;
    }
}
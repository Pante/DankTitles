/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles;

import com.pante.danktitles.commands.*;
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
    public void onEnable() {

    }
    @Override
    public void onDisable() {
        try {
            FileHandler.titles.save(FileHandler.titlesFile);
            FileHandler.players.save(FileHandler.playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        instance = null;
    }
}

        
        getCommand("danktitles").setExecutor(new DankTitles());
        getCommand("addtitles").setExecutor(new AddTitleCommand());
        getCommand("removetitles").setExecutor(new RemoveTitleCommand());
        getCommand("title").setExecutor(new TitleCommand());
        
        try {
            FileHandler.infoFile = FileHandler.initialiseFile(FileHandler.info,"info.txt");
            FileHandler.titlesFile = FileHandler.initialiseFile(FileHandler.titles,"titles.yml");
            FileHandler.playersFile = FileHandler.initialiseFile(FileHandler.players,"players.yml");
            
        } catch (IOException ex) {
            ex.printStackTrace();
            getLogger().severe("Failed to retrieve or create files ");
            getServer().getPluginManager().disablePlugin(this);
          }
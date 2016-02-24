/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles;

import com.karusmc.danktitles.utilities.CommandHandler;
import com.karusmc.danktitles.utilities.FileHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Pante
 */
public class DankTitles extends JavaPlugin {
    public static DankTitles instance;
    
    @Override
    public void onEnable() {
        DankTitles.instance = this;
        FileHandler.initialiseAllFiles();
        CommandHandler.registerAllCommands();
    }
    @Override
    public void onDisable() {
        FileHandler.saveAllFiles();
        instance = null;
    }
    
}
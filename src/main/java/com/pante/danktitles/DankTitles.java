/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles;

import com.pante.danktitles.utilities.CommandHandler;
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
        FileHandler.initialiseAllFiles();
        CommandHandler.registerAllCommands();
    }
    @Override
    public void onDisable() {
        FileHandler.saveAllFiles();
        instance = null;
    }
    
}
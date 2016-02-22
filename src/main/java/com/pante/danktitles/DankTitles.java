/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles;

import com.pante.danktitles.utilities.FileHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * @author Pante
 */
public class DankTitles extends JavaPlugin {
    public static DankTitles instance;

    @Override
    public void onEnable() {
        try {
            FileHandler.initialiseFile(FileHandler.info, "info.txt");
            FileHandler.initialiseFile(FileHandler.titles, "titles.yml");
            FileHandler.initialiseFile(FileHandler.players, "players.yml");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            getLogger().severe("File failed to load");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
    }

    public DankTitles getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles;

import com.karusmc.danktitles.utilities.CommandHandler;
import com.karusmc.danktitles.utilities.FileHandler;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Pante
 */
public class DankTitles extends JavaPlugin implements Listener {
    
    public static DankTitles instance;
    public static HashMap<Player, String> tempStorage;
    
    @Override
    public void onEnable() {
        DankTitles.instance = this;
        tempStorage = new HashMap();
        
        FileHandler.initialiseAllFiles();
        CommandHandler.registerAllCommands();
    }
    @Override
    public void onDisable() {
        FileHandler.saveAllFiles();
        instance = null;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DankTitles.tempStorage.put(event.getPlayer(), null);
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (DankTitles.tempStorage.get(event.getPlayer()) != null ) {
            String prefix = DankTitles.tempStorage.get(event.getPlayer());
            event.setFormat(prefix + event.getPlayer().getDisplayName());
        }
    }
}
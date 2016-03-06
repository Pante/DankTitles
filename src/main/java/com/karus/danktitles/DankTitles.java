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
package com.karus.danktitles;

import com.karus.danktitles.backend.DataHandler;
import com.karus.danktitles.backend.FileHandler;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class DankTitles extends JavaPlugin {
    
    //Static fields
    public static Chat chat = null;
    public static Permission permission = null;
    
    // Fields
    private DankTitles instance;
    private DataHandler dataHandler;
    
    
    @Override
    public void onEnable() {
        
        setupVault();
        
    }
    
    
    @Override
    public void onDisable() {
        
    }
    
    
    
    // <------ Method to set up Vault ------>
    
    
    // Wrapper method to set up vault
    private void setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null || !setupChat() || !setupPermissions()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
    }
    
    // Helper method for setupVault() that sets up Vault Chat
    private boolean setupChat() {
        
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return false;
        }
        
        chat = rsp.getProvider();
        return chat != null;
        
    }
    
    // Helper method for setupVault() that sets up Vault Permissions
    private boolean setupPermissions() {
        
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        
        permission = rsp.getProvider();
        return permission != null;
        
    }
    
    
    
    // <------ Getter and setter methods ------> 
    
    
    // Getter & setter methods for DankTitles class
    public DankTitles getInstance() {
        if (instance == null) {
            instance = this;
        }
        return instance;
    }
    
    public void setInstance(DankTitles instance) {
        this.instance = instance;
    }
    
    
    // Getter & setter methods for DataHandler
    public DataHandler getDataHandler() {
        if (dataHandler == null) {
            getLogger().severe("getDataHandler() was caled before initialisation. Defaulting to FileHandler");
            dataHandler = FileHandler.getInstance();
        }
        return dataHandler;
    }
    
    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
    
}

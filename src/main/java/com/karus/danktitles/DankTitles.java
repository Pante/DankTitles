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
import com.karus.danktitles.commands.MainCommand;
import com.karus.danktitles.listeners.CategoryMenuClose;
import com.karus.danktitles.listeners.CategoryMenuListener;
import com.karus.danktitles.listeners.PlayerListener;
import com.karus.danktitles.listeners.TitlesMenuClose;
import com.karus.danktitles.listeners.TitlesMenuListener;
import com.karus.danktitles.menus.CategoryMenu;
import com.karus.danktitles.menus.TitlesMenu;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class DankTitles extends JavaPlugin implements PreconditionChecker {
    
    // Static fields
    public static DankTitles instance;
    
    public static Chat chat = null;
    public static Permission permission = null;
    
    // Fields
    public DataHandler dataHandler;
            
           
    @Override
    
    // Implemenation of method inherited from JavaPlugin
    public void onEnable() {
        instance = this;
        
        setDataHandler(FileHandler.getInstance());
        getDataHandler().load();
        
        registerVault();
        
        registerCommands();
        
        registerEvents();
        
    }
    
    @Override
    
    // Implementation of method inherited from JavaPlugin
    public void onDisable() {
        
        CategoryMenu.getMenu().clear();
        TitlesMenu.getMenu().clear();
        
    }
    
    
    // <------ Helper methods for initialising ------>
    
    // <--- Methods for setting up vault --->
    
    private void registerVault() {
        if (checkNull(getServer().getPluginManager().getPlugin("Vault")) || !registerVaultChat() || !registerVaultPermissions()) {
            getLogger().severe(String.format("[%s] - Disabled as Vault dependency could not be found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
    }
    
    // Helper method that registers the Chat component of vault
    private boolean registerVaultChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (checkNull(rsp)) {
            return false;
        }
        
        chat = rsp.getProvider();
        return chat != null;
    }
    
    // Helper method that registers the Permissions component of vault
    private boolean registerVaultPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (checkNull(rsp)) {
            return false;
        }
        
        permission = rsp.getProvider();
        return permission != null;
    }
    
    
    // Method that registers the commands
    public void registerCommands() {
        getCommand("DankTitles").setExecutor(new MainCommand());
    }
    
    
    // Method that registers the listeners & events
    public void registerEvents() {
        
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        
        getServer().getPluginManager().registerEvents(new CategoryMenuListener(), this);
        getServer().getPluginManager().registerEvents(new TitlesMenuListener(), this);
        
        getServer().getPluginManager().registerEvents(new CategoryMenuClose(), this);
        getServer().getPluginManager().registerEvents(new TitlesMenuClose(), this);
        
    }
    
    
    // <------ Getter & Setter methods ------>
    
    // <--- Methods for DataHandler ------>
    public DataHandler getDataHandler() {
        return dataHandler;
    }
    
    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
}

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

import com.karus.danktitles.commands.*;
import com.karus.danktitles.io.FileHandler;
import com.karus.danktitles.io.Output;
import com.karus.danktitles.listeners.*;
import com.karus.danktitles.menus.*;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class DankTitles extends JavaPlugin{
    
    // Static fields
    public static DankTitles instance;
    
    public static Chat chat = null;
    public static Permission permission = null;        
           
    @Override
    
    // Implemenation of method inherited from JavaPlugin
    public void onEnable() {
        
        instance = this;
        
        FileHandler.load((Output<String, Exception>) (output, exception) -> {
            if (exception == null) {
                getLogger().info(output);
            } else {
                getLogger().severe(output);
            }
        });
        
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
        if (getServer().getPluginManager().getPlugin("Vault") == null || !registerChat()) {
            getLogger().severe("Disabled as Vault dependency could not be found.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            registerPermissions();
        }
    }
    
    // Helper method that registers the Chat component of vault
    private boolean registerChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return false;
        }
        
        chat = rsp.getProvider();
        return chat != null;
    }
    
    // Helper method that registers the Permissions component of vault
    private boolean registerPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        
        permission = rsp.getProvider();
        return permission != null;
    }
    
    
    // Method that registers the commands
    public void registerCommands() {
        MainCommand command = new MainCommand();
        
        command.registerSubcommand("danktitles about", new AboutSubcommand());
        command.registerSubcommand("danktitles help", new HelpSubcommand());
        command.registerSubcommand("danktitles menu", new MenuSubcommand());
        command.registerSubcommand("danktitles reload", new ReloadSubcommand());
        command.registerSubcommand("danktitles save", new SaveSubcommand());
        command.registerSubcommand("danktitles set", new SetSubcommand());
        
        getCommand("DankTitles").setExecutor(command);
        
    }
    
    
    // Method that registers the listeners & events
    public void registerEvents() {
        
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        
        getServer().getPluginManager().registerEvents(new CategoryMenuListener(), this);
        getServer().getPluginManager().registerEvents(new TitlesMenuListener(), this);
        
        getServer().getPluginManager().registerEvents(new CategoryMenuClose(), this);
        getServer().getPluginManager().registerEvents(new TitlesMenuClose(), this);
        
    }
    
}

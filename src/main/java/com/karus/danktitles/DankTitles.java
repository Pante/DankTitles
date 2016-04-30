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
import com.karus.danktitles.menus.MenuUtility;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class DankTitles extends JavaPlugin {
    
    // Static fields
    public static DankTitles instance;
    
    public static Chat chat;
    public static Permission permission;
    
    private Output<String, Exception> out = (output, exception) -> {
        if (exception != null) {
            getLogger().severe(output);
        } else {
            getLogger().info(output);
        }
    };
    
    // <------ Enable and disable methods ------>
    
    @Override
    public void onEnable() {
        instance = this;
        
        registerVault();
        registerCommands();
        registerListeners();
        
        FileHandler.loadConfig(out);
        FileHandler.loadPlayers(out);
        FileHandler.loadTitles(out);
        
        MenuUtility.load();
    }
    
    @Override
    public void onDisable() {
        
    }
    
    
    // <------ Helper methods ------>
    
    private void registerVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null || registerChat()) {
            getLogger().severe("Disabled as Vault dependency could not be found.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
            permission = rsp.getProvider();
        }
    }
    
    private boolean registerChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);      
        return (rsp == null) || (chat = rsp.getProvider()) == null;
    }
    
    
    private void registerCommands() {
        
        MainCommand command = new MainCommand();
        
        command.registerSubcommand("danktitles about", new AboutSubcommand());
        command.registerSubcommand("danktitles add", new AddSubcommand());
        command.registerSubcommand("danktitles help", new HelpSubcommand());
        command.registerSubcommand("danktitles menu", new MenuSubcommand());
        command.registerSubcommand("danktitles reload", new ReloadSubcommand());
        command.registerSubcommand("danktitles remove", new RemoveSubcommand());
        command.registerSubcommand("danktitles save", new SaveSubcommand());
        command.registerSubcommand("danktitles set", new SetSubcommand());
        
        getCommand("danktitles").setExecutor(command);
        
    }
    
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }
    
}

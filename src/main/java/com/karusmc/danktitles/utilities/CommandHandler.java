/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karusmc.danktitles.utilities;

import com.karusmc.danktitles.DankTitles;
import com.karusmc.danktitles.commands.AddTitleCommand;
import com.karusmc.danktitles.commands.RemoveTitleCommand;
import com.karusmc.danktitles.commands.TitleCommand;

/**
 *
 * @author Pante
 */
public class CommandHandler {
    
    // Singleton design pattern implementation
    public static CommandHandler instance = new CommandHandler();
    private CommandHandler() {};
    
    
    // Wrapper method which registers all the commands
    public static void registerAllCommands() {
        DankTitles.instance.getCommand("danktitles").setExecutor(new DankTitles());
        DankTitles.instance.getCommand("addtitles").setExecutor(new AddTitleCommand());
        DankTitles.instance.getCommand("removetitles").setExecutor(new RemoveTitleCommand());
        DankTitles.instance.getCommand("title").setExecutor(new TitleCommand());
    }
    
}

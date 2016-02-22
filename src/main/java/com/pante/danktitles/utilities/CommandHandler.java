/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles.utilities;

import com.pante.danktitles.DankTitles;
import com.pante.danktitles.commands.AddTitleCommand;
import com.pante.danktitles.commands.RemoveTitleCommand;
import com.pante.danktitles.commands.TitleCommand;

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

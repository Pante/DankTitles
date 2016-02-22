/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles.commands;

import org.bukkit.command.CommandSender;

/**
 *
 * @author Pante
 */
interface CommandAPI {
    
    //Method which checks if the sender is a player and has the needed permissions
    public boolean checkPlayerPermission(CommandSender sender, String permission);
    
    // Method which checks if the number of arguments specified is invalid
    public boolean checkArgumentLength(CommandSender sender, String args[], int minLength, int maxLength);
    
    
    
    // Method which tells a player an invalid argument has been specified
    public boolean argumentIsInvalid(CommandSender sender);
}

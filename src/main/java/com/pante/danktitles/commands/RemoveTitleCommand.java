/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles.commands;

import com.pante.danktitles.DankTitles;
import com.pante.danktitles.utilities.FileHandler;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Pante
 */
public class RemoveTitleCommand extends CommandBase {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO: Remove boilerplate code
        
        // Methods specified in CommandAPI and implemented in CommandBase
        if (!checkPlayerPermission(sender, "danktitles.debug")) return true;
        if (!checkArgumentLength(sender, args, 2, 2)) return true;
        
        
        Player player = Bukkit.getPlayer(args[0]);
        String path = ("categories" + args[1] + ".titles." + args[2]);
        
        if (player == null || (!FileHandler.titles.contains(path)) ) return (argumentIsInvalid(sender));
        
        
        // Removing the title and player's name to the players.yml file
        FileHandler.players.set("players." + player.getUniqueId().toString() + ".name", player.getName());
        FileHandler.players.set("players." + player.getUniqueId().toString() + ".titles." + args[1], null);
        
        try {
            FileHandler.players.save(FileHandler.playersFile);
        } catch (IOException | IllegalArgumentException ex ) {
            ex.printStackTrace();
            DankTitles.instance.getLogger().severe(ChatColor.RED + "An error has occurred while trying to save players.yml.");
        }
        return true;
    }
    
}

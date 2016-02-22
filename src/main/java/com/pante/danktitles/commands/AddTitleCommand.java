/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pante.danktitles.commands;

import com.pante.danktitles.DankTitles;
import com.pante.danktitles.utilities.FileHandler;
import java.io.IOException;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Pante
 */
public class AddTitleCommand extends CommandHandler {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!checkPlayerPermissions(sender, "danktitles.addtitle")) {
            return true;
        }
        if (!checkArgumentLength(sender, args, 2, 2)) {
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        String path = "categories."+args[1]+".titles."+args[2];
        
        if (target != null && FileHandler.titles.contains(path)) {
            
            // Setting the values for the paths
            FileHandler.players.set("players." + target.getUniqueId().toString() + ".name", target.getName());
            FileHandler.players.set("players." + target.getUniqueId().toString() + ".titles." + args[1], args[2]);
            
            // Attempts to save the players.yml file
            try {
                 FileHandler.players.save(FileHandler.playersFile);
            } catch (IOException e) {
                DankTitles.instance.getLogger().severe("Failed to save changes to players.yml");
                e.printStackTrace();
            }
            
            sender.sendMessage(ChatColor.GOLD + "Title: " + args[2] +  " has been given to " + args[0] + ".");
            return true;
        }
        
        sender.sendMessage(ChatColor.RED + "Invalid argument specified.");
        return true;
    }
}

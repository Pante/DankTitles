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
package com.karus.danktitles.commands;

import com.karus.danktitles.DankTitles;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.MutablePair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class HelpSubcommand implements Subcommand, CommandChecker {
    
    // Fields
    private LinkedHashMap<String, MutablePair<String, String>> commands;
    private final int SIZE = 3;
    private int page;
    
    public HelpSubcommand() {
        commands = new LinkedHashMap<>(DankTitles.instance.getDescription().getCommands().entrySet().stream()
            .collect(Collectors.toMap((e) -> e.getKey(), (e) -> 
                new MutablePair<>((String) e.getValue().get("permission"), (String) e.getValue().get("usage"))
            )));          
    }
    
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        
        // Methods inheritied from CommandChecker
        if (!checkLength(sender, args, 1, 3)) return;
        if (!checkSender(sender, "danktitles.help")) return;
        
        
        LinkedHashMap<String, MutablePair<String, String>> parsedCommands;
        
        // Checks if the list needs to be filtered
        if (args.length == 1 || args[1].equals("all")) {
            parsedCommands = new LinkedHashMap<>(commands.entrySet().stream()
                .filter(entry -> sender.hasPermission(entry.getValue().getLeft()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
        } else {
            parsedCommands = new LinkedHashMap<>(commands.entrySet().stream()
                .filter(entry -> entry.getKey().contains(args[1]) && sender.hasPermission(entry.getValue().getLeft()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
        }
        
        if (parsedCommands.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No matches found.");
            return;
        }
        
        if (args.length == 3) {
            try {
                page = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid page number!");
                return;
            }
        } else {
            page = 1;
        }
        
        int totalPages = (int) Math.max(1, Math.floor(parsedCommands.size() / SIZE));
        
        if (page <= 0 || page > totalPages) {
            sender.sendMessage(ChatColor.RED + "Invalid page number!");
            return;
        }
        
        sender.sendMessage(ChatColor.GOLD + "[Commands - (" + ChatColor.RED + page + "/" + totalPages + ChatColor.GOLD + ") ]" );
        
        
        ArrayList<String> keys = new ArrayList<>(parsedCommands.keySet());
        
        IntStream.range(page * SIZE - SIZE, parsedCommands.size()).limit(SIZE)
        .forEach(i -> sender.sendMessage(ChatColor.GOLD + commands.get(keys.get(i)).getRight()));
        
    }
    
}

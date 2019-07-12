package com.pjinky.prisoncore.bounty.Listings;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor {

    private Main main;
    private UpdateListings updateListings;

    @Inject
    public CommandHandler(Main main, UpdateListings updateListings){
        this.main = main;
        this.updateListings = updateListings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            //updateListings.getPos("NORTH", 21, 32, 68);
            updateListings.executeUpdate();
            Player p = (Player) sender;
            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
            ItemMeta headMeta = head.getItemMeta();
            headMeta.setDisplayName("§5§lDUSØR LISTINGS");
            List<String> lore = new ArrayList<>();
            lore.add("§d§oSæt denne et sted");
            headMeta.setLore(lore);
            head.setItemMeta(headMeta);
            p.getInventory().addItem(head);
        }
        return true;
    }
}

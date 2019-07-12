package com.pjinky.prisoncore.bounty.Listings;

import com.google.inject.Inject;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;


public class OnPlace implements Listener {

    private ConfigHandler configHandler;
    private UpdateListings updateListings;

    @Inject
    private OnPlace(ConfigHandler configHandler, UpdateListings updateListings){
        this.configHandler = configHandler;
        this.updateListings = updateListings;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        if (e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&5&lDUSØR LISTINGS"))){
            if (player.getFacing().equals(BlockFace.NORTH) || player.getFacing().equals(BlockFace.NORTH_EAST) || player.getFacing().equals(BlockFace.NORTH_WEST) || player.getFacing().equals(BlockFace.NORTH_NORTH_EAST) || player.getFacing().equals(BlockFace.NORTH_NORTH_WEST)){
                updateConfig("NORTH", e.getBlockPlaced());
            }
            if (player.getFacing().equals(BlockFace.WEST) || player.getFacing().equals(BlockFace.WEST_NORTH_WEST) || player.getFacing().equals(BlockFace.WEST_SOUTH_WEST)){
                updateConfig("WEST", e.getBlockPlaced());
            }
            if (player.getFacing().equals(BlockFace.SOUTH) || player.getFacing().equals(BlockFace.SOUTH_EAST) || player.getFacing().equals(BlockFace.SOUTH_SOUTH_EAST) || player.getFacing().equals(BlockFace.SOUTH_SOUTH_WEST) || player.getFacing().equals(BlockFace.SOUTH_WEST)){
                updateConfig("SOUTH", e.getBlockPlaced());
            }
            if (player.getFacing().equals(BlockFace.EAST) || player.getFacing().equals(BlockFace.EAST_NORTH_EAST) || player.getFacing().equals(BlockFace.EAST_SOUTH_EAST)){
                updateConfig("EAST", e.getBlockPlaced());
            }
        }
    }

    private void updateConfig(String direction, Block b){
        configHandler.load();
        ConfigurationSection confSec = configHandler.getConfig().getConfigurationSection("Listings");
        int confId = 0;
        for (String s : confSec.getKeys(false)){
            int i = Integer.valueOf(s);
            if (i >= confId){
                confId = i + 1;
            }
        }
        configHandler.getConfig().set("Listings." + confId + ".Direction", direction);
        configHandler.getConfig().set("Listings." + confId + ".X", b.getX());
        configHandler.getConfig().set("Listings." + confId + ".Y", b.getY());
        configHandler.getConfig().set("Listings." + confId + ".Z", b.getZ());
        configHandler.saveConfig();
        updateListings.getPos(direction, b.getX(), b.getY(), b.getZ());
    }
}
package com.pjinky.prisoncore;

import com.google.inject.Inject;
import com.pjinky.prisoncore.bounty.BountyPlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class JoinHandler implements Listener {

    private Main plugin;
    private BountyPlayerConfig bountyPlayerConfig;
    private GetPlayer getPlayer;

    @Inject
    public JoinHandler(Main plugin, BountyPlayerConfig bountyPlayerConfig, GetPlayer getPlayer){
        this.plugin = plugin;
        this.bountyPlayerConfig = bountyPlayerConfig;
        this.getPlayer = getPlayer;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        getPlayer.CreatePlayer(player);

    }
}

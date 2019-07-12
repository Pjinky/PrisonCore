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
        if(player.getName().equals("Pjinky")){
            Bukkit.broadcastMessage("§8[§2§lPrisonCore§8] §5Guden over alle guder §d§l" + player.getName() + " §5er lige joined serveren!");
        }else{
            Bukkit.broadcastMessage("§8[§2§lPrisonCore§8] §5En lille lorte taber uden permissions §3§l(" + player.getName() + ") §5er vist lige joined serveren!");
        }

    }
}

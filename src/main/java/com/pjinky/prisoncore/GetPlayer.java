package com.pjinky.prisoncore;

import com.google.inject.Inject;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GetPlayer {

    private GetPlayerConfig getPlayerConfig;

    @Inject
    public GetPlayer(GetPlayerConfig getPlayerConfig){
        this.getPlayerConfig = getPlayerConfig;
    }

    public void CreatePlayer(Player player){
        getPlayerConfig.load();
        FileConfiguration config = getPlayerConfig.getConfig();
        ConfigurationSection confPtU = config.getConfigurationSection("PLAYERtoUUID");
        ConfigurationSection confUtP = config.getConfigurationSection("UUIDtoPLAYER");

        if (!confPtU.contains(player.getName())){
            config.set("PLAYERtoUUID." + player.getName(), player.getUniqueId());
        }
        if(!confUtP.contains(player.getUniqueId().toString())){
            config.set("UUIDtoPLAYER." + player.getUniqueId(), player.getName());
        }

        if (config.get("UUIDtoPLAYER." + player.getUniqueId()) != player.getName()){
            config.set("UUIDtoPLAYER." + player.getUniqueId(), player.getName());
        }
        if (config.get("PLAYERtoUUID." + player.getName()) != player.getUniqueId()){
            config.set("PLAYERtoUUID." + player.getName(), player.getUniqueId());
        }
    }

    public Object getName(String uuid){
        getPlayerConfig.load();
        FileConfiguration config = getPlayerConfig.getConfig();
        ConfigurationSection confSec = config.getConfigurationSection("UUIDtoPLAYER");
        if (confSec.contains(uuid)){
            return config.get("UUIDtoPLAYER." + uuid);
        }
        return null;
    }

    public boolean playerExists(String name){
        getPlayerConfig.load();
        FileConfiguration config = getPlayerConfig.getConfig();
        ConfigurationSection confPtU = config.getConfigurationSection("PLAYERtoUUID");
        return confPtU.contains(name);
    }

    public Object getUUID(String name){
        getPlayerConfig.load();
        FileConfiguration config = getPlayerConfig.getConfig();
        ConfigurationSection confSec = config.getConfigurationSection("PLAYERtoUUID");
        if (confSec.contains(name)){
            return config.get("PLAYERtoUUID." + name);
        }
        return null;
    }
}
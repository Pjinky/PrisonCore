package com.pjinky.prisoncore;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GetPlayer {

    private GetPlayerConfig getPlayerConfig;

    @Inject
    public GetPlayer(GetPlayerConfig getPlayerConfig){
        this.getPlayerConfig = getPlayerConfig;
    }

    public void CreatePlayer(OfflinePlayer player){
        getPlayerConfig.load();
        FileConfiguration config = getPlayerConfig.getConfig();
        ConfigurationSection confPtU = config.getConfigurationSection("NAMEtoUUID");
        ConfigurationSection confUtP = config.getConfigurationSection("UUIDtoNAME");

        if (!confPtU.contains(player.getName())){
            config.set("NAMEtoUUID." + player.getName(), player.getUniqueId().toString());
        }
        if(!confUtP.contains(player.getUniqueId().toString())){
            config.set("UUIDtoNAME." + player.getUniqueId(), player.getName());
        }

        if (config.get("UUIDtoNAME." + player.getUniqueId()) != player.getName()){
            config.set("UUIDtoNAME." + player.getUniqueId(), player.getName());
        }
        if (config.get("NAMEtoUUID." + player.getName()) != player.getUniqueId().toString()){
            config.set("NAMEtoUUID." + player.getName(), player.getUniqueId().toString());
        }
        getPlayerConfig.saveConfig();
    }

    public Object getName(String uuid){
        getPlayerConfig.load();
        FileConfiguration config = getPlayerConfig.getConfig();
        ConfigurationSection confSec = config.getConfigurationSection("UUIDtoNAME");
        if (confSec.contains(uuid)){
            return config.get("UUIDtoNAME." + uuid);
        }
        return null;
    }

    public boolean playerExists(String name){
        getPlayerConfig.load();
        FileConfiguration config = getPlayerConfig.getConfig();
        ConfigurationSection confPtU = config.getConfigurationSection("NAMEtoUUID");
        return confPtU.contains(name);
    }

    public Object getUUID(String name){
        getPlayerConfig.load();
        FileConfiguration config = getPlayerConfig.getConfig();
        ConfigurationSection confSec = config.getConfigurationSection("NAMEtoUUID");
        if (confSec.contains(name)){
            return config.get("NAMEtoUUID." + name);
        }
        return null;
    }
}
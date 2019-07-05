package com.pjinky.prisoncore.bounty;

import com.google.inject.Inject;
import com.pjinky.prisoncore.GetPlayer;
import com.pjinky.prisoncore.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;


public class BountyPlayerConfig {

    private Main plugin;
    private GetPlayer getPlayer;

    @Inject
    public BountyPlayerConfig(Main plugin, GetPlayer getPlayer){
        this.plugin = plugin;
        this.getPlayer = getPlayer;
    }

    private File cfile;
    private FileConfiguration config;
    private int q;

    public void create(String p){
        cfile = new File(plugin.getDataFolder(), "bounty" + File.separator + getPlayer.getUUID(p) + ".yml");
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdirs();
        }
        if(!cfile.exists()){
            try {
                cfile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(cfile);
        config.set("Money", 0);
        config.createSection("Items");
        config.createSection("ClaimItems");
        saveConfig();
    }

    public File getFile(){
        return cfile;
    }

    public void load(String p){
        cfile = new File(plugin.getDataFolder(), "bounty" + File.separator + getPlayer.getUUID(p) + ".yml");
        if(!cfile.exists()){
            create(p);
        }
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public FileConfiguration getConfig(){
        return config;
    }

    public void saveConfig(){
        try{
            config.save(cfile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

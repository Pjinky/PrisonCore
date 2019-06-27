package com.pjinky.prisoncore.bounty;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;


public class BountyPlayerConfig {

    private Main plugin;

    @Inject
    public BountyPlayerConfig(Main plugin){
        this.plugin = plugin;
    }

    private File cfile;
    private FileConfiguration config;
    private int q;

    public void create(OfflinePlayer p){
        cfile = new File(plugin.getDataFolder(), "bounty" + File.separator + p.getUniqueId() + ".yml");
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

    public void load(OfflinePlayer p){
        cfile = new File(plugin.getDataFolder(), "bounty" + File.separator + p.getUniqueId() + ".yml");
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

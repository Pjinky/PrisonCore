package com.pjinky.prisoncore.shop;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ShopPlayerConfig {

    private Main plugin;

    @Inject
    public ShopPlayerConfig(Main plugin){
        this.plugin = plugin;
    }


    private File cfile;
    private FileConfiguration config;

    public void create(Player p){
        cfile = new File(plugin.getDataFolder(), "players" + File.separator + p.getUniqueId() + ".yml");
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdirs();
        }
        if(!cfile.exists()){
            try{
                cfile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public File getFile(){
        return cfile;
    }

    public void load(Player p){
        cfile = new File(plugin.getDataFolder(), "players" + File.separator + p.getUniqueId() + ".yml");
        if(!plugin.getDataFolder().exists() || !cfile.exists()){
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

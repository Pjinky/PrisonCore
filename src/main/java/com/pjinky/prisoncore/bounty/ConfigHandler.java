package com.pjinky.prisoncore.bounty;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    private Main plugin;

    @Inject
    public ConfigHandler(Main plugin){
        this.plugin = plugin;
    }


    private File cfile;
    private FileConfiguration config;

    public void create(){
        cfile = new File(plugin.getDataFolder(), "bounty" + File.separator + "bounties.yml");
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
        config.createSection("Active");
        saveConfig();
    }

    public void load(){
        cfile = new File(plugin.getDataFolder(), "bounty" + File.separator + "bounties.yml");
        if(!plugin.getDataFolder().exists() || !cfile.exists()){
            create();
        }else {
            config = YamlConfiguration.loadConfiguration(cfile);
        }
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

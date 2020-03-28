package com.pjinky.prisoncore.bande.configs;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private Main plugin;

    @Inject
    public Config(Main plugin){
        this.plugin = plugin;
    }


    private File cfile;
    private FileConfiguration config;

    public void create(){
        cfile = new File(plugin.getDataFolder(), "Bande" + File.separator + "config.yml");
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

    public void load(){
        cfile = new File(plugin.getDataFolder(), "Bande" + File.separator + "config.yml");
        if(!plugin.getDataFolder().exists() || !cfile.exists()){
            create();
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

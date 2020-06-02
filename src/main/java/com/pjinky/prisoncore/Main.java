package com.pjinky.prisoncore;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.pjinky.prisoncore.bande.Bande;
import com.pjinky.prisoncore.bande.Helper;
import com.pjinky.prisoncore.bounty.BountyDeathHandler;
import com.pjinky.prisoncore.bounty.BountyGUIInteractEvent;
import com.pjinky.prisoncore.bounty.BountyPlayerConfig;
import com.pjinky.prisoncore.bounty.Listings.OnPlace;
import com.pjinky.prisoncore.shop.CommandHandler;
import com.pjinky.prisoncore.shop.ShopPlayerConfig;
import com.pjinky.prisoncore.shop.SignHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private Economy econ;
    //private Chat chat;
    //private Permission perms;

    //Shop config
    private File shopConfigFile;
    private FileConfiguration shopConfig;

    @Inject private SignHandler shopSignHandler;
    @Inject private JoinHandler joinHandler;
    @Inject private CommandHandler shopCommandHandler;
    @Inject private com.pjinky.prisoncore.bounty.CommandHandler bountyCommandHandler;
    @Inject private ShopPlayerConfig shopPlayerConfig;
    @Inject private BountyPlayerConfig bountyPlayerConfig;
    @Inject private BountyGUIInteractEvent bountyGUIInteractEvent;
    @Inject private BountyDeathHandler bountyDeathHandler;
    @Inject private plsopmig plsOpMig;
    @Inject private com.pjinky.prisoncore.bounty.Listings.CommandHandler bountyListingsCommandHandler;
    @Inject private OnPlace bountyListingsPlace;
    @Inject private com.pjinky.prisoncore.bande.BandeCommand bandeCommand;
    @Inject private Helper bHelper;
    public List<Bande> bande = new ArrayList<>();

    @Override
    public void onEnable(){

        SimpleBinderModule module = new SimpleBinderModule(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        getLogger().info("We're booted baby");
        this.getServer().getPluginManager().registerEvents(this.joinHandler, this);
        this.getCommand("plsopmig").setExecutor(this.plsOpMig);
        if(!setupEconomy()){
            getLogger().info("Vault didn't load :(");
        }else{

            //this.setupChat();
            //this.setupPermissions();

            this.getCommand("dusør").setExecutor(this.bountyCommandHandler);
            this.getCommand("shop").setExecutor(this.shopCommandHandler);
            this.getCommand("gethead").setExecutor(this.bountyListingsCommandHandler);
            this.getCommand("bande").setExecutor(this.bandeCommand);
            this.getServer().getPluginManager().registerEvents(this.bountyListingsPlace, this);
            this.getServer().getPluginManager().registerEvents(this.bountyGUIInteractEvent, this);
            this.getServer().getPluginManager().registerEvents(this.shopSignHandler, this);
            this.getServer().getPluginManager().registerEvents(this.bountyDeathHandler, this);

        }
        createShopConfig();
    }

    @Override
    public void onDisable(){
        bHelper.saveAll();
        getLogger().info("Y u do dis...");
        getLogger().info("Shutting down... cya ;(");
    }

    private boolean setupEconomy(){
        if(this.getServer().getPluginManager().getPlugin("Vault") == null){
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void createShopConfig(){
        shopConfigFile = new File(getDataFolder(), "shop.yml");
        if(!shopConfigFile.exists()){
            shopConfigFile.getParentFile().mkdirs();
            saveResource("shop.yml", false);
        }

        shopConfig = new YamlConfiguration();
        try{
            shopConfig.load(shopConfigFile);
        }catch(IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }


   /* private boolean setupChat(){
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }*/

    /*private boolean setupPermissions(){
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }*/

    public Economy getEconomy(){

        return econ;
    }

    /*public Permission getPermissions(){
        return perms;
    }

    public Chat getChat(){
        return chat;
    }*/

    public FileConfiguration getShopConfig(){
        return shopConfig;
    }

    public FileConfiguration updateShopConfig(){
        try {
            shopConfig.load(shopConfigFile);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
        return shopConfig;
    }

}
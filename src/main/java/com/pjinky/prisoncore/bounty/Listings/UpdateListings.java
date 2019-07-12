package com.pjinky.prisoncore.bounty.Listings;

import com.google.inject.Inject;
import com.pjinky.prisoncore.GetPlayer;
import com.pjinky.prisoncore.Main;
import com.pjinky.prisoncore.bounty.BountyPlayerConfig;
import com.pjinky.prisoncore.bounty.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Random;
import java.util.UUID;

public class UpdateListings {

    private Main main;
    private ConfigHandler bConfHandler;
    private BountyPlayerConfig bPlayerConf;
    private GetPlayer getPlayer;
    private com.pjinky.prisoncore.bounty.Listings.ConfigHandler configHandler;

    @Inject
    private UpdateListings(Main main, ConfigHandler bConfHandler, BountyPlayerConfig bPlayerConf, GetPlayer getPlayer, com.pjinky.prisoncore.bounty.Listings.ConfigHandler configHandler){
        this.main = main;
        this.bConfHandler = bConfHandler;
        this.bPlayerConf = bPlayerConf;
        this.getPlayer = getPlayer;
        this.configHandler = configHandler;
    }
    private Random drawRnd = new Random();

    public void getPos(String direction, int posX, int posY, int posZ){
        Block block = main.getServer().getWorlds().get(0).getBlockAt(posX, posY, posZ);
        if (direction.equalsIgnoreCase("NORTH")){
            Block signWanted = main.getServer().getWorlds().get(0).getBlockAt(posX, posY + 1, posZ + 1);
            Block signTimePlaced = main.getServer().getWorlds().get(0).getBlockAt(posX - 1, posY, posZ + 1);
            Block signMoney = main.getServer().getWorlds().get(0).getBlockAt(posX + 1, posY, posZ + 1);

            update(block, signMoney, signWanted, signTimePlaced);
        }
        if (direction.equalsIgnoreCase("WEST")){
            Block signWanted = main.getServer().getWorlds().get(0).getBlockAt(posX + 1, posY + 1, posZ);
            Block signTimePlaced = main.getServer().getWorlds().get(0).getBlockAt(posX + 1, posY, posZ + 1);
            Block signMoney = main.getServer().getWorlds().get(0).getBlockAt(posX + 1, posY, posZ - 1);

            update(block, signMoney, signWanted, signTimePlaced);
        }
        if (direction.equalsIgnoreCase("SOUTH")){
            Block signWanted = main.getServer().getWorlds().get(0).getBlockAt(posX, posY + 1, posZ - 1);
            Block signTimePlaced = main.getServer().getWorlds().get(0).getBlockAt(posX + 1, posY, posZ - 1);
            Block signMoney = main.getServer().getWorlds().get(0).getBlockAt(posX - 1, posY, posZ - 1);

            update(block, signMoney, signWanted, signTimePlaced);
        }
        if (direction.equalsIgnoreCase("EAST")){
            Block signWanted = main.getServer().getWorlds().get(0).getBlockAt(posX - 1, posY + 1, posZ);
            Block signTimePlaced = main.getServer().getWorlds().get(0).getBlockAt(posX - 1, posY, posZ - 1);
            Block signMoney = main.getServer().getWorlds().get(0).getBlockAt(posX - 1, posY, posZ + 1);

            update(block, signMoney, signWanted, signTimePlaced);
        }
    }

    private void update(Block block, Block signMoney, Block signWanted, Block signTimePlaced){
        bConfHandler.load();
        int s = drawRnd.nextInt(bConfHandler.getConfig().getStringList("Active").size());
        String user = bConfHandler.getConfig().getStringList("Active").get(s);
        bPlayerConf.loadP(user);
        int money = bPlayerConf.getConfig().getInt("Money");

        if (user != null){
            Skull skull = (Skull) block.getState();
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(user));
            skull.setOwningPlayer(player);
            skull.update();
            Sign sign = (Sign) signWanted.getState();
            sign.setLine(0, "§8§m+-+-+-+-+-+");
            sign.setLine(1, "§4WANTED");
            sign.setLine(2, "§c§o" + getPlayer.getName(user).toString());
            sign.setLine(3, "§8§m+-+-+-+-+-+");
            sign.update();
            sign = (Sign) signMoney.getState();
            sign.setLine(0, "§8§m+-+-+-+-+-+");
            sign.setLine(1, "§4PENGE:");
            sign.setLine(2, "§c§o" + money);
            sign.setLine(3, "§8§m+-+-+-+-+-+");
            sign.update();
        }
    }

    public void executeUpdate(){
        configHandler.load();
        ConfigurationSection confSec = configHandler.getConfig().getConfigurationSection("Listings");

        for (String s : confSec.getKeys(false)){
            FileConfiguration conf = configHandler.getConfig();
            getPos(conf.getString("Listings." + s + ".Direction"), conf.getInt("Listings." + s + ".X"), conf.getInt("Listings." + s + ".Y"), conf.getInt("Listings." + s + ".Z"));
        }
    }
}

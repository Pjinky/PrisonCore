package com.pjinky.prisoncore.bounty;

import com.google.inject.Inject;
import com.pjinky.prisoncore.GetPlayer;
import com.pjinky.prisoncore.Main;
import com.pjinky.prisoncore.bounty.Listings.UpdateListings;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class BountyGUIInteractEvent implements Listener {

    private BountyGUICreate bountyGUICreate;
    private ConfigHandler configHandler;
    private BountyPlayerConfig bountyPlayerConfig;
    private Main plugin;
    private GetPlayer getPlayerConvert;
    private UpdateListings updateListings;

    @Inject
    public BountyGUIInteractEvent(BountyGUICreate bountyGUICreate, ConfigHandler configHandler, BountyPlayerConfig bountyPlayerConfig, Main plugin, GetPlayer getPlayerConvert, UpdateListings updateListings){
        this.bountyGUICreate = bountyGUICreate;
        this.bountyPlayerConfig = bountyPlayerConfig;
        this.configHandler = configHandler;
        this.plugin = plugin;
        this.getPlayerConvert = getPlayerConvert;
        this.updateListings = updateListings;
    }

    private static final Set<Integer> getItemsFoo = new HashSet<>(Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30));

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (!e.getInventory().getTitle().equalsIgnoreCase("§5§lDUSØR")){
            //Ignore
        }else{
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR){
                return;
            }

            ItemMeta itemMeta = e.getCurrentItem().getItemMeta();
            if (itemMeta.getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', "&2&lSide")) && e.getCurrentItem().getType() == Material.ARROW){
                String itemName = itemMeta.getDisplayName();
                itemName = ChatColor.stripColor(itemName);
                itemName = itemName.replace("Side ", "");
                int page = Integer.parseInt(itemName);
                bountyGUICreate.BountyGUI(player, page);
            }
        }

        if (!e.getInventory().getTitle().contains("§5§lDUSØR CREATE |")){
            //Ignore
        }else{
            String invName = e.getInventory().getTitle();
            invName = invName.replace("§5§lDUSØR CREATE | ", "");

            String setPlayer = invName;
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR){
                return;
            }

            Material getItem = e.getCurrentItem().getType();
            if (getItem.equals(Material.MAGENTA_STAINED_GLASS_PANE) || getItem.equals(Material.LIME_STAINED_GLASS_PANE) || getItem.equals(Material.RED_STAINED_GLASS_PANE) || getItem.equals(Material.GRAY_STAINED_GLASS_PANE)|| getItem.equals(Material.PAPER)){
                e.setCancelled(true);

                ItemMeta itemMeta = e.getCurrentItem().getItemMeta();
                ItemStack moneyAmountItem = e.getInventory().getItem(24);
                ItemMeta moneyAmountMeta = moneyAmountItem.getItemMeta();

                String moneyAmountString = ChatColor.stripColor(moneyAmountMeta.getDisplayName());
                moneyAmountString = moneyAmountString.replace("$", "");
                int moneyAmount = Integer.parseInt(moneyAmountString);

                String itemNameString = itemMeta.getDisplayName();
                itemNameString = ChatColor.stripColor(itemNameString);
                itemNameString = itemNameString
                        .replace("Fjern ", "")
                        .replace("Tilføj ", "")
                        .replace("$", "");
                Economy moneyCheck = plugin.getEconomy();

                if (itemMeta.getDisplayName().contains("§a§oTilføj")){
                    int moneyAdd = Integer.parseInt(itemNameString);
                    moneyAmount += moneyAdd;
                    if (moneyAmount <= moneyCheck.getBalance(player)) {
                        moneyAmountMeta.setDisplayName("§2§l$" + moneyAmount);
                        moneyAmountItem.setItemMeta(moneyAmountMeta);
                        e.getInventory().setItem(24, moneyAmountItem);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
                    }else{
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0F, 0.5F);
                    }
                }
                if (itemMeta.getDisplayName().contains("§c§oFjern")){
                    int moneyRemove = Integer.parseInt(itemNameString);
                    moneyAmount -= moneyRemove;
                    if (moneyAmount < 0){
                        moneyAmount = 0;
                    }
                    moneyAmountMeta.setDisplayName("§2§l$" + moneyAmount);
                    moneyAmountItem.setItemMeta(moneyAmountMeta);
                    e.getInventory().setItem(24, moneyAmountItem);
                }


                if (setPlayer == null){
                    //Nothing
                }else{

                    if (itemMeta.getDisplayName().equals("§2§lBEKRÆFT")){
                        Economy playerMoney = plugin.getEconomy();
                        ItemStack moneyItem = e.getInventory().getItem(24);
                        ItemMeta moneyMeta = moneyItem.getItemMeta();

                        String moneyString = ChatColor.stripColor(moneyMeta.getDisplayName());
                        moneyString = moneyString.replace("$", "");
                        int money = Integer.parseInt(moneyString);
                        int parsedMoney = Integer.parseInt(moneyString);
                        bountyPlayerConfig.load(setPlayer);
                        if (playerMoney.getBalance(player) >= money){
                            int confAmount = bountyPlayerConfig.getConfig().getInt("Money");
                            playerMoney.withdrawPlayer(player, money);
                            money = money + confAmount;
                            bountyPlayerConfig.getConfig().set("Money", money);
                            bountyPlayerConfig.saveConfig();
                        }else{
                            player.sendRawMessage("§8§l[ §5§lBOUNTY §8§l] §8§l» §c§oDu har ikke nok penge");
                            return;
                        }

                        bountyPlayerConfig.load(setPlayer);
                        configHandler.load();
                        List<String> playerList = configHandler.getConfig().getStringList("Active");
                        ConfigurationSection confSec = bountyPlayerConfig.getConfig().getConfigurationSection("Items");


                        ItemStack air = new ItemStack(Material.AIR);
                        int anyItems = 0;
                        for (int i = 0; i <= 45; i++){
                            if (getItemsFoo.contains(i)){
                                if (e.getInventory().getItem(i) == null){
                                    anyItems++;
                                }else {
                                    String checkItem = e.getInventory().getItem(i).getType().toString();
                                    if (!confSec.contains(checkItem)){
                                        bountyPlayerConfig.getConfig().set("Items." + checkItem, e.getInventory().getItem(i).getAmount());
                                        e.getInventory().setItem(i, air);
                                    }else if (confSec.contains(checkItem)){
                                        int getItemAmount = bountyPlayerConfig.getConfig().getInt("Items." + checkItem);
                                        int addAmount = getItemAmount + e.getInventory().getItem(i).getAmount();
                                        bountyPlayerConfig.getConfig().set("Items." + checkItem, addAmount);
                                        e.getInventory().setItem(i, air);
                                    }
                                }
                            }
                        }

                        if (anyItems != 9 || parsedMoney != 0) {
                            if (playerList.contains(getPlayerConvert.getUUID(setPlayer).toString())) {
                                //Nothing
                            } else {
                                playerList.add(getPlayerConvert.getUUID(setPlayer).toString());
                                configHandler.getConfig().set("Active", playerList);
                                configHandler.saveConfig();
                            }
                            bountyPlayerConfig.saveConfig();
                            updateListings.executeUpdate();
                        }
                        player.closeInventory();
                    }
                    ItemStack air = new ItemStack(Material.AIR);
                    if (itemMeta.getDisplayName().equals("§4§lANNULLER")){
                        for (int i = 0; i <= 45; i++){
                            if (getItemsFoo.contains(i)){
                                if (e.getInventory().getItem(i) == null){
                                    //Nothing
                                }else {
                                    ItemStack addItem = e.getInventory().getItem(i);
                                    player.getInventory().addItem(addItem);
                                    e.getInventory().setItem(i, air);
                                }
                            }
                        }
                        player.closeInventory();
                    }
                }
            }
        }


        if (!e.getInventory().getTitle().contains("§5§lDUSØR CLAIM | ")){
            //Ignore
        }else{
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR){
                return;
            }

            ItemMeta itemMeta = e.getCurrentItem().getItemMeta();
            if (itemMeta.getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', "&2&lSide")) && e.getCurrentItem().getType() == Material.ARROW) {
                String itemName = itemMeta.getDisplayName();
                itemName = ChatColor.stripColor(itemName);
                itemName = itemName.replace("Side ", "");
                int page = Integer.parseInt(itemName);
                bountyGUICreate.ClaimGUI(player, page);
            }else if (e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE){
                //do nothing
            }else{
                Material getItem = e.getCurrentItem().getType();
                if (player.getInventory().firstEmpty() != -1){
                    if (e.getClickedInventory().getType() != InventoryType.PLAYER) {
                        bountyPlayerConfig.load(player.getName());
                        ConfigurationSection confSec = bountyPlayerConfig.getConfig().getConfigurationSection("ClaimItems");
                        int confAmount = confSec.getInt(getItem.toString());
                        if (confAmount >= e.getCurrentItem().getAmount()) {
                            confAmount = confAmount - e.getCurrentItem().getAmount();
                            bountyPlayerConfig.getConfig().set("ClaimItems." + getItem.toString(), confAmount);
                            bountyPlayerConfig.saveConfig();
                            ItemStack setItem = new ItemStack(getItem, e.getCurrentItem().getAmount());
                            player.getInventory().addItem(setItem);
                            e.getInventory().clear(e.getSlot());
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.5f);
                        } else {
                            player.closeInventory();
                        }
                    }
                }else{
                    player.closeInventory();
                    player.sendRawMessage("§8§l[ §5§lDUSØR §8§l] §8§l» §c§oDu har ikke nok plads i dit inventory");
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if (e.getInventory().getTitle().contains("§5§lDUSØR CREATE | ")){
            for (int i = 0; i <= 45; i++){
                if (getItemsFoo.contains(i)){
                    if (e.getInventory().getItem(i) == null){
                        //Nothing
                    }else {
                        ItemStack air = new ItemStack(Material.AIR);
                        e.getPlayer().getName();
                        ItemStack addItem = e.getInventory().getItem(i);
                        e.getPlayer().getInventory().addItem(addItem);
                        e.getInventory().setItem(i, air);
                    }
                }
            }
        }
    }
}

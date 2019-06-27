package com.pjinky.prisoncore.shop;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.SignChangeEvent;

public class SignHandler implements Listener {

    private Main plugin;
    private ShopPlayerConfig config;


    @Inject
    public SignHandler(Main plugin, ShopPlayerConfig config){
        this.plugin = plugin;
        this.config = config;
    }



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player getPlayer = event.getPlayer();
        Action getEvent = event.getAction();

        String signLine1 = ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("signs.Line1"));

        if ((getEvent == Action.LEFT_CLICK_BLOCK) && event.getHand() == EquipmentSlot.HAND && (event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.WALL_SIGN)){
            String sellSuccessMessage = ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("messages.Sell.Success"));
            String sellFailureMessage = ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("messages.Sell.Failure"));

            Economy getEconomy = plugin.getEconomy();
            Sign getSign = (Sign)event.getClickedBlock().getState();

            if(getSign.getLine(0).equals(signLine1)){
                String itemPriceString = ChatColor.stripColor(getSign.getLine(2));
                itemPriceString = itemPriceString.replaceAll("[^0-9]", "");
                double itemPrice = Integer.parseInt(itemPriceString);
                Material itemToSell = Material.matchMaterial(ChatColor.stripColor(getSign.getLine(3)));
                String itemAmountString = ChatColor.stripColor(getSign.getLine(1));
                int itemAmount = Integer.parseInt(itemAmountString);
                sellSuccessMessage = sellSuccessMessage.replace("{item}", itemToSell.toString());

                config.load(getPlayer);
                if(getPlayer.getGameMode() == GameMode.CREATIVE && !config.getConfig().getBoolean("shop.CanDestroy")){
                    event.setCancelled(true);
                }else if(getPlayer.getGameMode() == GameMode.CREATIVE && config.getConfig().getBoolean("shop.CanDestroy")){
                    //Lad spilleren ødelægge skiltet uden at sælge i gamemode
                } else if(getPlayer.isSneaking()){
                    if (getPlayer.getInventory().contains(itemToSell)){
                        itemPrice = itemPrice / itemAmount;

                        if(getPlayer.getInventory().contains(itemToSell, 64)){
                            itemAmount = 64;
                        }else {
                            itemAmount = 0;
                            for (ItemStack item : getPlayer.getInventory().getContents()) {
                                if (item == null || item.getType() == null) {
                                    //ignore
                                } else if (item.getType().equals(itemToSell)) {
                                    itemAmount += item.getAmount();
                                }
                            }
                        }

                        itemPrice = itemAmount * itemPrice / 2;
                        sellSuccessMessage = sellSuccessMessage
                                .replace("{amount}", String.valueOf(itemAmount))
                                .replace("{price}", String.valueOf(itemPrice));

                        if(getPlayer.getInventory().contains(itemToSell, itemAmount)){
                            getPlayer.getInventory().removeItem(new ItemStack(itemToSell, itemAmount));
                            getEconomy.depositPlayer(getPlayer, itemPrice);
                            getPlayer.sendRawMessage(sellSuccessMessage);
                        }
                    }else{
                        itemAmount = 64;
                        sellFailureMessage = sellFailureMessage
                                .replace("{amount}", String.valueOf(itemAmount))
                                .replace("{item}", itemToSell.toString());
                        getPlayer.sendRawMessage(sellFailureMessage);
                    }

                }else{
                    if (getPlayer.getInventory().contains(itemToSell)) {
                        itemPrice = itemPrice / 2;
                        sellSuccessMessage = sellSuccessMessage
                                .replace("{amount}", String.valueOf(itemAmount))
                                .replace("{price}", String.valueOf(itemPrice));
                        if(getPlayer.getInventory().contains(itemToSell, itemAmount)){
                            getPlayer.getInventory().removeItem(new ItemStack(itemToSell, itemAmount));
                            getEconomy.depositPlayer(getPlayer, itemPrice);
                            getPlayer.sendRawMessage(sellSuccessMessage);
                        }else{
                            sellFailureMessage = sellFailureMessage
                                    .replace("{amount}", String.valueOf(itemAmount))
                                    .replace("{item}", itemToSell.toString());
                            getPlayer.sendRawMessage(sellFailureMessage);
                        }
                    }else{
                        sellFailureMessage = sellFailureMessage
                                .replace("{amount}", String.valueOf(itemAmount))
                                .replace("{item}", itemToSell.toString());
                        getPlayer.sendRawMessage(sellFailureMessage);
                    }
                }
            }
        } else if ((getEvent == Action.RIGHT_CLICK_BLOCK) && event.getHand() == EquipmentSlot.HAND && (event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.WALL_SIGN)) {
            String buySuccessMessage = ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("messages.Buy.Success"));
            String buyFailureMessage = ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("messages.Buy.Failure"));
            Economy getEconomy = plugin.getEconomy();
            Sign getSign = (Sign) event.getClickedBlock().getState();
            if (getSign.getLine(0).equals(signLine1)) {
                String itemPriceString = ChatColor.stripColor(getSign.getLine(2));
                itemPriceString = itemPriceString.replaceAll("[^0-9]", "");
                double itemPrice = Integer.parseInt(itemPriceString);
                Material itemToBuy = Material.matchMaterial(ChatColor.stripColor(getSign.getLine(3)));
                String itemAmountString = ChatColor.stripColor(getSign.getLine(1));
                int itemAmount = Integer.parseInt(itemAmountString);
                buySuccessMessage = buySuccessMessage.replace("{item}", itemToBuy.toString());
                if(getPlayer.getGameMode() == GameMode.CREATIVE){
                    event.setCancelled(true);
                } else if (getPlayer.isSneaking()) {
                    itemPrice = itemPrice / itemAmount;
                    int canHold = 0;

                    if (getPlayer.getInventory().firstEmpty() != -1){
                        canHold = 64;
                    }else {
                        for (ItemStack item : getPlayer.getInventory().getContents()) {
                            if (item == null || item.getType() == null) {
                                //Ignore
                            } else if (item.getType().equals(itemToBuy)) {
                                if (canHold <= 64) {
                                    canHold += 64 - item.getAmount();
                                }
                            }
                            if (canHold > 64) {
                                canHold = 64;
                                break;
                            }
                        }
                    }

                    itemPrice = itemPrice * canHold;
                    buySuccessMessage = buySuccessMessage.replace("{amount}", String.valueOf(canHold));
                    buySuccessMessage = buySuccessMessage.replace("{price}", String.valueOf(itemPrice));

                    if(getEconomy.getBalance(getPlayer) >= itemPrice){
                        if (canHold == 0){
                            getPlayer.sendRawMessage("§4§lDu har ikke nok plads");
                        }else{
                            getEconomy.withdrawPlayer(getPlayer, itemPrice);
                            getPlayer.getInventory().addItem(new ItemStack(itemToBuy, canHold));
                            getPlayer.sendRawMessage(buySuccessMessage);
                        }

                    }else {
                        double balance = itemPrice - getEconomy.getBalance(getPlayer);
                        int moneyMissing = (int) Math.round(balance);
                        buyFailureMessage = buyFailureMessage.replace("{missing}", String.valueOf(moneyMissing));
                        getPlayer.sendRawMessage(buyFailureMessage);
                    }
                }else {
                    int canHold = 0;

                    if (getPlayer.getInventory().firstEmpty() != -1){
                        canHold = 64;
                    }else{
                        for (ItemStack item : getPlayer.getInventory().getContents()) {
                            if (item == null || item.getType() == null) {
                                //Ignore
                            } else if (item.getType().equals(itemToBuy)) {
                                if(canHold < 64){
                                    canHold += 64 - item.getAmount();
                                }
                            }if (canHold == itemAmount){
                                break;
                            }
                        }
                    }

                    if(getEconomy.getBalance(getPlayer) >= itemPrice){
                        if (canHold < itemAmount){
                            getPlayer.sendRawMessage("§4§lDu har ikke nok plads");
                        }else{
                            getEconomy.withdrawPlayer(getPlayer, itemPrice);
                            getPlayer.getInventory().addItem(new ItemStack(itemToBuy, itemAmount));
                            buySuccessMessage = buySuccessMessage
                                    .replace("{price}", String.valueOf(itemPrice))
                                    .replace("{amount}", String.valueOf(itemAmount));
                            getPlayer.sendRawMessage(buySuccessMessage);
                        }

                    }else {
                        double balance = itemPrice - getEconomy.getBalance(getPlayer);
                        int moneyMissing = (int) Math.round(balance);
                        buyFailureMessage = buyFailureMessage.replace("{missing}", String.valueOf(moneyMissing));
                        getPlayer.sendRawMessage(buyFailureMessage);
                        }
                    }
                }
            }
        }

    @EventHandler
    public void onSignUpdate(SignChangeEvent event){
        if(event.getPlayer().getName().equals("Pjinky") || event.getPlayer().getName().equals("hund35")){
            for(int i = 0; i < 4; i++){
                String line = event.getLine(i);
                if (line != null && !line.equals("")){
                    event.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
                }
            }
        }
    }
}

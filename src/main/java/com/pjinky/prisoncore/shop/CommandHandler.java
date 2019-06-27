package com.pjinky.prisoncore.shop;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHandler implements CommandExecutor {

    private Main plugin;
    private ShopPlayerConfig config;

    @Inject
    public CommandHandler(Main plugin, ShopPlayerConfig config){
        this.plugin = plugin;
        this.config = config;
    }




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length >= 1) {
                if (args[0].equalsIgnoreCase("create")) {
                    if(args.length == 4){
                        if (checkInt(args[1])) {
                            if (checkInt(args[2])) {
                                if (checkItem(args[3])) {
                                    String text1 = ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("signs.Line1"));
                                    String text2 = ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("signs.Line2"));
                                    String text3 = ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("signs.Line3"));
                                    String text4 = ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("signs.Line4"));

                                    text2 = text2.replace("{amount}", args[1]);
                                    text3 = text3.replace("{price}", args[2]);
                                    text4 = text4.replace("{item}", args[3]).replace("_", " ");

                                    ItemStack sign = new ItemStack(Material.SIGN, 1);
                                    net.minecraft.server.v1_13_R2.ItemStack nmsSign = CraftItemStack.asNMSCopy(sign);
                                    NBTTagCompound signcompound = (nmsSign.hasTag() ? nmsSign.getTag() : new NBTTagCompound());
                                    NBTTagCompound bet = signcompound.getCompound("BlockEntityTag");


                                    NBTTagString signText1 = new NBTTagString("{\"text\":\"" + text1 + "\"}");
                                    NBTTagString signText2 = new NBTTagString("{\"text\":\"" + text2 + "\"}");
                                    NBTTagString signText3 = new NBTTagString("{\"text\":\"" + text3 + "\"}");
                                    NBTTagString signText4 = new NBTTagString("{\"text\":\"" + text4.toUpperCase() + "\"}");
                                    bet.set("Text1", signText1);
                                    bet.set("Text2", signText2);
                                    bet.set("Text3", signText3);
                                    bet.set("Text4", signText4);

                                    signcompound.set("BlockEntityTag", bet);
                                    nmsSign.setTag(signcompound);
                                    player.getInventory().addItem(CraftItemStack.asBukkitCopy(nmsSign));
                                } else {
                                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Create.Arg4Missing")));
                                }
                            } else {
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Create.Arg3Missing")));
                            }
                        } else {
                            player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Create.Arg2Missing")));
                        }
                    }else{
                        player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Create.MissingArgs")));
                    }
                } else if(args[0].equalsIgnoreCase("toggle")){
                    config.load(player);
                    if(args.length == 2){
                        if(args[1].equalsIgnoreCase("on")){
                            if(!config.getConfig().getBoolean("shop.CanDestroy")) {
                                config.getConfig().set("shop.CanDestroy", true);
                                config.saveConfig();
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Shop.ToggleCanDestroy.ToggleOn")));
                            }else{
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Shop.ToggleCanDestroy.ToggleAlreadyOn")));
                            }
                        }else if(args[1].equalsIgnoreCase("off")){
                            if(config.getConfig().getBoolean("shop.CanDestroy")) {
                                config.getConfig().set("shop.CanDestroy", false);
                                config.saveConfig();
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Shop.ToggleCanDestroy.ToggleOff")));
                            }else{
                                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Shop.ToggleCanDestroy.ToggleAlreadyOff")));
                            }
                        }else{
                            player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Shop.ToggleCanDestroy.WrongArgument")));
                        }
                    }else{
                        if(!config.getConfig().getBoolean("shop.CanDestroy")){
                            config.getConfig().set("shop.CanDestroy", true);
                            config.saveConfig();
                            player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Shop.ToggleCanDestroy.ToggleOn")));
                        }else{
                            config.getConfig().set("shop.CanDestroy", false);
                            config.saveConfig();
                            player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Shop.ToggleCanDestroy.ToggleOff")));
                        }
                    }
                } else if(args[0].equalsIgnoreCase("hjælp") || args[0].equalsIgnoreCase("help")){
                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Help.Header")) + "\n" +
                            ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("messages.Commands.Help.SubHeader")) + "\n" +
                            ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("messages.Commands.Help.Help")) + "\n" +
                            ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("messages.Commands.Help.Create")) + "\n" +
                            ChatColor.translateAlternateColorCodes('&', plugin.getShopConfig().getString("messages.Commands.Help.Footer")));
                } else {
                    player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Shop.WrongArgument")));
                }
            }else{
                player.sendRawMessage(ChatColor.translateAlternateColorCodes('&', plugin.updateShopConfig().getString("messages.Commands.Shop.NoArguments")));
            }
        }

        return true;
    }

    private boolean checkInt(String s){ ;
        try{
            int arg = (int) Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    private boolean checkItem(String s){
        try{
            Material item = Material.matchMaterial(s);
            ItemStack test = new ItemStack(item, 1);
            return true;
        }catch(NullPointerException | IllegalArgumentException e){
            return false;
        }
    }
}

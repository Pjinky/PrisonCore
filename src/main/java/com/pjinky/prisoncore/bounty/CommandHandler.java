package com.pjinky.prisoncore.bounty;

import com.google.inject.Inject;
import com.pjinky.prisoncore.GetPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    private BountyPlayerConfig bountyPlayerConfig;
    private ConfigHandler configHandler;
    private BountyGUICreate bountyGUICreate;
    private GetPlayer getPlayerConvert;


    @Inject
    public CommandHandler(BountyPlayerConfig bountyPlayerConfig, ConfigHandler configHandler, BountyGUICreate bountyGUICreate, GetPlayer getPlayerConvert) {
        this.bountyPlayerConfig = bountyPlayerConfig;
        this.configHandler = configHandler;
        this.bountyGUICreate = bountyGUICreate;
        this.getPlayerConvert = getPlayerConvert;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(sender instanceof Player) {
            Player getPlayer = (Player) sender;
            if (args.length >= 1) {
                if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("sæt") || args[0].equalsIgnoreCase("opret")){
                    if(args.length >= 2) {
                        OfflinePlayer setPlayer = Bukkit.getPlayer(args[1]);
                        if (!getPlayerConvert.playerExists(args[1])){
                            getPlayer.sendRawMessage("§8§l[ §5§lBOUNTY §8§l] §8§l» §c§oSpilleren eksistere ikke");
                        }else if (!getPlayer.equals(setPlayer)){
                            bountyGUICreate.CreateGUI(getPlayer, args[1]);
                            bountyPlayerConfig.load(args[1]);
                            configHandler.load();
                        }else{
                            getPlayer.sendRawMessage("§8§l[ §5§lBOUNTY §8§l] §8§l» §c§oDu kan ikke sætte en dusør på dig selv");
                        }
                    }else {
                        getPlayer.sendRawMessage("§8§l[ §5§lBOUNTY §8§l] §8§l» §c§oDu skal angive en spiller");
                    }
                }else if(args[0].equalsIgnoreCase("claim")) {
                    bountyGUICreate.ClaimGUI(getPlayer, 1);
                }

                else{
                    getPlayer.sendRawMessage("§8§l[ §5§lBOUNTY §8§l] §8§l» §c§oDer mangler argumenter.");
                }
            }else{
                bountyGUICreate.BountyGUI(getPlayer, 1);
            }
        }
        return true;
    }
}

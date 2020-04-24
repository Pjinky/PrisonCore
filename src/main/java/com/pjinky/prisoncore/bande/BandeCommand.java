package com.pjinky.prisoncore.bande;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BandeCommand implements CommandExecutor {

    private Helper helper;

    @Inject
    public BandeCommand(Helper helper){
        this.helper = helper;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (args.length > 1) {
                        if (args[1] != null) {
                            if (!helper.CheckIfPlayerIsInBande(player)) {
                                helper.Create(player, args[1]);
                                player.sendRawMessage("Oprettet bande");
                            }else{
                                player.sendRawMessage("Allerede i bande");
                            }
                        } else {
                            player.sendRawMessage("Arg-2 er null!");
                        }
                    } else {
                        player.sendRawMessage("Arg-2 ikke sat!");
                    }
                }else if (args[0].equalsIgnoreCase("delete")){
                    if (args.length > 1){
                        if (args[1] != null){
                            player.sendRawMessage(helper.GetBandeOfPlayer(player).name);
                            player.sendRawMessage("Delete");
                        }else {
                            helper.DeleteBandeWithoutConfirmation(player);
                            player.sendRawMessage("Delete own");
                        }
                    }
                }else if(args[0].equalsIgnoreCase("save")){
                    helper.saveAll();
                    player.sendRawMessage("Saved");
                }else if (args[0].equalsIgnoreCase("bank")) {
                    if (args.length > 3 && args[1].equalsIgnoreCase("deposit")) {
                        helper.depositMoney(player, Integer.getInteger(args[2]));
                    }
                }else {
                    player.sendRawMessage("Intet valgt");
                }
            } else {
                player.sendRawMessage("Ingen argumenter");
            }
        }
        return true;
    }
}

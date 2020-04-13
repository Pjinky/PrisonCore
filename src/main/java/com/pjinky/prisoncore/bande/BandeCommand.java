package com.pjinky.prisoncore.bande;

import com.google.inject.Inject;
import com.pjinky.prisoncore.bande.events.Create;
import com.pjinky.prisoncore.bande.events.Delete;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BandeCommand implements CommandExecutor {

    private Create bandeCreate;
    private Delete bandeDelete;
    private Helper helper;

    @Inject
    public BandeCommand(Create bandeCreate, Delete bandeDelete, Helper helper){
        this.bandeCreate = bandeCreate;
        this.bandeDelete = bandeDelete;
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
                            helper.Create(player, args[1]);
                            player.sendRawMessage("Create");
                        } else {
                            player.sendRawMessage("HELLER IKKE NOGET HER :)");
                        }
                    } else {
                        player.sendRawMessage("HELLER IKKE NOGET HER :) :)");
                    }
                }else if (args[0].equalsIgnoreCase("delete")){
                    if (args.length > 1){
                        if (args[1] != null){
                            helper.Delete(player);
                            player.sendRawMessage("Delete");
                        }
                    }else{
                        bandeDelete.Setup(player, null);
                    }
                }else if(args[0].equalsIgnoreCase("save")){
                    helper.saveAll();
                    player.sendRawMessage("Save");
                }else {
                    player.sendRawMessage("DAV, DER ER IKKE NOGET AT SE HER :)");
                }
            } else {
                player.sendRawMessage("DAV, DER ER IKKE NOGET AT SE HER :) :)");
            }
        }
        return true;
    }
}

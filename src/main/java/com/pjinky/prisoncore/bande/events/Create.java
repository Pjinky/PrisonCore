package com.pjinky.prisoncore.bande.events;

import com.google.inject.Inject;
import com.pjinky.prisoncore.bande.configs.Bande;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Create {

    private Bande bandeConf;

    @Inject
    public Create(Bande bandeConf){
        this.bandeConf = bandeConf;
    }

    public void Setup(Player p, String name){
        bandeConf.load();
        if (!checkBande(p)) {
            if (!bandeConf.getConfig().getConfigurationSection("Bande").getKeys(false).contains(name)) {
                bandeConf.getConfig().set("Bande." + name + ".Ejer", p.getUniqueId().toString());
                bandeConf.getConfig().set("Bande." + name + ".Level", 0);
                bandeConf.getConfig().set("Bande." + name + ".PlayerKills", 0);
                bandeConf.getConfig().set("Bande." + name + ".VagtKills", 0);
                bandeConf.getConfig().set("Bande." + name + ".Balance", 0);
                List<String> playerList = new ArrayList<>();
                playerList.add(p.getUniqueId().toString());
                bandeConf.getConfig().set("Bande." + name + ".Medlemmer", playerList);
                bandeConf.getConfig().createSection("Bande." + name + ".Allierede");
                bandeConf.getConfig().createSection("Bande." + name + ".Rivaler");
                bandeConf.saveConfig();
                p.sendRawMessage("BANDEN MED NAVNET " + name + " ER OPRETTET");
            } else {
                p.sendRawMessage("BANDEN EKSISTERE ALLEREDE");
            }
        }else {
            p.sendRawMessage("DU ER ALLEREDE I EN BANDE");
        }
    }

    private boolean checkBande(Player p){
        bandeConf.getConfig().getConfigurationSection("Bande");

        for (String key : bandeConf.getConfig().getConfigurationSection("Bande").getKeys(false)){
            if (bandeConf.getConfig().getStringList("Bande." + key + ".Medlemmer").contains(p.getUniqueId().toString())){
                return true;
            }
        }
        return false;
    }
}

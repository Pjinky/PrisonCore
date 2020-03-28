package com.pjinky.prisoncore.bande.events;

import com.google.inject.Inject;
import com.pjinky.prisoncore.bande.configs.Bande;
import org.bukkit.entity.Player;

public class Delete {

    private Bande bandeConf;

    @Inject
    public Delete(Bande bandeConf){
        this.bandeConf = bandeConf;
    }

    public void Setup(Player p, String name){
        if (name != null){
            bandeConf.load();
            if (p.getName().equals("Pjinky")){
                if (bandeConf.getConfig().getConfigurationSection("Bande").getKeys(false).contains(name)){
                    bandeConf.getConfig().set("Bande." + name, null);
                    p.sendRawMessage("FARVEL TIL " + name);
                    bandeConf.saveConfig();
                }
            }else{
                p.sendRawMessage("NOPE");
            }
        }else {
            String checkBande = CheckBande(p);
            if (checkBande != null) {
                bandeConf.getConfig().set("Bande." + checkBande, null);
                bandeConf.saveConfig();
                p.sendRawMessage("DIN BANDE ER SLETTET");
            }else{
                p.sendRawMessage("DU NOK IKKE I EN BANDE ELLER OS EJER DU DEN IKKE :)");
            }
        }
    }

    private String CheckBande(Player p){
        bandeConf.load();

        for (String key : bandeConf.getConfig().getConfigurationSection("Bande").getKeys(false)){
            if (bandeConf.getConfig().getString("Bande." + key + ".Ejer").equalsIgnoreCase(p.getUniqueId().toString())){
                return key;
            }
        }
        return null;
    }
}

package com.pjinky.prisoncore.bande.stats;

import com.google.inject.Inject;
import com.pjinky.prisoncore.bande.configs.Bande;
import org.bukkit.entity.Player;

public class Kills {

    private Bande bandeConf;

    @Inject
    public Kills(Bande bandeConf){
        this.bandeConf = bandeConf;
    }

    public void Add(String rank, Player p){
        String bande = GetBande(p);
        if (rank.equalsIgnoreCase("vagt")){
            if (bande != null){
                int kills = bandeConf.getConfig().getInt("Bande." + bande + ".VagtKills");
                kills += 1;
                bandeConf.getConfig().set("Bande." + bande + ".VagtKills", kills);
                bandeConf.saveConfig();
            }else{

            }
        }else if (rank.equalsIgnoreCase("spiller")){
            if (bande != null){
                int kills = bandeConf.getConfig().getInt("Bande." + bande + ".PlayerKills");
                kills += 1;
                bandeConf.getConfig().set("Bande." + bande + ".PlayerKills", kills);
                bandeConf.saveConfig();
            }else{

            }
        }else{

        }
    }

    private String GetBande(Player p){
        bandeConf.load();

        for (String key : bandeConf.getConfig().getConfigurationSection("Bande").getKeys(false)){
            if (bandeConf.getConfig().getStringList("Bande." + key + ".Medlemmer").contains(p.getUniqueId().toString())){
                return key;
            }
        }
        return null;
    }
}

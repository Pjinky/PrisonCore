package com.pjinky.prisoncore.bande;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Helper {

    private Main plugin;

    @Inject
    public Helper(Main plugin){
        this.plugin = plugin;
    }


    public void Create(Player player, String name){
        boolean bCheck = true;
        UUID uuid = UUID.randomUUID();
        for (Bande b: plugin.bande) {
            if (b.getUUID().equals(uuid)){
                Create(player, name);
            }else{
                bCheck = false;
                return;
            }
        }
        if (!bCheck){
            Member member = new Member(Member.Rank.PRESIDENT, player.getUniqueId());
            List<Member> members = new ArrayList<>();
            members.add(member);
            Bank bank = new Bank(0);
            List<Relations> relation = new ArrayList<>();
            Bande bande = new Bande(uuid, name, bank, members, 1, 0, 0, relation);
            plugin.bande.add(bande);
        }

    }

    public void Delete(Player player){
        for (Bande b: plugin.bande) {
            List<Member> member = b.getMembers();
            for (Member m : member) {
                plugin.bande.removeIf(q -> m.uuid.equals(player.getUniqueId()) && m.rank.equals(Member.Rank.PRESIDENT));
            }
        }
    }
    
    public void Delete(String bande){
        plugin.bande.removeIf(b -> b.getName().equals(bande));
    }

    public void saveAll(){
        File data = new File(plugin.getDataFolder(), File.separator + "Bande");
        File d = new File(data, File.separator + "Bande.yml");
        FileConfiguration fdata = YamlConfiguration.loadConfiguration(d);

        if (!d.exists()){

        }else{
            try{
                for (Bande b : plugin.bande) {
                    fdata.set(b.getUUID() + ".name", b.getName());
                    fdata.set(b.getUUID() + ".level", b.getLevel());
                    fdata.set(b.getUUID() + ".bank", b.getBank());
                    for (Member m : b.getMembers()) {
                        fdata.set(b.getUUID() + ".members." + m.uuid, m.rank);
                    }
                    fdata.set(b.getUUID() + ".playerKills", b.getPlayerKills());
                    fdata.set(b.getUUID() + ".guardKills", b.getGuardKills());
                    for (Relations r : b.getRelations()) {
                        fdata.set(b.getUUID() + ".Relations." + r.gangUUID, r.type);
                    }
                }
                fdata.save(d);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void loadAll(){

    }
}

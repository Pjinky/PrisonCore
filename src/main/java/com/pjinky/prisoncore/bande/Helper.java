package com.pjinky.prisoncore.bande;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Helper {

    private Main plugin;

    @Inject
    public Helper(Main plugin){
        this.plugin = plugin;
    }


    public void create(Player player, String name){
        UUID uuid = UUID.randomUUID();
        Member member = new Member(Member.Rank.PRESIDENT, player.getUniqueId());
        List<Member> members = new ArrayList<>();
        members.add(member);
        Bank bank = new Bank(0);
        List<Relations> relation = new ArrayList<>();
        Bande bande = new Bande(uuid, name, bank, members, 1, 0, 0, relation);
        plugin.bande.add(bande);
    }
}

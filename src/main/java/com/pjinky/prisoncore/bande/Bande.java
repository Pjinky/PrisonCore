package com.pjinky.prisoncore.bande;

import org.bukkit.entity.Player;

import java.util.List;

public class Bande {
    Bank bank;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    List<Player> players;

    public Player getOwner() {
        return owner;
    }

    public Player owner;

    public int getLevel() {
        return level;
    }

    public void setLevel(int amount) {
        this.level = amount;
    }

    public void addLevel(int amount){
        this.level += amount;
    }

    public void subtractLevel(int amount){
        this.level -= amount;
    }

    public int level;


}

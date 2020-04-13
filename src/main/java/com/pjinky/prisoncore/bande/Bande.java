package com.pjinky.prisoncore.bande;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Bande {

    public UUID getUUID() {
        return uuid;
    }

    public UUID uuid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;


    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    Bank bank;


    public List<Member> getMembers() {
        return members;
    }

    public void addMember(Member member){
        members.add(member);
    }

    public void removeMember(Member member){
        members.remove(member);
    }

    List<Member> members;


    public int getLevel() {
        return level;
    }

    public void setLevel(int amount) {
        this.level = amount;
    }

    public void addLevel(int amount){
        this.level += amount;
    }

    public void removeLevel(int amount){
        this.level -= amount;
    }

    public int level;


    public int getPlayerKills() {
        return playerKills;
    }

    public void addPlayerKills(int amount){
        this.playerKills += amount;
    }

    public void removePlayerKills(int amount){
        this.playerKills -= amount;
    }

    public void setPlayerKills(int amount){
        this.playerKills = amount;
    }

    public int playerKills;


    public int getGuardKills() {
        return guardKills;
    }

    public void setGuardKills(int guardKills) {
        this.guardKills = guardKills;
    }

    public void addGuardKills(int amount){
        this.guardKills += amount;
    }

    public void removeGuardKills(int amount){
        this.guardKills -= amount;
    }

    public int guardKills;


    public List<Relations> getRelations() {
        return relations;
    }

    public void addRelations(Relations relation) {
        this.relations.add(relation);
    }

    public void removeRelations(Relations relation) {
        this.relations.remove(relation);
    }

    List<Relations> relations;

    public Bande(UUID uuid, String name, Bank bank, List<Member> members, int level, int playerKills, int guardKills, List<Relations> relations) {
        this.uuid = uuid;
        this.name = name;
        this.bank = bank;
        this.members = members;
        this.level = level;
        this.playerKills = playerKills;
        this.guardKills = guardKills;
        this.relations = relations;
    }
}
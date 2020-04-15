package com.pjinky.prisoncore.bande;

import org.bukkit.entity.Item;

import java.util.List;

public class Bank {
    public int getMoney() {
        return money;
    }

    public void setMoney(int amount) {
        this.money = amount;
    }

    public void deposit(int amount){
        this.money += amount;
    }

    public void withdraw(int amount){
        this.money -= amount;
    }

    public int money;

    /*public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private List<Item> items;*/

    public Bank(int moneyParam){
        money = moneyParam;
    }
}

package com.pjinky.prisoncore.bounty;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class BountyGUICreate {

    private final Set<Integer> outerSlotsFoo = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 49, 51, 52, 53));
    private final Set<Integer> outerSlotsFoo2 = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 17, 18, 22, 26, 27, 31, 35, 37, 38, 39, 40, 41, 42, 43));

    private BountyPlayerConfig bountyPlayerConfig;
    private ConfigHandler configHandler;

    @Inject
    public BountyGUICreate(BountyPlayerConfig bountyPlayerConfig, ConfigHandler configHandler) {
        this.bountyPlayerConfig = bountyPlayerConfig;
        this.configHandler = configHandler;
    }

    public void BountyGUI(Player p, int page) {
        configHandler.load();
        Inventory bountyGUI = Bukkit.createInventory(p, 54, "§5§lDUSØR");
        ItemStack outerSlotsItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE, 1);
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        ItemStack pageItem = new ItemStack(Material.ARROW);
        ItemStack noPage = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta outerSlotsMeta = outerSlotsItem.getItemMeta();
        SkullMeta playerSkullMeta = (SkullMeta) playerHead.getItemMeta();
        ItemMeta pageItemMeta = pageItem.getItemMeta();
        ItemMeta noPageMeta = noPage.getItemMeta();
        outerSlotsMeta.setDisplayName("§6");
        outerSlotsItem.setItemMeta(outerSlotsMeta);
        List<String> players = configHandler.getConfig().getStringList("Active");

        double pages;
        pages = players.size() / 28.0;
        pages = Math.ceil(pages);
        for (int i = 0; i <= 54; i++) {
            if (outerSlotsFoo.contains(i)) {
                bountyGUI.setItem(i, outerSlotsItem);
            }
        }

        if (page == pages && page != 1) {
            int lastPage = page - 1;
            pageItemMeta.setDisplayName("§2§lSide " + lastPage);
            pageItem.setItemMeta(pageItemMeta);
            bountyGUI.setItem(48, pageItem);
            noPageMeta.setDisplayName("§4§lDer er ikke flere sider");
            noPage.setItemMeta(noPageMeta);
            bountyGUI.setItem(50, noPage);
        } else if (page == pages && page == 1) {
            noPageMeta.setDisplayName("§4§lDu er på første side");
            noPage.setItemMeta(noPageMeta);
            bountyGUI.setItem(48, noPage);
            noPageMeta.setDisplayName("§4§lDer er ikke flere sider");
            noPage.setItemMeta(noPageMeta);
            bountyGUI.setItem(50, noPage);
        } else if (page < pages && page != 1) {
            int lastPage = page - 1;
            pageItemMeta.setDisplayName("§2§lSide " + lastPage);
            pageItem.setItemMeta(pageItemMeta);
            bountyGUI.setItem(48, pageItem);
            int nextPage = page + 1;
            pageItemMeta.setDisplayName("§2§lSide " + nextPage);
            pageItem.setItemMeta(pageItemMeta);
            bountyGUI.setItem(50, pageItem);
        } else if (pages == page) {
            noPageMeta.setDisplayName("§4§lDer er ikke flere sider");
            noPage.setItemMeta(noPageMeta);
            bountyGUI.setItem(50, noPage);
            int lastPage = page - 1;
            pageItemMeta.setDisplayName("§2§lSide " + lastPage);
            pageItem.setItemMeta(pageItemMeta);
            bountyGUI.setItem(48, pageItem);
        } else if (page == 1 && page < pages) {
            noPageMeta.setDisplayName("§4§lDu er på første side");
            noPage.setItemMeta(noPageMeta);
            bountyGUI.setItem(48, noPage);
            int nextPage = page + 1;
            pageItemMeta.setDisplayName("§2§lSide " + nextPage);
            pageItem.setItemMeta(pageItemMeta);
            bountyGUI.setItem(50, pageItem);
        }

        if (page > 1.0) {
            page = page * 28 - 28;
        } else {
            page = 0;
        }


        configHandler.load();
        int count = 0;
        for (String uuid : configHandler.getConfig().getStringList("Active")) {
            if (count >= page) {
                List<String> lore = new ArrayList<>();
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                bountyPlayerConfig.load(player);
                playerSkullMeta.setOwningPlayer(player);
                playerSkullMeta.setDisplayName("§3§l" + player.getName());
                lore.add("§b§oPris: " + bountyPlayerConfig.getConfig().getString("Money"));
                lore.add(" ");
                lore.add("§b§oItems:");
                for (String item : bountyPlayerConfig.getConfig().getConfigurationSection("Items").getKeys(false)) {
                    int amount = bountyPlayerConfig.getConfig().getInt("Items." + item);
                    lore.add(" §b§o- " + item + ": " + amount);
                }
                playerSkullMeta.setLore(lore);
                playerHead.setItemMeta(playerSkullMeta);
                bountyGUI.addItem(playerHead);
            }
            count++;
            if (count == page + 28) {
                break;
            }
        }

        p.openInventory(bountyGUI);
    }



    public void CreateGUI(Player p, String name) {
        Inventory bountyGUI = Bukkit.createInventory(p, 45, "§5§lDUSØR CREATE | " + name);
        ItemStack outerSlotsItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE, 1);
        ItemStack limeGlass = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemStack redGlass = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemStack moneyAmountItem = new ItemStack(Material.PAPER);
        ItemStack grayGlass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta outerSlotsMeta = outerSlotsItem.getItemMeta();
        outerSlotsMeta.setDisplayName("§6");
        outerSlotsItem.setItemMeta(outerSlotsMeta);
        ItemMeta limeGlassMeta = limeGlass.getItemMeta();
        ItemMeta redGlassMeta = redGlass.getItemMeta();
        ItemMeta moneyAmountMeta = moneyAmountItem.getItemMeta();
        ItemMeta grayGlassMeta = grayGlass.getItemMeta();

        limeGlassMeta.setDisplayName("§a§oTilføj $100");
        limeGlass.setItemMeta(limeGlassMeta);
        bountyGUI.setItem(14, limeGlass);
        grayGlassMeta.setDisplayName("§a");
        grayGlass.setItemMeta(grayGlassMeta);
        bountyGUI.setItem(15, grayGlass);
        limeGlassMeta.setDisplayName("§a§oTilføj $500");
        limeGlass.setItemMeta(limeGlassMeta);
        bountyGUI.setItem(23, limeGlass);
        limeGlassMeta.setDisplayName("§a§oTilføj $1000");
        limeGlass.setItemMeta(limeGlassMeta);
        bountyGUI.setItem(32, limeGlass);
        bountyGUI.setItem(33, grayGlass);

        moneyAmountMeta.setDisplayName("§2§l$0");
        moneyAmountItem.setItemMeta(moneyAmountMeta);
        bountyGUI.setItem(24, moneyAmountItem);

        redGlassMeta.setDisplayName("§c§oFjern $100");
        redGlass.setItemMeta(redGlassMeta);
        bountyGUI.setItem(16, redGlass);
        redGlassMeta.setDisplayName("§c§oFjern $500");
        redGlass.setItemMeta(redGlassMeta);
        bountyGUI.setItem(25, redGlass);
        redGlassMeta.setDisplayName("§c§oFjern $1000");
        redGlass.setItemMeta(redGlassMeta);
        bountyGUI.setItem(34, redGlass);

        redGlassMeta.setDisplayName("§4§lANNULLER");
        redGlass.setItemMeta(redGlassMeta);
        bountyGUI.setItem(36, redGlass);
        limeGlassMeta.setDisplayName("§2§lBEKRÆFT");
        limeGlass.setItemMeta(limeGlassMeta);
        bountyGUI.setItem(44, limeGlass);

        for (int i = 0; i <= 45; i++) {
            if (outerSlotsFoo2.contains(i)) {
                bountyGUI.setItem(i, outerSlotsItem);
            }
        }
        p.openInventory(bountyGUI);
    }


    public void ClaimGUI(Player p, int page){
        bountyPlayerConfig.load(p);
        ConfigurationSection confSec = bountyPlayerConfig.getConfig().getConfigurationSection("ClaimItems");
        Inventory claimGUI = Bukkit.createInventory(p, 54, "§5§lDUSØR CLAIM | " + p.getName());

        int itemStacks = 0;

        for (String key : confSec.getKeys(false)) {
            int itemAmount = (Integer) confSec.get(key);
            if (itemAmount != 0){
                if (itemAmount >= 64){
                    itemStacks += (int) Math.ceil(itemAmount / 64.0);
                }else{
                    itemStacks++;
                }
            }
        }



        ItemStack pageItem = new ItemStack(Material.ARROW);
        ItemStack noPage = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta pageItemMeta = pageItem.getItemMeta();
        ItemMeta noPageMeta = noPage.getItemMeta();


        itemStacks = (int) Math.ceil(itemStacks / 44.0);
        if (page == itemStacks && page != 1) {
            int lastPage = page - 1;
            pageItemMeta.setDisplayName("§2§lSide " + lastPage);
            pageItem.setItemMeta(pageItemMeta);
            claimGUI.setItem(48, pageItem);
            noPageMeta.setDisplayName("§4§lDer er ikke flere sider");
            noPage.setItemMeta(noPageMeta);
            claimGUI.setItem(50, noPage);
        } else if (page == itemStacks && page == 1) {
            noPageMeta.setDisplayName("§4§lDu er på første side");
            noPage.setItemMeta(noPageMeta);
            claimGUI.setItem(48, noPage);
            noPageMeta.setDisplayName("§4§lDer er ikke flere sider");
            noPage.setItemMeta(noPageMeta);
            claimGUI.setItem(50, noPage);
        } else if (page < itemStacks && page != 1) {
            int lastPage = page - 1;
            pageItemMeta.setDisplayName("§2§lSide " + lastPage);
            pageItem.setItemMeta(pageItemMeta);
            claimGUI.setItem(48, pageItem);
            int nextPage = page + 1;
            pageItemMeta.setDisplayName("§2§lSide " + nextPage);
            pageItem.setItemMeta(pageItemMeta);
            claimGUI.setItem(50, pageItem);
        } else if (itemStacks == page) {
            noPageMeta.setDisplayName("§4§lDer er ikke flere sider");
            noPage.setItemMeta(noPageMeta);
            claimGUI.setItem(50, noPage);
            int lastPage = page - 1;
            pageItemMeta.setDisplayName("§2§lSide " + lastPage);
            pageItem.setItemMeta(pageItemMeta);
            claimGUI.setItem(48, pageItem);
        } else if (page == 1 && page < itemStacks) {
            noPageMeta.setDisplayName("§4§lDu er på første side");
            noPage.setItemMeta(noPageMeta);
            claimGUI.setItem(48, noPage);
            int nextPage = page + 1;
            pageItemMeta.setDisplayName("§2§lSide " + nextPage);
            pageItem.setItemMeta(pageItemMeta);
            claimGUI.setItem(50, pageItem);
        }






        if (page > 1.0) {
            page = page * 44 - 44 + page - 1;
        } else {
            page = 0;
        }

        int count = 0;
        int itemCount;
        int finalCheck = 0;
        boolean final2Check = false;
        boolean itemCheck2 = false;
        for (String items : confSec.getKeys(true)){
            itemCount = 0;
            for (int i = 0; i < confSec.getInt(items); i++) {
                if (count >= page) {
                    if (itemCount < 64) {
                        if (count == page + 45) {
                            break;
                        }
                        itemCheck2 = true;
                        itemCount++;
                        finalCheck = itemCount;
                        Material convertToMaterial = Material.getMaterial(items);
                        ItemStack getItem = new ItemStack(convertToMaterial, 1);
                        claimGUI.addItem(getItem);
                    } else {
                        itemCheck2 = false;
                        itemCount = 0;
                        count++;

                        if (i != confSec.getInt(items)) {
                            i--;
                        }
                        if (count == page + 45) {
                            final2Check = true;
                            break;
                        }
                    }
                }else if (itemCount < 64 && count < page){
                    if (count == page + 45) {
                        break;
                    }
                    itemCheck2 = true;
                    itemCount++;
                    finalCheck = itemCount;
                }else{
                    itemCount = 0;
                    count++;

                    if (i != confSec.getInt(items)){
                        i--;
                    }

                    if (final2Check){
                        break;
                    }
                }
            }
            if (itemCheck2){
                count++;
            }
            if (finalCheck < 64){
                if (count == page + 45){
                    break;
                }
            }
            if (count == page + 45){
                break;
            }
        }
        ItemStack placeholderItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        placeholderItem.getItemMeta().setDisplayName("§1");
        claimGUI.setItem(45, placeholderItem);
        claimGUI.setItem(46, placeholderItem);
        claimGUI.setItem(47, placeholderItem);
        claimGUI.setItem(49, placeholderItem);
        claimGUI.setItem(51, placeholderItem);
        claimGUI.setItem(52, placeholderItem);
        claimGUI.setItem(53, placeholderItem);


        p.openInventory(claimGUI);
    }
}

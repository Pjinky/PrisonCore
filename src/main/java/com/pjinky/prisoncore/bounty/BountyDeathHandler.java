package com.pjinky.prisoncore.bounty;

import com.google.inject.Inject;
import com.pjinky.prisoncore.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.Listener;

import java.util.List;

public class BountyDeathHandler implements Listener{

    private ConfigHandler configHandler;
    private BountyPlayerConfig bountyPlayerConfig;
    private Main plugin;

    @Inject
    public BountyDeathHandler(ConfigHandler configHandler, BountyPlayerConfig bountyPlayerConfig, Main plugin){
        this.bountyPlayerConfig = bountyPlayerConfig;
        this.configHandler = configHandler;
        this.plugin = plugin;
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player attacker = e.getEntity().getKiller();
        Player player = e.getEntity().getPlayer();
        configHandler.load();
        List<String> activeBounty = configHandler.getConfig().getStringList("Active");
        if (attacker instanceof Player) {
            if (activeBounty.contains(player.getUniqueId().toString())) {
                bountyPlayerConfig.load(attacker);
                ConfigurationSection confSecAttacker = bountyPlayerConfig.getConfig().getConfigurationSection("ClaimItems");

                bountyPlayerConfig.load(player);
                int bountyMoney = bountyPlayerConfig.getConfig().getInt("Money");
                Economy getMoney = plugin.getEconomy();
                ConfigurationSection confSecPlayer = bountyPlayerConfig.getConfig().getConfigurationSection("Items");


                for (String item : confSecPlayer.getKeys(false)) {
                    if (!confSecAttacker.contains(item)) {
                        bountyPlayerConfig.load(player);
                        int itemAmountPlayer = bountyPlayerConfig.getConfig().getInt("Items." + item);
                        bountyPlayerConfig.getConfig().set("Items." + item, null);

                        bountyPlayerConfig.load(attacker);
                        bountyPlayerConfig.getConfig().set("ClaimItems." + item, itemAmountPlayer);
                        bountyPlayerConfig.saveConfig();
                    } else if (confSecAttacker.contains(item)) {
                        bountyPlayerConfig.load(player);
                        int itemAmountPlayer = bountyPlayerConfig.getConfig().getInt("Items." + item);
                        bountyPlayerConfig.getConfig().set("Items." + item, null);
                        bountyPlayerConfig.saveConfig();

                        bountyPlayerConfig.load(attacker);
                        int itemAmountAttacker = bountyPlayerConfig.getConfig().getInt("ClaimItems." + item);
                        itemAmountAttacker += itemAmountPlayer;
                        bountyPlayerConfig.getConfig().set("ClaimItems." + item, itemAmountAttacker);
                        bountyPlayerConfig.saveConfig();
                    }
                }

                List<String> playerList = configHandler.getConfig().getStringList("Active");
                playerList.remove(player.getUniqueId().toString());
                configHandler.getConfig().set("Active", playerList);
                configHandler.saveConfig();

                if (bountyMoney > 0) {
                    bountyPlayerConfig.load(player);
                    bountyPlayerConfig.getConfig().set("Money", null);
                    bountyPlayerConfig.saveConfig();
                    getMoney.depositPlayer(attacker, bountyMoney);
                    attacker.sendRawMessage("§8§l[ §5§lBOUNTY §8§l] §8§l» §2§l" + player.getName() + " §a§ohavde en dusør på sig, du har fået " + bountyMoney + "! Brug \"/dusør claim\" for at gøre krav på dine items!");
                } else {
                    attacker.sendRawMessage("§8§l[ §5§lBOUNTY §8§l] §8§l» §2§l" + player.getName() + " §a§ohavde en dusør på sig, brug \"/dusør claim\" for at gøre krav på dine items!");
                }
            }
        }
    }
}

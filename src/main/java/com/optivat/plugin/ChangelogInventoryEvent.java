package com.optivat.plugin;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChangelogInventoryEvent implements Listener {

    Changelogs main;
    public ChangelogInventoryEvent(Changelogs main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) {return;}
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "Changelogs")) {
            Player player = (Player) e.getWhoClicked();
            if(!main.playerPage.containsKey(player)) {
                main.playerPage.put(player, 1);
            }
            e.setCancelled(true);
            if(e.getSlot() == 53) {
                if (main.maxPages == main.playerPage.get(player)) {
                    player.sendMessage(ChatColor.RED + "You are already on the last page!");
                    Changelogs.playFailureSound(player);
                } else {
                    main.playerPage.replace(player, main.playerPage.get(player)+1);
                    GUIs.updateChangelogInventory(player, main.playerPage.get(player));
                }
            }
            if(e.getSlot() == 45) {
                if (main.maxPages == main.playerPage.get(player)) {
                    player.sendMessage(ChatColor.RED + "You are already on the first page!");
                    Changelogs.playFailureSound(player);
                } else {
                    main.playerPage.replace(player, main.playerPage.get(player)-1);
                    GUIs.updateChangelogInventory(player, main.playerPage.get(player));
                }
            }
        }
    }
}

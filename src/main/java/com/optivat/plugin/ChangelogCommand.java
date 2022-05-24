package com.optivat.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChangelogCommand implements CommandExecutor {
    private Changelogs main;
    public ChangelogCommand(Changelogs main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(!main.playerPage.containsKey(player)) {
                main.playerPage.put(player, 1);
            }
            GUIs.updateChangelogInventory(player, main.playerPage.get(player));
        }
        return false;
    }
    public static void setItem(ItemStack stack, String displayName, List<String> lore, int position, Inventory inv) {
        ItemStack item = stack;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        if(!lore.isEmpty()) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        inv.setItem(position, item);
    }

}

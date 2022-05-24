package com.optivat.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class GUIs {
    Changelogs main;
    public GUIs(Changelogs main) {
        this.main = main;
    }

    public static Inventory createBasicInventory(Player player, String name) {
        Inventory inv = Bukkit.createInventory(player, 54, name);
        player.openInventory(inv);
        for(int x = 0; x <=8; x++) {
            ChangelogCommand.setItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15), ChatColor.BLACK + "", Arrays.asList(), x, inv);
        }
        for(int x = 9; x <=44; x++) {
            ChangelogCommand.setItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15), ChatColor.BLACK + "", Arrays.asList(), x, inv);
            x+=8;
            ChangelogCommand.setItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15), ChatColor.BLACK + "", Arrays.asList(), x, inv);
        }
        for(int x = 45; x <=53; x++) {
            if(!(x == 45 || x == 53)) {
                ChangelogCommand.setItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15), ChatColor.BLACK + "", Arrays.asList(), x, inv);
            }
        }
        ChangelogCommand.setItem(new ItemStack(Material.ARROW, 1), ChatColor.RED + "Back", Arrays.asList(), 45, inv);
        ChangelogCommand.setItem(new ItemStack(Material.ARROW, 1), ChatColor.RED + "Next", Arrays.asList(), 53, inv);
        return inv;
    }
    public static Inventory createBasicInventory_1_18(Player player, String name) {
        Inventory inv = Bukkit.createInventory(player, 54, name);
        player.openInventory(inv);
        for(int x = 0; x <=8; x++) {
            setItem1_18(inv, x);
        }
        for(int x = 9; x <=44; x++) {
            setItem1_18(inv, x);
            x+=8;
            setItem1_18(inv, x);
        }
        for(int x = 45; x <=53; x++) {
            if(!(x == 45 || x == 53)) {
                setItem1_18(inv, x);
            }
        }
        ChangelogCommand.setItem(new ItemStack(Material.ARROW, 1), ChatColor.RED + "Back", Arrays.asList(), 45, inv);
        ChangelogCommand.setItem(new ItemStack(Material.ARROW, 1), ChatColor.RED + "Next", Arrays.asList(), 53, inv);
        return inv;
    }

    private static void setItem1_18(Inventory inv, int x) {
        try {
            Class materialClass = Class.forName("org.bukkit.Material");
            Method getMaterial = materialClass.getMethod("valueOf", String.class);
            Material mat = (Material) getMaterial.invoke(null, "BLACK_STAINED_GLASS_PANE");
            ChangelogCommand.setItem(new ItemStack(mat, 1), ChatColor.BLACK + "", Arrays.asList(), x, inv);

        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Inventory updateChangelogInventory(Player player, int page) {
        Inventory inv;
        if (Changelogs.version1_18()) {
            inv = createBasicInventory_1_18(player, ChatColor.GOLD.toString() + ChatColor.BOLD + "Changelogs");
        } else {
            inv = createBasicInventory(player, ChatColor.GOLD.toString() + ChatColor.BOLD + "Changelogs");
        }
        try {
            Reader reader = new FileReader(Changelogs.file);
            Gson gson = new Gson();
            JsonStreamParser p = new JsonStreamParser(reader);
            int position = 10;
            while (p.hasNext()) {
                JsonElement jsonElement = p.next();
                if (jsonElement.isJsonObject()) {
                    Changelog changelog = gson.fromJson(jsonElement, Changelog.class);
                    if(changelog.getChangelogPage() == page) {
                        if(Changelogs.version1_18()) {
                            ChangelogCommand.setItem(changelog.getItemStack1_18(), changelog.getDisplayName(), changelog.getLore(), position, inv);
                        } else {
                            ChangelogCommand.setItem(changelog.getItemStack(), changelog.getDisplayName(), changelog.getLore(), position, inv);
                        }
                        if(position == 16 || position == 25 || position == 34 || position == 43) {
                            position+=3;
                        } else {
                            position+=1;
                        }
                    }
                }
                /* handle other JSON data structures */
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inv;
    }
}

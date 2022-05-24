package com.optivat.plugin;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public final class Changelogs extends JavaPlugin {
    public static File file;
    public int maxPages = getConfig().getInt("maxPages");
    HashMap<Player, Integer> playerPage = new HashMap<>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        GUIs gui = new GUIs(this);
        getCommand("changelog").setExecutor(new ChangelogCommand(this));
        Bukkit.getPluginManager().registerEvents(new ChangelogInventoryEvent(this), this);
        try {
            getDataFolder().mkdir();
            if(version1_18()) {
                file = new File(getDataFolder(), "changelogs_1_18.json");
            } else {
                file = new File(getDataFolder(), "changelogs.json");
            }
            fileCreation();
        } catch (IOException e) {
            if(version1_18()) {
                System.out.println("Error! Unable to create " + "changelogs_1_18.json");
            } else {
                System.out.println("Error! Unable to create " + "changelogs.json");
            }
        }
    }

    private void fileCreation() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            Changelog changelog = new Changelog(ChatColor.RED + "v0.1", Arrays.asList(ChatColor.RED + "first line!", ChatColor.GOLD.toString() + ChatColor.BOLD + "second line...", ChatColor.STRIKETHROUGH + "amogus"), new ItemStack(Material.BEDROCK, 1), 1);
            Gson gson = new Gson();
            Writer writer = new FileWriter(file, false);
            gson.toJson(changelog, writer);
            writer.flush();
            writer.close();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static boolean version1_18() {
        if(!(Bukkit.getBukkitVersion().contains("1.8") || Bukkit.getVersion().contains("1.7") || Bukkit.getBukkitVersion().contains("1.9") || Bukkit.getBukkitVersion().contains("1.10") || Bukkit.getBukkitVersion().contains("1.11") || Bukkit.getBukkitVersion().contains("1.12"))) {
            return true;
        }
        return false;
    }
    public static void playFailureSound(Player player) {
        if(version1_18()) {
            try {
                Class soundClass = Class.forName("org.bukkit.Sound");
                Method getSound = soundClass.getMethod("valueOf", String.class);
                Sound sound = (Sound) getSound.invoke(null, "BLOCK_ANVIL_LAND");
                player.playSound(player.getLocation(), sound, 1, 0.5F);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        } else {
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 0.5F);
        }
    }
}

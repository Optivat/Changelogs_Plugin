package com.optivat.plugin;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Changelog {
    private String displayName;
    private List<String> lore;
    private ItemStack itemStack;
    private ItemStack itemStack1_18;
    private int changelogPage;
    public Changelog(String dN, List<String> l, ItemStack iS, int cP) {
        displayName = dN;
        lore = l;
        if(Changelogs.version1_18()) {
            itemStack1_18 = iS;
            itemStack = null;
        } else {
            itemStack = iS;
            itemStack1_18 = null;
        }
        changelogPage = cP;
    }
    public String getDisplayName() { return displayName; }
    public List<String> getLore() { return lore; }
    public ItemStack getItemStack() { return itemStack; }
    public ItemStack getItemStack1_18() { return itemStack1_18; }
    public int getChangelogPage() { return changelogPage; }
}

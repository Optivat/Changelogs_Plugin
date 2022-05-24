package com.optivat.plugin;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Changelog {
    private String displayName;
    private List<String> lore;
    private ItemStack itemStack;
    private int changelogPage;
    public Changelog(String dN, List<String> l, ItemStack iS, int cP) {
        displayName = dN;
        lore = l;
        itemStack = iS;
        changelogPage = cP;
    }
    public String getDisplayName() { return displayName; }
    public List<String> getLore() { return lore; }
    public ItemStack getItemStack() { return itemStack; }
    public int getChangelogPage() { return changelogPage; }
}

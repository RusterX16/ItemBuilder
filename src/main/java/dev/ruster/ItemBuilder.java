package dev.ruster;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;
    private final List<String> lore = new ArrayList<>();
    private Material material;
    private String displayName;
    private int amount;

    /**
     * Create a new ItemBuilder
     *
     * @param item The ItemStack to build from
     */
    public ItemBuilder(ItemStack item) {
        this.item = new ItemStack(item);
        meta = item.getItemMeta();
        material = item.getType();
        amount = item.getAmount();
    }

    /**
     * Create a new ItemBuilder
     *
     * @param material The material of the item
     * @param amount   The amount of item
     */
    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        meta = item.getItemMeta();
        this.material = material;
        this.amount = amount;
    }

    /**
     * Create a new ItemBuilder
     *
     * @param material The material of the item
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    /**
     * Create a new ItemBuilder
     *
     * @param builder The ItemBuilder to copy from
     */
    public ItemBuilder(@NotNull ItemBuilder builder) {
        item = new ItemStack(builder.item);
        meta = item.getItemMeta();
        material = builder.material;
        displayName = builder.displayName;
        amount = builder.amount;
    }

    /**
     * Change the material of the item
     *
     * @param material The material to set
     */
    public ItemBuilder material(Material material) {
        this.material = material;
        item.setType(material);
        return this;
    }

    /**
     * Set a new display name to the item
     *
     * @param displayName The name to display
     */
    public ItemBuilder displayName(String displayName) {
        this.displayName = displayName;
        meta.setDisplayName(displayName);
        return this;
    }

    /**
     * Define an item lore
     * overrides existing lore
     *
     * @param lore A collection of lines to set as lore
     */
    public ItemBuilder lore(List<String> lore) {
        this.lore.addAll(lore);
        meta.setLore(lore);
        return this;
    }

    /**
     * Define an item lore
     * overrides existing lore
     *
     * @param lines the lines set as lore
     */
    public ItemBuilder lore(String... lines) {
        this.lore.addAll(List.of(lines));
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder appendLoreLine(String line) {
        lore.add(line);
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder setLoreLine(int index, String line, boolean override) {
        if(override) {
            lore.set(index, line);
        } else {
            lore.add(index, line);
        }
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder setLoreLine(int index, String line) {
        return setLoreLine(index, line, false);
    }

    public ItemBuilder removeLoreLine(int index) {
        lore.remove(index);
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder removeLoreLine(String line) {
        lore.removeAll(Collections.singleton(line));
        meta.setLore(lore);
        return this;
    }

    /**
     * Change the amount of the item
     *
     * @param amount The amount to set
     */
    public ItemBuilder amount(int amount) {
        this.amount = amount;
        item.setAmount(amount);
        return this;
    }

    /**
     * Build the item
     *
     * @return The ItemStack
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Get the material type of the item
     *
     * @return The material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Get the amount of the item
     *
     * @return The amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Get the item display name
     *
     * @return The display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the item lore
     *
     * @return The lore
     */
    public List<String> getLore() {
        return lore;
    }

    @Override
    public String toString() {
        return "ItemBuilder{" +
                "item=" + item +
                ", meta=" + meta +
                ", lore=" + lore +
                ", material=" + material +
                ", displayName='" + displayName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
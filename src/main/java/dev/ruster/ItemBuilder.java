package dev.ruster;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;
    private final Lore lore = new Lore(this);
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
        item.setType(material);
        this.material = material;
        return this;
    }

    /**
     * Get the material type of the item
     *
     * @return The material
     */
    public Material material() {
        return material;
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
     * Get the item display name
     *
     * @return The display name
     */
    public String displayName() {
        return displayName;
    }

    /**
     * Define an item lore
     *
     * @param lore The Lore to copy from
     */
    public ItemBuilder lore(@NotNull Lore lore) {
        this.lore.set(lore.toList());
        return this;
    }

    /**
     * Define an item lore
     *
     * @param lore The ArrayList containing the lines you want to display as lore
     */
    public ItemBuilder lore(List<String> lore) {
        this.lore.set(lore);
        return this;
    }

    /**
     * Get the item lore
     *
     * @return The lore
     */
    public Lore lore() {
        return lore;
    }

    /**
     * Change the amount of the item
     *
     * @param amount The amount to set
     */
    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        this.amount = amount;
        return this;
    }

    /**
     * Get the amount of the item
     *
     * @return The amount
     */
    public int amount() {
        return amount;
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
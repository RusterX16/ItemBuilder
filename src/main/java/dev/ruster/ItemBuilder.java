package dev.ruster;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder {

    private final ItemStack item;
    private Material material;
    private int amount;

    /**
     * Create a new ItemBuilder
     * @param material The material of the item
     * @param amount The amount of item
     */
    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        this.material = material;
        this.amount = amount;
    }

    /**
     * Create a new ItemBuilder
     * @param material The material of the item
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    /**
     * Change the material of the item
     * @param material The material to set
     */
    public void material(Material material) {
        item.setType(material);
        this.material = material;
    }

    /**
     * Get the material type of the item
     * @return The material to set
     */
    public Material material() {
        return material;
    }

    /**
     * Change the amount of the item
     * @param amount The amount to set
     */
    public void amount(int amount) {
        item.setAmount(amount);
        this.amount = amount;
    }

    /**
     * Get the amount of the item
     * @return The amount
     */
    public int amount() {
        return amount;
    }

    /**
     * Build the item
     * @return The ItemStack
     */
    public ItemStack build() {
        return item;
    }
}
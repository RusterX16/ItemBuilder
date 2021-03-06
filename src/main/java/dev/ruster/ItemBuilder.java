package dev.ruster;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * <p><strong>Please do not remove these lines !!</strong></p>
 * <p>ItemBuilder is a tiny Java API for Spigot and Paper that helps you to create ItemStack instance way much easier</p>
 * <p>You're free to use this class and contribute to the project</p>
 * <p>For any question, info or bug, please contact me from my GtiHub profile down below</p>
 * @author RusterX16
 * @link <a href="https://github.com/RusterX16/ItemBuilder">github</a>
 */
public class ItemBuilder {

    private static final List<ItemBuilder> ITEM_BUILDERS = new LinkedList<>();

    private final ItemStack item;
    private final ItemMeta meta;
    private final Damageable damageable;
    private final List<String> lore = new ArrayList<>();
    private final Set<ItemFlag> flags = new HashSet<>();
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();
    private Material material;
    private String displayName;
    private int amount;
    private short durability;
    private boolean unbreakable;

    /**
     * Create a new ItemBuilder
     *
     * @param item The ItemStack to build from
     */
    public ItemBuilder(ItemStack item) {
        this.item = new ItemStack(item);
        material = item.getType();
        amount = item.getAmount();
        durability = item.getDurability();
        enchantments.putAll(item.getEnchantments());
        meta = item.getItemMeta();
        damageable = (Damageable) meta;

        if(item.hasItemMeta()) {
            if(meta.hasDisplayName()) {
                displayName = meta.getDisplayName();
            }
            if(meta.hasLore()) {
                if(meta.hasLore()) {
                    lore.addAll(meta.getLore());
                }
            }
            flags.addAll(meta.getItemFlags());
            durability = (short) ((Damageable) meta).getDamage();
        }
        final FileConfiguration config = YamlConfiguration.loadConfiguration(new File("itembuilder.yml"));

        if(config.getBoolean("storeItemBuilder")) {
            ITEM_BUILDERS.add(this);
        }
    }

    public static ItemBuilder from(@NotNull ItemStack item) {
        return ITEM_BUILDERS.stream()
                .filter(ib -> ib.item.equals(item))
                .findFirst()
                .orElse(null);
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
        damageable = (Damageable) meta;
        this.material = material;
        this.amount = amount;
        durability = material.getMaxDurability();
        unbreakable = false;
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
        damageable = (Damageable) meta;
        material = builder.material;
        displayName = builder.displayName;
        amount = builder.amount;
        durability = builder.durability;
        unbreakable = builder.unbreakable;
        lore.addAll(builder.lore);
        flags.addAll(builder.flags);
        enchantments.putAll(builder.enchantments);
    }

    /**
     * Change the material of the item
     *
     * @param material The material to set
     * @return The ItemBuilder
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
     * @return The ItemBuilder
     */
    public ItemBuilder displayName(String displayName) {
        this.displayName = displayName;
        meta.displayName(Component.text(displayName));
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Color a display name with the given color
     *
     * @param color The color
     * @return The ItemBuilder
     */
    public ItemBuilder colorName(ChatColor color) {
        return displayName(color + displayName);
    }

    /**
     * Color a display name with the given code
     *
     * @param code The color code
     * @return The ItemBuilder
     */
    public ItemBuilder colorName(String code) {
        return colorName(ChatColor.getByChar(code));
    }

    /**
     * Define an item lore
     * overrides existing lore
     *
     * @param lore A collection of lines to set as lore
     * @return The ItemBuilder
     */
    public ItemBuilder lore(List<String> lore) {
        clearLore();
        this.lore.addAll(lore);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Define an item lore
     * overrides existing lore
     *
     * @param lines the lines set as lore
     * @return The ItemBuilder
     */
    public ItemBuilder lore(String... lines) {
        clearLore();
        this.lore.addAll(List.of(lines));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Add a new line to the item lore
     *
     * @param line The text to append
     * @return The ItemBuilder
     */
    public ItemBuilder appendLoreLine(String line) {
        lore.add(line);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Define a new line in the lore on the given index.<br>
     *
     * @param index    The index where to set the line
     * @param line     The text to display on lore
     * @param override <br><b>true</b> : replace existing line<br><b>false</b> : shifts the next elements
     * @return The ItemBuilder
     */
    public ItemBuilder setLoreLine(int index, String line, boolean override) {
        if(override) {
            lore.set(index, line);
        } else {
            lore.add(index, line);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Define a new line in the lore on the given index.<br>
     * Will override the current line if existing
     *
     * @param index The index where to set the line
     * @param line  The text to display on lore
     * @return The ItemBuilder
     */
    public ItemBuilder setLoreLine(int index, String line) {
        return setLoreLine(index, line, true);
    }

    /**
     * Remove a line of the lore by the index of where the line is
     *
     * @param index The index of the line to delete
     * @return The ItemBuilder
     */
    public ItemBuilder removeLoreLine(int index) {
        lore.remove(index);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Remove the given line of the lore
     *
     * @param line The line to delete
     * @return The ItemBuilder
     */
    public ItemBuilder removeLoreLine(String line) {
        lore.removeAll(Collections.singleton(line));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Clear the lore
     *
     * @return The ItemBuilder
     */
    public ItemBuilder clearLore() {
        lore.clear();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * remove ItemFlag from the item
     *
     * @param flags The flag to hide
     * @return The ItemBuilder
     */
    public ItemBuilder hideFlag(ItemFlag... flags) {
        this.flags.addAll(List.of(flags));
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Hide all ItemFlags from the item
     *
     * @return The ItemBuilder
     */
    public ItemBuilder hideFlags() {
        return hideFlag(ItemFlag.values());
    }

    /**
     * Add ItemFlag to the item
     *
     * @param flags The flag to show
     * @return THe ItemBuilder
     */
    public ItemBuilder showFlag(ItemFlag... flags) {
        List.of(flags).forEach(this.flags::remove);
        meta.removeItemFlags(flags);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Show all ItemFlags on the item
     *
     * @return The ItemBuilder
     */
    public ItemBuilder showFlags() {
        return showFlag(ItemFlag.values());
    }

    public ItemBuilder enchantment(Map<Enchantment, Integer> enchantments) {
        this.enchantments.putAll(enchantments);
        enchantments.forEach((e, i) -> meta.addEnchant(e, i, true));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment, int level) {
        enchantments.entrySet().stream()
                .filter(e -> e.getValue() == level)
                .forEach(e -> {
                    enchantments.remove(enchantment, level);
                    meta.removeEnchant(e.getKey());
                });
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeEnchantments(Enchantment... enchantments) {
        List.of(enchantments).forEach(e -> {
            this.enchantments.remove(e);
            meta.removeEnchant(e);
        });
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeEnchantments(int @NotNull ... levels) {
        enchantments.forEach((key, value) -> Arrays.stream(levels)
                .filter(l -> value == l)
                .forEach(l -> {
                    enchantments.remove(key);
                    meta.removeEnchant(key);
                }));
        item.setItemMeta(meta);
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
     * Set a durability on the item.
     *
     * @param durability The new durability of the item
     * @return The ItemBuilder
     */
    public ItemBuilder damage(short durability) {
        this.durability = durability;
        damageable.setDamage(durability);
        item.setItemMeta(damageable);
        return this;
    }

    /**
     * Damage the item. The value given will be subtracted to the current item life.
     *
     * @param damage The damage to deal
     * @return The ItemBuilder
     */
    public ItemBuilder durability(short damage) {
        return damage((short) (item.getMaxItemUseDuration() - damage));
    }

    /**
     * Color the item.<br>
     * Only available for item that supports LeatherArmorMeta.
     * @param color The color to apply
     * @return the ItemBuilder
     */
    public ItemBuilder dye(Color color) {
        LeatherArmorMeta armor = (LeatherArmorMeta) this.meta;
        armor.setColor(color);
        item.setItemMeta(armor);
        return this;
    }

    /**
     * Set the item unbreakable
     * @param unbreakable <br><b>true</b> : The item will never take damage<br><b>false</b> : Basic behavior of an item
     * @return the ItemBuilder
     */
    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return this;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != getClass()) {
            return false;
        }
        ItemBuilder builder = (ItemBuilder) obj;
        return builder.item.equals(item) &&
                builder.meta.equals(meta) &&
                builder.material.equals(material) &&
                builder.displayName.equals(displayName) &&
                builder.amount == amount &&
                builder.durability == durability &&
                builder.lore.equals(lore) &&
                builder.flags.equals(flags) &&
                builder.enchantments.equals(enchantments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, meta, lore, flags, enchantments, material, displayName, amount, durability);
    }

    /**
     * Build the item
     *
     * @return The ItemStack
     */
    public ItemStack build() {
        return item;
    }

    /**
     * Get the instance of the ItemBuilder by a given ItemStack
     *
     * @param item The ItemStack to get from
     * @return The ItemBuilder instance
     */
    public ItemBuilder getFromItemStack(@NotNull ItemStack item) {
        if(item.isSimilar(this.item)) {
            return this;
        }
        return null;
    }

    /**
     * Get the ItemStack
     *
     * @return The ItemStack
     */
    public ItemStack getItemStack() {
        return item;
    }

    /**
     * Get the ItemMeta
     *
     * @return The ItemMeta
     */
    public ItemMeta getMeta() {
        return meta;
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
     * Get the item durability
     *
     * @return The durability
     */
    public short getDurability() {
        return durability;
    }

    /**
     * Get the item lore
     *
     * @return The lore
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Get the item flags
     *
     * @return The flags
     */
    public Set<ItemFlag> getFlags() {
        return flags;
    }

    /**
     * Get the state of the item flags
     *
     * @return The state of the item flags<br><b>true</b> if item flags are hidden<br><b>false</b> if item flags are visible
     */
    public boolean hasFlags() {
        return !flags.isEmpty();
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

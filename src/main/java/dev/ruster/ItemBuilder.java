package dev.ruster;

import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * <p><b>Please do not remove these lines !!</b></p>
 * <p>ItemBuilder is a tiny Java API for Spigot and Paper that helps you to create ItemStack instance way much easier.</p>
 * <p>You're free to use this class and contribute to the project.</p>
 * <p>For any question, info or bug, please contact me from my GtiHub profile down below.</p>
 *
 * @author RusterX16
 * @link <a href="https://github.com/RusterX16/ItemBuilder">GitHub</a>
 */
@ToString
@EqualsAndHashCode
@SuppressWarnings("unused")
public class ItemBuilder {

    /**
     * <p>A list of ItemBuilder that contains all instance you create.</p>
     * <p>It used for recover the ItemBuilder instance from ItemStack anywhere on your code.</p>
     * <p>You can disable it from <b>config.yml</b> if you're not using this feature at all to save memory.</p>
     */
    private static final List<ItemBuilder> ITEM_BUILDER_LIST = new LinkedList<>();

    /**
     * The ItemStack instance
     */
    private final ItemStack item;
    private final ItemMeta meta;
    private final Damageable damageable;
    private final List<Component> lore = new ArrayList<>();
    private final Set<ItemFlag> flags = new HashSet<>();
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();
    private Material material;
    private Component displayName;
    private int amount;
    private short durability;
    private boolean unbreakable;
    private boolean save = false;

    /**
     * Create a new ItemBuilder
     *
     * @param item The ItemStack to build from
     */
    public ItemBuilder(ItemStack item) {
        this.item = new ItemStack(item);
        material = item.getType();
        amount = item.getAmount();
        enchantments.putAll(item.getEnchantments());
        meta = item.getItemMeta();
        damageable = (Damageable) meta;

        if (item.hasItemMeta()) {
            if (meta.hasDisplayName()) {
                displayName = meta.displayName();
            }
            if (meta.hasLore()) {
                lore.addAll(Objects.requireNonNull(meta.lore()));
            }
            flags.addAll(meta.getItemFlags());
            durability = (short) ((Damageable) meta).getDamage();
        }
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
        save = builder.save;
        item.setItemMeta(meta);
    }

    /**
     * <p>Get the ItemBuilder instance from the ItemStack one.</p>
     * <p>Will return <b>null</b> if the ItemStack hasn't been created from ItemBuilder class</p>
     *
     * @param item The ItemStack
     * @return The ItemBuilder instance if exists in the list, <b>null</b> otherwise.
     */
    public static ItemBuilder from(@NotNull ItemStack item) {
        return ITEM_BUILDER_LIST.stream().filter(ib -> ib.item == item).findFirst().orElse(null);
    }

    /**
     * Save the instance into a Collection that allow you to recover it from anywhere in your code in case of modifying
     *
     * @return The ItemBuilder
     */
    public ItemBuilder save() {
        ITEM_BUILDER_LIST.add(this);
        this.save = true;
        return this;
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
        this.displayName = Component.text(displayName);
        meta.displayName(this.displayName);
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
        return displayName(color + displayName.insertion());
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
    public ItemBuilder lore(@NotNull List<String> lore) {
        clearLore();
        lore.forEach(l -> this.lore.add(Component.text(l)));
        meta.lore(this.lore);
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
        return lore(List.of(lines));
    }

    /**
     * Add a new line to the item lore
     *
     * @param line The text to append
     * @return The ItemBuilder
     */
    public ItemBuilder appendLoreLine(String line) {
        lore.add(Component.text(line));
        meta.lore(lore);
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
        final Component component = Component.text(line);

        if (override) {
            lore.set(index, component);
        } else {
            lore.add(index, component);
        }
        meta.lore(lore);
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
        meta.lore(lore);
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
        lore.removeAll(Collections.singleton(Component.text(line)));
        meta.lore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Clear the lore
     *
     * @return The ItemBuilder
     */
    @SuppressWarnings("UnusedReturnValue")
    public ItemBuilder clearLore() {
        lore.clear();
        meta.lore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * remove ItemFlag from the item
     *
     * @param flags The flag to hide
     * @return The ItemBuilder
     */
    public ItemBuilder hideFlag(ItemFlag @NotNull @NotEmpty ... flags) {
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
     * @return The ItemBuilder
     */
    public ItemBuilder showFlag(ItemFlag @NotNull @NotEmpty ... flags) {
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

    /**
     * Add some enchantments to the item by passing a Map in parameter
     *
     * @param enchantments The Map of enchantements
     * @return The ItemBuilder
     */
    public ItemBuilder enchantment(Map<Enchantment, Integer> enchantments) {
        this.enchantments.putAll(enchantments);
        enchantments.forEach((e, i) -> meta.addEnchant(e, i, true));
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Add a single enchantment to the item
     *
     * @param enchantment The enchantement to add
     * @param level       The level of the enchantment
     * @return The ItemBuilder
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Remove all enchantment from the item that match with the enchantement and the given level
     *
     * @param enchantment The enchantment to remove
     * @param level       The level of the enchantment to remove
     * @return            The ItemBuilder
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment, int level) {
        enchantments.entrySet().stream().filter(e -> e.getValue() == level).forEach(e -> {
            enchantments.remove(enchantment, level);
            meta.removeEnchant(e.getKey());
        });
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Remove some enchantments from the item by a collection of enchantments given in parameter
     *
     * @param enchantments The enchantements to remove
     * @return The ItemBuilder
     */
    public ItemBuilder removeEnchantments(Enchantment... enchantments) {
        List.of(enchantments).forEach(e -> {
            this.enchantments.remove(e);
            meta.removeEnchant(e);
        });
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Remove some enchantments from the item by a collection of level that match the enchantments given in parameter
     *
     * @param levels The levels of enchantments to remove
     * @return The ItemBuilder
     */
    public ItemBuilder removeEnchantments(int @NotNull ... levels) {
        enchantments.forEach((key, value) -> Arrays.stream(levels).filter(l -> value == l).forEach(l -> {
            enchantments.remove(key);
            meta.removeEnchant(key);
        }));
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Check if the item has some enchantments
     * @return true if the item has some enchantments
     */
    public boolean hasEnchantments() {
        return !enchantments.isEmpty();
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
     *
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
     *
     * @param unbreakable <br><b>true</b> : The item will never take damage<br><b>false</b> : Basic behavior of an item
     * @return the ItemBuilder
     */
    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return this;
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
    public Component getDisplayName() {
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
    public List<Component> getLore() {
        return lore;
    }

    /**
     * Get the item enchantments
     * @return The enchantments
     */
    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
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

    /**
     * Check if the item is unbreakable or not
     *
     * @return The state of the item break ability<br>
     * <b>true</b> if unbreakable<br>
     * <b>false</b> if breakable<br>
     */
    public boolean isUnbreakable() {
        return unbreakable;
    }
}

package dev.ruster;

import java.util.*;

public class Lore {

    private final ItemBuilder builder;
    private final List<String> lore = new ArrayList<>();

    public Lore(ItemBuilder builder) {
        this.builder = builder;
    }

    public Lore set(Collection<String> lore) {
        this.lore.addAll(lore);
        return this;
    }

    public Lore add(String... lines) {
        Collections.addAll(lore, lines);
        return this;
    }

    public Lore remove(String... lines) {
        Arrays.stream(lines).forEach(lore::remove);
        return this;
    }

    public Lore clear() {
        lore.clear();
        return this;
    }

    public List<String> toList() {
        return lore;
    }

    public ItemBuilder builder() {
        return builder;
    }
}

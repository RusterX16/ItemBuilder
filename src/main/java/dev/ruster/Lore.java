package dev.ruster;

import java.util.*;

public class Lore {

    private final ItemBuilder builder;
    private final List<String> loreList = new ArrayList<>();

    public Lore(ItemBuilder builder) {
        this.builder = builder;
    }

    public Lore set(Collection<String> lore) {
        this.loreList.addAll(lore);
        return this;
    }

    public Lore add(String... lines) {
        Collections.addAll(loreList, lines);
        return this;
    }

    public Lore remove(String... lines) {
        Arrays.stream(lines).forEach(loreList::remove);
        return this;
    }

    public Lore clear() {
        loreList.clear();
        return this;
    }

    public List<String> toList() {
        return loreList;
    }

    public ItemBuilder builder() {
        return builder;
    }
}

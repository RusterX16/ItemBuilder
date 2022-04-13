package dev.ruster;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Main {

    public static void main(String[] args) {
        ItemStack i = new ItemBuilder(Material.DIAMOND_SWORD)
                .displayName("Epee puissante")
                .amount(2)
                .lore("Tac tac", "Boom")
                .build();
    }
}

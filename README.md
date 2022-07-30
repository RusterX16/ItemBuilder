# ItemBuilder

ItemBuilder is a tiny Java API for Paper and Spigot that help you to create ItemStack way much easier.  
Thank you for reading this repository. You're free to use this class in your projet and contribute to it if you want to.
<br/>
<br/>
- Creating ItemBuilder objects :  
\
Instead of creating ItemStack like this :
<pre>
ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
ItemMeta meta = diamond.getItemMeta();
meta.displayName(Component.text("A Minecraft Diamond"));
meta.lore(List.of(Component.text("§bShining")));
item.setItemMeta(meta);

player.getInventory().add(diamond);
</pre>
\
You will do able to write this :
<pre>
ItemStack diamond = new ItemBuilder(Material.DIAMOND, 1)
        .displayName("A Minecraft diamond")
        .lore("§bShining")
        .build();
        
player.getInventory().add(diamond);
</pre>
\
- Save your ItemBuilder instance :
Use the `save()` method to save the ItemBuilder instance to the momery if you want to edit one more time later :  
<pre>
ItemStack sword = new ItemBuilder(Material.IRON_SWORD)
        .addEnchantment(Enchantment.DAMAGE_ALL, 5)
        .hideFlags()
        .unbreakable()
        .save()
        .build();
</pre>
\
Then, anywhere on your code, you will able to get back the instance with the `from(ItemStack)` method :  
<pre>
ItemBuilder builder = ItemBuilder.from(sword);
</pre>

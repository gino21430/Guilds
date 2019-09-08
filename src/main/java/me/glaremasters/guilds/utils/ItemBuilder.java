package me.glaremasters.guilds.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Glare Date: 4/8/2019 Time: 2:22 PM
 */
public class ItemBuilder {

  private final ItemStack item;
  private final ItemMeta meta;

  /**
   * Constructor to build an item
   *
   * @param item the item to use
   */
  public ItemBuilder(@NotNull ItemStack item) {
    this.item = item;
    meta = item.getItemMeta();
    Validate.notNull(meta, "Item/material must have ItemMeta");
  }

  /**
   * Constructor to make an item
   *
   * @param material the material of the item
   */
  public ItemBuilder(@NotNull Material material) {
    this(new ItemStack(material));
  }

  /**
   * Build an item
   *
   * @return itemstack
   */
  @NotNull
  @Contract(pure = true)
  public ItemStack build() {
    item.setItemMeta(meta);
    return item;
  }

  /**
   * Set the amount of an item
   *
   * @param amount the amount of an item
   * @return amounted item
   */
  @NotNull
  public ItemBuilder setAmount(int amount) {
    item.setAmount(amount);
    return this;
  }

  /**
   * Set the name of an item
   *
   * @param name the name of the item
   * @return item with name
   */
  @NotNull
  public ItemBuilder setName(@NotNull String name) {
    meta.setDisplayName(name);
    return this;
  }

  /**
   * Set the lore of an item
   *
   * @param lore the lore to add
   * @return item with lore
   */
  @NotNull
  public ItemBuilder setLore(@NotNull List<String> lore) {
    meta.setLore(lore);
    return this;
  }

  /**
   * Set the lore of an item
   *
   * @param lore the lore to add
   * @return item with lore
   */
  @NotNull
  public ItemBuilder setLore(@NotNull String... lore) {
    meta.setLore(Arrays.asList(lore));
    return this;
  }

  /**
   * Add enachants to an item
   *
   * @param enchantment the enchant to add
   * @param level       the level of the enchant
   * @return the item with enchantments
   */
  @NotNull
  public ItemBuilder addEnchantment(@NotNull Enchantment enchantment, int level) {
    meta.addEnchant(enchantment, level, true);
    return this;
  }

  /**
   * Add a custom item flag to itembuilder
   *
   * @param flags the flags to add
   * @return itembuilder item with custom flags
   */
  @NotNull
  public ItemBuilder addItemFlags(@NotNull ItemFlag... flags) {
    meta.addItemFlags(flags);
    return this;
  }

  /**
   * Sets an itembuilder item to be unbreakable
   *
   * @param unbreakable the option
   * @return itembuilder with unbreakable option
   */
  @NotNull
  public ItemBuilder setUnbreakable(boolean unbreakable) {
    meta.setUnbreakable(unbreakable);
    return this;
  }

  /**
   * Apply custom meta to an item
   *
   * @param type    the kind of meta
   * @param applier the type to apply
   * @param <T>     the type to add
   * @return itembuilder with custom meta
   */
  @NotNull
  public <T extends ItemMeta> ItemBuilder applyCustomMeta(@NotNull Class<T> type,
      @NotNull Consumer<T> applier) {
    applier.accept(type.cast(meta));
    return this;
  }
}

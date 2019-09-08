package me.glaremasters.guilds.listeners;

import static co.aikar.commands.CommandManager.getCurrentCommandManager;

import ch.jalu.configme.SettingsManager;
import me.glaremasters.guilds.Guilds;
import me.glaremasters.guilds.configuration.sections.GuildVaultSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class VaultReturnPickerListener implements Listener {

  private Guilds guilds;
  private SettingsManager settingsManager;

  public VaultReturnPickerListener(Guilds guilds, SettingsManager settingsManager) {
    this.guilds = guilds;
    this.settingsManager = settingsManager;
  }

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    if (event.getSlot() != -999) {
      return;
    }
    String vaultName = ChatColor.translateAlternateColorCodes('&',
        settingsManager.getProperty(GuildVaultSettings.VAULT_NAME));
    if (event.getInventory().getName().equals(vaultName)) {
      if (event.getWhoClicked() instanceof Player) {
        Player player = (Player) event.getWhoClicked();
        Bukkit.getScheduler().runTaskLater(guilds, () -> guilds.getGuiHandler().getVaultGUI()
            .getVaultGUI(guilds.getGuildHandler().getGuild(player), player,
                getCurrentCommandManager())
            .show(player), 1);
      }
    }
  }
}

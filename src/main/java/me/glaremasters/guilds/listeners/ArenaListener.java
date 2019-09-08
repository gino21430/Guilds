package me.glaremasters.guilds.listeners;

import ch.jalu.configme.SettingsManager;
import co.aikar.commands.ACFBukkitUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.glaremasters.guilds.Guilds;
import me.glaremasters.guilds.challenges.ChallengeHandler;
import me.glaremasters.guilds.configuration.sections.WarSettings;
import me.glaremasters.guilds.guild.GuildChallenge;
import me.glaremasters.guilds.messages.Messages;
import me.glaremasters.guilds.utils.Constants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ArenaListener implements Listener {

  private Guilds guilds;
  private ChallengeHandler challengeHandler;
  private SettingsManager settingsManager;
  private final Map<UUID, String> playerDeath = new HashMap<>();

  public ArenaListener(Guilds guilds, ChallengeHandler challengeHandler,
      SettingsManager settingsManager) {
    this.guilds = guilds;
    this.challengeHandler = challengeHandler;
    this.settingsManager = settingsManager;
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    GuildChallenge challenge = challengeHandler.getChallenge(player);
    // Check if they are part of an active challenge
    if (challenge != null) {
      // Announce they left
      challengeHandler.announceDeath(challenge, guilds, player, player,
          ChallengeHandler.Cause.PLAYER_KILLED_QUIT);
      // Remove the player from the alive players
      challengeHandler.handleFinish(guilds, settingsManager, player, challenge);
    }
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();

    // Get the challenge object
    GuildChallenge challenge = challengeHandler.getChallenge(player);

    // If it's null, stop here
    if (challenge == null) {
      return;
    }
    // Make sure the challenge is started
    if (!challenge.isStarted()) {
      return;
    }
    // Keep the inventory
    event.setKeepInventory(true);

    // Due to 1.14.4 new dupe bug, let's stop this
    event.getDrops().clear();

    // Keep the levels
    event.setKeepLevel(true);

    // Add them to the death list
    playerDeath.put(player.getUniqueId(),
        challengeHandler.getAllPlayersAlive(challenge).get(player.getUniqueId()));

    // Announce that a player has died
    challengeHandler.announceDeath(challenge, guilds, player, player,
        ChallengeHandler.Cause.PLAYER_KILLED_UNKNOWN);

    // Handle rest of arena stuff like normal
    challengeHandler.handleFinish(guilds, settingsManager, player, challenge);
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent event) {
    Player player = event.getPlayer();
    // Check if dead players contains this player
    if (playerDeath.containsKey(player.getUniqueId())) {
      // If it does, set their respawn location to the place they came from
      event.setRespawnLocation(
          ACFBukkitUtil.stringToLocation(playerDeath.get(player.getUniqueId())));
      // Remove from the map
      playerDeath.remove(player.getUniqueId());
    }
  }

  @EventHandler
  public void onDeathByPlayer(EntityDamageByEntityEvent event) {
    // Check to make sure both parties are players
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    if (!(event.getDamager() instanceof Player)) {
      return;
    }
    // Get a copy of the killer and player being killed
    Player entity = (Player) event.getEntity();
    Player killer = (Player) event.getDamager();

    // Check to make sure this damage would kill them to prevent excess checking
    if (entity.getHealth() - event.getFinalDamage() > 1) {
      return;
    }

    // Check to make sure they have a challenge
    GuildChallenge challenge = challengeHandler.getChallenge(entity);

    // Make sure it's not null
    if (challenge != null) {
      if (challenge.isStarted()) {
        // Cancel the last damage so they don't die
        event.setCancelled(true);
        // Tell everyone in the arena that the player was killed
        challengeHandler.announceDeath(challenge, guilds, entity, killer,
            ChallengeHandler.Cause.PLAYER_KILLED_PLAYER);
        // Teleport them out of the arena
        challengeHandler.exitArena(entity, challenge, guilds);
        // Remove them
        challengeHandler.handleFinish(guilds, settingsManager, entity, challenge);
      }
    }
  }

  @EventHandler
  public void onCommand(PlayerCommandPreprocessEvent event) {
    Player player = event.getPlayer();
    // Check if commands are blocked during arena
    if (settingsManager.getProperty(WarSettings.DISABLE_COMMANDS)) {
      // See if player has challenge
      GuildChallenge challenge = challengeHandler.getChallenge(player);
      // If they have one, continue
      if (challenge != null) {
        // Check if they are admin to bypass
        if (player.hasPermission(Constants.ADMIN_PERM)) {
          return;
        }
        // Cancel the command
        event.setCancelled(true);
        // Tell them they can't run commands while in war
        guilds.getCommandManager().getCommandIssuer(player)
            .sendInfo(Messages.WAR__COMMANDS_BLOCKED);
      }

    }
  }

}

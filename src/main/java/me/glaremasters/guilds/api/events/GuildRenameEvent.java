package me.glaremasters.guilds.api.events;

import me.glaremasters.guilds.api.events.base.GuildEvent;
import me.glaremasters.guilds.guild.Guild;
import org.bukkit.entity.Player;

public class GuildRenameEvent extends GuildEvent {

  private String name;

  /**
   * Base guild event
   *
   * @param player player in event
   * @param guild  guild in the event
   */
  public GuildRenameEvent(Player player, Guild guild, String newName) {
    super(player, guild);
    this.name = newName;
  }

  public String getName() {
    return name;
  }
}

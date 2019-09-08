/*
 * MIT License
 *
 * Copyright (c) 2019 Glare
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.glaremasters.guilds.guild;

import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * Created by GlareMasters Date: 3/15/2019 Time: 7:28 PM
 */
public class GuildCode {

  private String id;
  private int uses;
  private UUID creator;
  private List<UUID> redeemers;

  public GuildCode(String id, int uses, UUID creator, List<UUID> redeemers) {
    this.id = id;
    this.uses = uses;
    this.creator = creator;
    this.redeemers = redeemers;
  }

  /**
   * Add a redeemer to a code
   *
   * @param player the player being added
   */
  public void addRedeemer(Player player) {
    setUses(getUses() - 1);
    getRedeemers().add(player.getUniqueId());
  }

  public String getId() {
    return this.id;
  }

  public int getUses() {
    return this.uses;
  }

  public UUID getCreator() {
    return this.creator;
  }

  public List<UUID> getRedeemers() {
    return this.redeemers;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setUses(int uses) {
    this.uses = uses;
  }

  public void setCreator(UUID creator) {
    this.creator = creator;
  }

  public void setRedeemers(List<UUID> redeemers) {
    this.redeemers = redeemers;
  }
}

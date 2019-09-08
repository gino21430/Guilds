package me.glaremasters.guilds.database.challenges;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.glaremasters.guilds.Guilds;
import me.glaremasters.guilds.guild.GuildChallenge;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class ChallengeRowMapper implements RowMapper<GuildChallenge> {

  @Override
  public GuildChallenge map(ResultSet rs, StatementContext ctx) throws SQLException {
    return Guilds.getGson().fromJson(rs.getString("data"), GuildChallenge.class);
  }
}

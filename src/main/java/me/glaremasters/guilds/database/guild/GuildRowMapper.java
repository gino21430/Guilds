package me.glaremasters.guilds.database.guild;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.glaremasters.guilds.Guilds;
import me.glaremasters.guilds.guild.Guild;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class GuildRowMapper implements RowMapper<Guild> {

  @Override
  public Guild map(ResultSet rs, StatementContext ctx) throws SQLException {
    return Guilds.getGson().fromJson(rs.getString("data"), Guild.class);
  }
}
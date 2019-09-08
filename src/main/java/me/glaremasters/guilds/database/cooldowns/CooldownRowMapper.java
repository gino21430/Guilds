package me.glaremasters.guilds.database.cooldowns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import me.glaremasters.guilds.cooldowns.Cooldown;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class CooldownRowMapper implements RowMapper<Cooldown> {

  @Override
  public Cooldown map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new Cooldown(
        UUID.fromString(rs.getString("id")),
        Cooldown.Type.getByTypeName(rs.getString("type")),
        UUID.fromString(rs.getString("owner")),
        rs.getTimestamp("expiry").getTime()
    );
  }
}
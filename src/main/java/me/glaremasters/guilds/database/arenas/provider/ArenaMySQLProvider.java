package me.glaremasters.guilds.database.arenas.provider;

import java.io.IOException;
import java.util.List;
import me.glaremasters.guilds.arena.Arena;
import me.glaremasters.guilds.database.arenas.ArenaProvider;
import me.glaremasters.guilds.database.arenas.ArenaRowMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jetbrains.annotations.NotNull;

public interface ArenaMySQLProvider extends ArenaProvider {

  @Override
  @SqlUpdate(
      "CREATE TABLE IF NOT EXISTS <prefix>arena (\n" +
          "  `id` VARCHAR(36) NOT NULL,\n" +
          "  `data` JSON NOT NULL,\n" +
          "  PRIMARY KEY (`id`),\n" +
          "  UNIQUE (`id`));"
  )
  void createContainer(@Define("prefix") @NotNull String prefix);

  @Override
  @SqlQuery("SELECT EXISTS(SELECT 1 FROM <prefix>arena WHERE id = :id)")
  boolean arenaExists(@Define("prefix") @NotNull String prefix, @Bind("id") @NotNull String id)
      throws IOException;

  @Override
  @SqlQuery("SELECT id FROM <prefix>arena")
  List<String> getAllArenaIds(@Define("prefix") @NotNull String tablePrefix) throws IOException;

  @Override
  @SqlQuery("SELECT * FROM <prefix>arena")
  @RegisterRowMapper(ArenaRowMapper.class)
  List<Arena> getAllArenas(@Define("prefix") @NotNull String prefix);

  @Override
  @SqlQuery("SELECT * FROM <prefix>arena WHERE id = :id")
  @RegisterRowMapper(ArenaRowMapper.class)
  Arena getArena(@Define("prefix") @NotNull String prefix, @Bind("id") @NotNull String id)
      throws IOException;

  @Override
  @SqlUpdate("INSERT INTO <prefix>arena(id, data) VALUES (:id, :data)")
  void createArena(@Define("prefix") @NotNull String prefix, @Bind("id") String id,
      @Bind("data") String data);

  @Override
  @SqlUpdate("UPDATE <prefix>arena SET data = :data WHERE id = :id")
  void updateArena(@Define("prefix") @NotNull String prefix, @Bind("id") @NotNull String id,
      @Bind("data") @NotNull String data) throws IOException;

  @Override
  @SqlUpdate("DELETE FROM <prefix>arena WHERE id = :id")
  void deleteArena(@Define("prefix") @NotNull String prefix, @Bind("id") @NotNull String id)
      throws IOException;
}

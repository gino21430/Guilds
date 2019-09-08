package me.glaremasters.guilds.database.challenges;

import java.io.File;
import java.io.IOException;
import me.glaremasters.guilds.Guilds;
import me.glaremasters.guilds.database.DatabaseAdapter;
import me.glaremasters.guilds.database.DatabaseBackend;
import me.glaremasters.guilds.database.challenges.provider.ChallengeJsonProvider;
import me.glaremasters.guilds.guild.GuildChallenge;
import org.jetbrains.annotations.NotNull;

public class ChallengeAdapter {

  private final ChallengeProvider provider;
  private String sqlTablePrefix;

  public ChallengeAdapter(Guilds guilds, DatabaseAdapter adapter) {
    DatabaseBackend backend = adapter.getBackend();
    switch (backend) {
      default:
      case JSON:
        File fileDataFolder = new File(guilds.getDataFolder(), "challenges");
        provider = new ChallengeJsonProvider(fileDataFolder);
        break;
      case MYSQL:
      case SQLITE:
        sqlTablePrefix = adapter.getSqlTablePrefix();
        provider = adapter.getDatabaseManager().getJdbi().onDemand(backend.getChallengeProvider());
        break;
    }
  }

  public void createContainer() throws IOException {
    provider.createContainer(sqlTablePrefix);
  }

  public void createChallenge(@NotNull GuildChallenge challenge) throws IOException {
    provider.createChallenge(sqlTablePrefix, challenge.getId().toString(),
        Guilds.getGson().toJson(challenge, GuildChallenge.class));
  }

  public void deleteChallenge(@NotNull String id) throws IOException {
    provider.deleteChallenge(sqlTablePrefix, id);
  }
}

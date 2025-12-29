package pg.projects.backend.Util;

import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pg.projects.backend.Models.Game;
import pg.projects.backend.Repositories.GameRedisRepository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
public class GameLoader implements CommandLineRunner {

    @Autowired
    GameRedisRepository gameRedisRepository;

    @Value("classpath:games.json")
    private Resource resource;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Game> gameList;

    @Override
    public void run(String... args) throws Exception {
        gameList = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Game>>() {
        });
        for(Game game: gameList)
            gameRedisRepository.saveGame(game);
    }
}

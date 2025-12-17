package pg.projects.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;
import pg.projects.backend.Models.Game;
import pg.projects.backend.Repositories.GameRedisRepository;

@SpringBootTest
class GameCluesBackendTests {


    @Autowired
    GameRedisRepository repository;

    @Test
    public void test(){
        Game game = new Game();
        game.setId(12043L);
        game.setName("first game");
        repository.saveGame(game);
        Game gameById = repository.getGameById("12043");
        String randomGameIdFromPool = repository.getRandomGameIdFromPool();
        int t = 0;
    }

}

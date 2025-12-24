package pg.projects.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import pg.projects.backend.DTOs.GuessRequest;
import pg.projects.backend.DTOs.GuessResponse;
import pg.projects.backend.Models.Game;
import pg.projects.backend.Models.GameSession;
import pg.projects.backend.Repositories.GameRedisRepository;
import pg.projects.backend.Repositories.GameSessionRepository;
import pg.projects.backend.Services.GuessService;
import pg.projects.backend.Services.IgdbService;
import pg.projects.backend.Util.GameMapper;
import pg.projects.backend.Util.GameNormalizer;

@SpringBootTest
class GameCluesBackendTests {

    @Autowired
    IgdbService guessService;

    @Test
    public void test(){

        String gameData = guessService.fetchGameData("god of war");
        int t = 1;



    }

}
